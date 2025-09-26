-- 기존 테이블이 존재할 경우 삭제
DROP TABLE IF EXISTS board;
DROP TABLE IF EXISTS user;

-- 유저 테이블 생성
CREATE TABLE `user` (
                        `user_id` VARCHAR(20) NOT NULL,
                        `user_name` VARCHAR(50) NOT NULL,
                        `user_password` VARCHAR(255) NOT NULL,
                        `join_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 게시판 테이블 생성
CREATE TABLE `board` (
                         `article_no` INT NOT NULL AUTO_INCREMENT,
                         `user_id` VARCHAR(20) NOT NULL,
                         `subject` VARCHAR(255) NOT NULL,
                         `content` TEXT NOT NULL,
                         `hit` INT NOT NULL DEFAULT 0,
                         `register_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         PRIMARY KEY (`article_no`),
    -- 외래 키(Foreign Key) 설정: user 테이블의 user_id를 참조
                         FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 샘플 데이터 추가 (테스트용)
INSERT INTO `user` (user_id, user_name, user_password) VALUES
                                                           ('ssafy', '김싸피', '1234'),
                                                           ('admin', '관리자', 'admin');

INSERT INTO `board` (user_id, subject, content) VALUES
                                                    ('ssafy', '첫 번째 글입니다.', '안녕하세요. 만나서 반갑습니다.'),
                                                    ('admin', '공지사항입니다.', '사이트 규칙을 잘 지켜주세요.');