package com.example.ordering_lecture.recommend.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class RecommendationScheduler {
    private final RecommendationService recommendationService;

    public RecommendationScheduler(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    // 테스트용 1분마다 실행
    @Scheduled(cron = "0 0/1 * * * *")
    @Transactional
    public void postSchedule(){
        System.out.println("===스케줄러 시작===");
        recommendationService.getRecommendations();
        System.out.println("===스케줄러 끝===");
    }
}
