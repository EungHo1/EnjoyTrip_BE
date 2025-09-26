# ✈️ EnjoyTrip Backend

### 나만의 여행 계획을 세우고 공유하는 **EnjoyTrip**

EnjoyTrip은 전국의 관광 정보를 기반으로 사용자가 직접 여행 계획을 세우고, 다른 여행자들과 경험을 공유할 수 있는 커뮤니티형 웹 서비스입니다. 이 저장소는 **EnjoyTrip의 백엔드 시스템**을 위한 코드를 담고 있습니다.

---

## 💻 기술 스택

- **Backend**: Java
- **Database**: 
- **Web Server**: Tomcat

---

## ✨ 주요 기능

### 🗺️ 지역별 관광 정보 조회
- 공공데이터 API 기반의 전국 관광지, 축제, 먹거리 정보 제공  
- **리스트 뷰 & 지도 뷰**로 손쉽게 탐색 가능  
- 지역 및 콘텐츠 유형별 필터링 지원  


### 📅 나만의 여행 계획
- 원하는 여행지를 선택하고 일정에 추가  
- 일자별 상세 일정, 예상 경비, 메모 기록 가능  
- 효율적인 동선을 기반으로 여행 최적화  


### 🔥 HotPlace 공유
- 사용자 추천 장소 등록 가능  
- 사진, 방문 후기, 위치, 장소 유형 기록  
- 다른 여행자들이 공유한 HotPlace 확인 가능  


### 🗣️ 커뮤니티 게시판
- 여행 후기, 꿀팁, 질문과 답변을 자유롭게 공유  
- 사용자 간 소통을 통해 여행 경험 확장  

---

## 📂 프로젝트 구조

```bash
C:.
├─java
│  └─ssafy
│      └─ps
│          └─enjoytrip_be
│              ├─controller       # 서블릿 컨트롤러
│              ├─dao              # DAO 인터페이스
│              │  └─impl          # DAO 구현체
│              ├─domain           # 엔티티 클래스
│              ├─dto              # DTO 클래스
│              ├─service          # 서비스 인터페이스
│              │  └─impl          # 서비스 구현체
│              └─util             # DB 연결 유틸
│
├─resources
│      schema.sql                 # DB 초기 스키마
│
└─webapp
    │  index.jsp                  # 메인 페이지
    │
    └─WEB-INF
        │  web.xml                # 서블릿 매핑
        │
        └─views
            ├─attraction          # 관광지 관련 뷰
            ├─board               # 게시판 뷰
            ├─common              # header/footer/nav
            ├─hotplace            # HotPlace 뷰
            ├─notice              # 공지사항 뷰
            ├─plan                # 여행 계획 뷰
            └─user                # 사용자 뷰 (로그인/회원가입 등)
```

---

## 🛠️ 실행 방법

1.  **프로젝트 클론**
    ```bash
    git clone [프로젝트 GitHub 주소]
    ```

2. 


3. 


---

## 🧑‍💻 개발 참여자

| 이름 | 역할 | GitHub |
| :---: | :---: | :---: |
| [본인 이름] | [역할 상세] | [GitHub 아이디] |
| [팀원 1] | [역할 상세] | [GitHub 아이디] |
