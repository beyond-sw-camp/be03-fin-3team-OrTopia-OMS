<div align=center>

# OrTopia
공급망 주문 관리 시스템
<br>
<br>
<a href = "https://www.notion.so/c275b28e80d348438337a95a55b7bc56"><img src="https://img.shields.io/badge/Team Notion-ffffff?style=social&logo=Notion&logoColor=black" /></a>
<br>
<br>
<br>
[🤠신유정](https://github.com/yujeong-shin)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[🐻임재영](https://github.com/Hi-Imjaeyoung)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[🐼권도훈](https://github.com/kwondohoon1)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[🐱김종원](https://github.com/Kimjongwon1)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
<br>
<br>

---
<div align=center>
<h2> 🗓️ 전체 프로젝트 일정 </h2>
2024.03.16 ~ 2024.05.09
<br>
<br>
<br>
<h2>📝 프로젝트 계획서 </h2>
<a href="https://github.com/yujeong-shin/OrTopia/blob/master/Docs/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8%EA%B3%84%ED%9A%8D%EC%84%9C/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8%20%EA%B3%84%ED%9A%8D%EC%84%9C.pdf">프로젝트 계획서</a>
</div>
<br>
<br>

---
<div align=center>
<h2> 🛠️ 요구사항 정의서 </h2>
<a href="https://github.com/yujeong-shin/OrTopia/blob/master/Docs/%EC%9A%94%EA%B5%AC%EC%82%AC%ED%95%AD%EC%A0%95%EC%9D%98%EC%84%9C/%EC%9A%94%EA%B5%AC%EC%82%AC%ED%95%AD%EC%A0%95%EC%9D%98%EC%84%9C.pdf">요구사항 정의서</a>
</div>
<br>
<br>

---
<div align=center>
<h2> 🎨 화면 설계서 </h2>
<a href="https://www.figma.com/file/LJ6RQlTleAg8cQPLwReTHm/3%EC%A1%B0?type=design&node-id=0-1&mode=design&t=87fYffD9wVwiXvXe-0">화면 설계서</a>
</div>
<br>
<br>

---
<div align=center>
<h2> 🕒 WBS </h2>
<a href="https://github.com/yujeong-shin/OrTopia/blob/master/Docs/WBS/WBS.pdf">WBS</a>
<img src="https://github.com/yujeong-shin/OrTopia/blob/master/Docs/WBS/WBS1.png" />
<img src="https://github.com/yujeong-shin/OrTopia/blob/master/Docs/WBS/WBS2.png" />
</div>
<br>
<br>

---
<div align=center>
<h2> 🖼️ ERD </h2> 
<a href="https://app.diagrams.net/#G1V4hFe8kKqSb__3v7j4n1dP4GaezfI1jR#%7B%22pageId%22%3A%22zsw55LCCuFM0yS5R2fEg%22%7D">ERD</a>
<img src="https://github.com/yujeong-shin/OrTopia/blob/master/Docs/ERD/ERD.png" />
</div>
<br>
<br>

---
<div align=center>
<h2> ⚙️ 시스템 아키텍처 설계서 </h2>
<a href="https://github.com/beyond-sw-camp/be03-fin-3team-OrTopia-OMS/assets/57553339/e5498ed1-97a3-4119-800f-17d86b315299">시스템 아키텍처 설계서</a>
</div>
<br>
<br>

---
<div align=center>
<h2> 📰 프로그램 사양서 </h2>
<a href="https://github.com/yujeong-shin/OrTopia/blob/master/Docs/%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%A8%EC%82%AC%EC%96%91%EC%84%9C/%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%A8%20%EC%82%AC%EC%96%91%EC%84%9C.pdf">프로그램 사양서</a>
</div>
<br>
<br>

---

<div align=center>
<h2> 📈 CI/CD 계획서 </h2>
<img src="https://github.com/beyond-sw-camp/be03-fin-3team-OrTopia-OMS/assets/57553339/044ef5ff-42a8-48c2-ad9a-935a4ba692b6" />
</div>
 
<h3> Front </h3>
1. Jenkins를 사용해 Front 코드를 빌드 후, S3 버킷에 업로드<br>
2. Webhook 설정 시 ngrok 사용해 local URL을 public URL로 변경<br>
* Front 코드 변경이 발생 즉시, GitHub hook trigger를 통해 빌드 완전 자동화<br>

<h4> Front Script </h4>
1. Git Clone<br>
2. 해당 Repo로 checkout<br>
3. vue run/build를 위한 npm 설치<br>
4. AWS CLI 자격 증명<br>
5. 프론트 빌드 후 생성되는 dist 폴더 S3 bucket에 업로드

<br>
<br>

<h3> Back </h3>
1. 개발자 코드 수정 후 PUSH할 때마다 Docker 이미지를 ECR에 업로드<br>
2. 마스터 노드가 ECR에 올린 Docker 이미지를 다운<br>
3. AWS EKS 서비스를 사용해 다중 서버 구동<br>
* Order, Item, Member, Notice<br>
4. Recommendation 서버는 버전 충돌 문제로 인해 별도의 AWS EC2 서버 구동<br>
5. 서버 간 비동기 DB 업데이트 요청은 Kafka를 사용해 비동기로 처리, 동기가 필요한 업데이트 요청은 동기로 처리. <br>
6. Git Actions, Kubernetes 내장 secret 활용해 외부로부터 중요 정보 보호<br>
7. AWS Elasti Cache를 사용하여 Redis 관리 - 토큰 검증, 추천 아이템 저장, 아이템 재고 관리, 최근 본 상품 관리, 결제 코드 관리

<h4> Back Script </h4>
1. 해당 Repo로 checkout<br>
2. EKS 클러스터와 상호 작용하기 위한 kubectl 구성 파일 생성<br>
3. docker로 생성한 백엔드 이미지 빌드 후 ECR에 업로드<br>
4. pod 생성 시 백엔드 이미지 배포
<br>
<br>

---
<div align=center>
<h2> 🧩 주요 단위 테스트 결과서 </h2> 
* 메인 페이지
<br>
<img src="https://github.com/beyond-sw-camp/be03-fin-3team-OrTopia-OMS/assets/57553339/3e9bdbe0-647f-4c2c-b63b-5b4ddcc8415e"
 />
<img src="https://github.com/beyond-sw-camp/be03-fin-3team-OrTopia-OMS/assets/57553339/d8101d8b-d09c-424c-baae-225c49103c9c" />
<img src="https://github.com/beyond-sw-camp/be03-fin-3team-OrTopia-OMS/assets/57553339/27f74d94-1835-4912-9d71-774d67cc114c" />
<br>
<br>

* 회원가입 및 로그인
<br>
<img src="https://github.com/beyond-sw-camp/be03-fin-3team-OrTopia-OMS/assets/57553339/c9af6eba-6506-4ab0-a795-4925931a3da8" />
<br>
<br>

* 마이페이지
<br>
<img src="https://github.com/beyond-sw-camp/be03-fin-3team-OrTopia-OMS/assets/57553339/0f0c1913-7945-4f7e-9d63-0546afed635f" />
<img src="https://github.com/beyond-sw-camp/be03-fin-3team-OrTopia-OMS/assets/57553339/15e87ebb-af4a-4457-9396-b243140f5599" />
<br>
* 주소 등록
<br>
<img src="https://github.com/beyond-sw-camp/be03-fin-3team-OrTopia-OMS/assets/57553339/620325fb-bd6b-4dfe-bdde-5eb119b25d85" />
<br>
* 판매자 등록
<br>
<img src="https://github.com/beyond-sw-camp/be03-fin-3team-OrTopia-OMS/assets/57553339/d25b5d15-fd17-450f-a1a2-c950e41229ee" />
<br>
<br>

* 판매자 쿠폰 발급 및 조회
<br>
<img src="https://github.com/beyond-sw-camp/be03-fin-3team-OrTopia-OMS/assets/57553339/89f31afe-3dd5-4644-bef8-b112b795dbe1" />
<br>
<br>

* 판매자 아이템 등록
<br>
<img src="https://github.com/beyond-sw-camp/be03-fin-3team-OrTopia-OMS/assets/57553339/b503dc36-4b2d-400c-a9fd-fda5459a474a" />
<br>
<br>

* 상품 상세 조회 & 최근 본 상품 조회
<br>
<img src="https://github.com/beyond-sw-camp/be03-fin-3team-OrTopia-OMS/assets/57553339/ca9c0dba-ea97-4542-a01b-4b5047118499" />
<br>
<br>

* 사용자별 추천 아이템 조회
<br>
<img src="https://github.com/beyond-sw-camp/be03-fin-3team-OrTopia-OMS/assets/57553339/4b73af96-a9f6-4b80-a221-6f832cddf0b0" />
<br>
<br>


* 즐겨찾기
<br>
<img src="https://github.com/beyond-sw-camp/be03-fin-3team-OrTopia-OMS/assets/57553339/d9d51c0c-76f6-4a4f-ae20-4fbff339e385" />
<br>
<br>

* 공지 사항
<br>
<img src="https://github.com/beyond-sw-camp/be03-fin-3team-OrTopia-OMS/assets/57553339/634761e5-eb1e-4071-be16-b2f3ebda7f78" />
<br>
<br>


* 장바구니
<br>
<img src="https://github.com/beyond-sw-camp/be03-fin-3team-OrTopia-OMS/assets/57553339/ace85082-7d44-4244-8842-df28118e3d8a" />
<br>
<br>


* 주문 및 결제
<br>
<img src="https://github.com/beyond-sw-camp/be03-fin-3team-OrTopia-OMS/assets/57553339/9d5a8396-16d7-4d9a-8090-e94fd98d33e7" />
<br>
<br>
