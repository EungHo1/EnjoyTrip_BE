-- MySQL 버전 스키마
-- H2와 달리 식별자에 백틱(`)을 사용하고, ENGINE과 CHARSET을 명시하는 것이 좋음

DROP TABLE IF EXISTS `board`;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
                        `user_no` BIGINT NOT NULL AUTO_INCREMENT COMMENT '사용자 고유 번호 (PK)',
                        `user_id` VARCHAR(20) NOT NULL COMMENT '로그인 ID (UNIQUE)',
                        `user_name` VARCHAR(50) NOT NULL COMMENT '사용자 이름',
                        `user_password` VARCHAR(255) NOT NULL COMMENT '비밀번호',
                        `join_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '가입일',
                        PRIMARY KEY (`user_no`),
                        UNIQUE KEY `UK_user_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='사용자 정보';

CREATE TABLE `board` (
                         `article_no` BIGINT NOT NULL AUTO_INCREMENT COMMENT '게시글 고유 번호 (PK)',
                         `user_no` BIGINT NOT NULL COMMENT '작성자 고유 번호 (FK)',
                         `subject` VARCHAR(255) NOT NULL COMMENT '게시글 제목',
                         `content` TEXT NOT NULL COMMENT '게시글 내용',
                         `hit` INT NOT NULL DEFAULT 0 COMMENT '조회수',
                         `register_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성일',
                         PRIMARY KEY (`article_no`),
                         FOREIGN KEY (`user_no`) REFERENCES `user`(`user_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='게시판 정보';

-- 샘플 데이터 (주의: user_id로 넣지만, 실제로는 user_no 1, 2번으로 저장됨)
INSERT INTO `user` (user_id, user_name, user_password) VALUES
                                                           ('ssafy', '김싸피', '1234'),
                                                           ('admin', '관리자', 'admin');

-- board 테이블에는 user_no 값을 사용해서 데이터를 넣어야 함
INSERT INTO `board` (user_no, subject, content) VALUES
                                                    (1, '첫 번째 글입니다.', '안녕하세요. 만나서 반갑습니다.'),
                                                    (2, '공지사항입니다.', '사이트 규칙을 잘 지켜주세요.');