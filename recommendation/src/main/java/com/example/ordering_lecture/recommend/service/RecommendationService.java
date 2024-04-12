package com.example.ordering_lecture.recommend.service;

import com.example.ordering_lecture.common.ErrorCode;
import com.example.ordering_lecture.common.OrTopiaException;
import com.example.ordering_lecture.feign.FeignClient;
import com.example.ordering_lecture.recommend.dto.RecommendationRedisData;
import com.example.ordering_lecture.recommend.token.JwtTokenProvider;
import com.example.ordering_lecture.redis.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.stereotype.Service;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationService {
    private final byte RECOMMENDATION_COUNT = 3;
    private final RedisService redisService;
    private final FeignClient feignClient;
    private final JwtTokenProvider jwtTokenProvider;
    public RecommendationService(RedisService redisService, FeignClient feignClient, JwtTokenProvider jwtTokenProvider) {
        this.redisService = redisService;
        this.feignClient = feignClient;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void getRecommendations() {
        // 기존 값들 비우기
        redisService.flushAll();

        // jwt 토큰 발행 후 Redis 3번 채널에 저장
        jwtTokenProvider.createRecommandToken();

        MariaDbDataSource dataSource = new MariaDbDataSource();
        try {
            // TODO : RDS 주소로 변경
            dataSource.setUrl("jdbc:mariadb://localhost:3306/item_server");
        } catch (SQLException e) {
            throw new OrTopiaException(ErrorCode.NOT_SET_URL);
        }
        try {
            dataSource.setUser("root");
        } catch (SQLException e) {
            throw new OrTopiaException(ErrorCode.NOT_SET_USER);
        }
        try {
            dataSource.setPassword("1234");
        } catch (SQLException e) {
            throw new OrTopiaException(ErrorCode.NOT_SET_PASSWORD);
        }

        // review 테이블에서 buyer_id, item_id, score 추출 후 모델 생성
        DataModel model = new MySQLJDBCDataModel(
                dataSource, // 데이터 소스
                "review", // 사용할 테이블 이름
                "buyer_id", // 사용자 ID 컬럼 이름
                "item_id", // 아이템 ID 컬럼 이름
                "score", // 평점 컬럼 이름
                null // 타임스탬프 컬럼 이름, 필요 없으면 null
        );

        // review를 작성한 구매자 id 모두 구하기
        List<Long> buyer_Ids = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT DISTINCT buyer_id FROM review")) {
            while (rs.next()) {
                long buyerId = rs.getLong("buyer_id");
                buyer_Ids.add(buyerId);
            }
        } catch (SQLException e) {
            throw new OrTopiaException(ErrorCode.DB_ERROR);
        }

        // 유저 기준 유사성 측정
        UserSimilarity similarity = null;
        try {
            similarity = new PearsonCorrelationSimilarity(model);
        } catch (TasteException e) {
            throw new OrTopiaException(ErrorCode.NOT_MAKE_SIMILARITY);
        }

        // 0.1보다 큰 유사성을 가진 모든 것을 사용.
        UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);

        // 유저 기준 추천 모델 생성
        UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

        // 각 유저에게 3개 아이템 추천
        for(Long id : buyer_Ids){
            List<RecommendedItem> recommendations;
            try {
                recommendations = recommender.recommend(id, RECOMMENDATION_COUNT);
            } catch (TasteException e) {
                throw new OrTopiaException(ErrorCode.NOT_MAKE_TASTE);
            }

            List<RecommendationRedisData> recommendationRedisDatas = new ArrayList<>();
            for (RecommendedItem recommendation : recommendations) {
                // Feign : item 서버에서 해당 아이템 이미지 경로 얻어오기
                String imagePath = feignClient.getImagePath(recommendation.getItemID());
                // [itemId, itemImagePath] 형식으로 저장
                RecommendationRedisData recommendationRedisData = new RecommendationRedisData(recommendation.getItemID(), imagePath);
                recommendationRedisDatas.add(recommendationRedisData);
            }

            // redis에 저장
            redisService.setValues(id, recommendationRedisDatas);
            System.out.println(id + "번 사용자에게 아이템 추천 완료");
        }
    }

    public List<RecommendationRedisData> readRecommendationItems(Long id) {
        List<String> list = redisService.getValues(id);
        List<RecommendationRedisData> recommendationRedisDatas = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for(String str: list){
            RecommendationRedisData  recommendationRedisData = null;
            try {
                recommendationRedisData = objectMapper.readValue(str, RecommendationRedisData.class);
            } catch (JsonProcessingException e) {
                throw new OrTopiaException(ErrorCode.JSON_PARSE_ERROR);
            }
            recommendationRedisDatas.add(recommendationRedisData);
        }
        return recommendationRedisDatas;
    }
}
