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
ssafy.ps.enjoytrip_be
├─controller
│  │  MainServlet.java
│  │  UserServlet.java
│  │  BoardServlet.java
│  │  NoticeServlet.java
│  │  AttractionServlet.java
│  │  PlanServlet.java
│  └─ HotplaceServlet.java
│
├─dao
│  │  UserDao.java
│  │  BoardDao.java
│  │  NoticeDao.java
│  │  AttractionDao.java
│  │  PlanDao.java
│  │  HotplaceDao.java
│  └─ NewsDao.java
│  └─impl
│      │  UserDaoImpl.java
│      │  BoardDaoImpl.java
│      │  NoticeDaoImpl.java
│      │  AttractionDaoImpl.java
│      │  PlanDaoImpl.java
│      │  HotplaceDaoImpl.java
│      └─ NewsDaoImpl.java
│
├─domain
│  │  User.java
│  │  Board.java
│  │  Notice.java
│  │  Attraction.java
│  │  Hotplace.java
│  │  News.java
│  │  TripPlan.java
│  └─ TripCourse.java
│
├─dto
│  │  UserDto.java
│  └─ BoardDto.java
│
├─service
│  │  UserService.java
│  │  BoardService.java
│  │  NoticeService.java
│  │  AttractionService.java
│  │  PlanService.java
│  │  HotplaceService.java
│  └─ CrawlingService.java
│  └─impl
│      │  UserServiceImpl.java
│      │  BoardServiceImpl.java
│      │  NoticeServiceImpl.java
│      │  AttractionServiceImpl.java
│      │  PlanServiceImpl.java
│      │  HotplaceServiceImpl.java
│      └─ CrawlingServiceImpl.java
│
└─util
   │  DBUtil.java
   └─ ApiUtil.java

```

---

## 🛠️ 실행 방법

0. EnjoyTrip 로컬 개발 환경 설정 가이드

0-1. 사전 요구사항
- `MySQL` 서버가 로컬에 설치되어 있어야 합니다.

0-2. 데이터베이스 및 계정 생성
- 로컬 MySQL 서버에 접속하여 아래 쿼리를 실행하세요.
- 이 작업은 프로젝트 최초 설정 시 **한 번만** 수행하면 됩니다.

```sql
-- 1. enjoytripdb 데이터베이스 생성 (이미 존재하면 건너뛰기)
CREATE DATABASE IF NOT EXISTS enjoytripdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 2. 프로젝트에서 사용할 사용자 계정 생성 및 권한 부여
-- 'ssafy'@'localhost' 계정이 이미 존재하면 건너뛰어도 됩니다.
CREATE USER 'SSAFY'@'localhost' IDENTIFIED BY 'SSAFY';
GRANT ALL PRIVILEGES ON enjoytripdb.* TO 'SSAFY'@'localhost';
FLUSH PRIVILEGES;
```
0-3. 애플리케이션 실행
- 위 설정이 완료된 후, IntelliJ에서 Tomcat 서버를 실행하면 `ServletContextListener`가 자동으로 `enjoytripdb` 내부에 필요한 테이블을 생성합니다.

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
