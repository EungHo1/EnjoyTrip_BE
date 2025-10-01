-- -----------------------------------------------------
-- Schema ssafy_trip
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ssafy_trip` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `ssafy_trip` ;

-- -----------------------------------------------------
-- 기존 테이블들 초기화
-- -----------------------------------------------------
DROP TABLE IF EXISTS `attractions`;
DROP TABLE IF EXISTS `contenttypes`;
DROP TABLE IF EXISTS `guguns`;
DROP TABLE IF EXISTS `sidos`;
DROP TABLE IF EXISTS `board`;
DROP TABLE IF EXISTS `user`;

-- -----------------------------------------------------
-- 공식 스키마 테이블 생성
-- -----------------------------------------------------
CREATE TABLE `sidos` ( `no` int NOT NULL AUTO_INCREMENT, `sido_code` int NOT NULL, `sido_name` varchar(20), PRIMARY KEY (`no`), UNIQUE KEY `sido_code_UNIQUE` (`sido_code`));
CREATE TABLE IF NOT EXISTS `ssafy_trip`.`guguns` (
                                                     `no` INT NOT NULL AUTO_INCREMENT COMMENT '구군번호',
                                                     `sido_code` INT NOT NULL COMMENT '시도코드',
                                                     `gugun_code` INT NOT NULL COMMENT '구군코드',
                                                     `gugun_name` VARCHAR(20) DEFAULT NULL COMMENT '구군이름',
                                                     PRIMARY KEY (`no`),
                                                     UNIQUE INDEX `gugun_code_UNIQUE` (`gugun_code` ASC),
                                                     INDEX `guguns_sido_to_sidos_cdoe_fk_idx` (`sido_code` ASC),
                                                     CONSTRAINT `guguns_sido_to_sidos_cdoe_fk`
                                                         FOREIGN KEY (`sido_code`)
                                                             REFERENCES `ssafy_trip`.`sidos` (`sido_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='구군정보테이블';
CREATE TABLE `contenttypes` ( `content_type_id` int NOT NULL, `content_type_name` varchar(45), PRIMARY KEY (`content_type_id`));
CREATE TABLE `attractions` ( `no` int NOT NULL AUTO_INCREMENT, `content_id` int, `title` varchar(500), `content_type_id` int, `area_code` int, `si_gun_gu_code` int, `first_image1` varchar(100), `first_image2` varchar(100), `map_level` int, `latitude` decimal(20,17), `longitude` decimal(20,17), `tel` varchar(20), `addr1` varchar(100), `addr2` varchar(100), `homepage` varchar(1000), `overview` varchar(10000), PRIMARY KEY (`no`), FOREIGN KEY (`area_code`) REFERENCES `sidos` (`sido_code`), FOREIGN KEY (`si_gun_gu_code`) REFERENCES `guguns` (`gugun_code`), FOREIGN KEY (`content_type_id`) REFERENCES `contenttypes` (`content_type_id`));

-- -----------------------------------------------------
-- user, board 테이블 생성
-- -----------------------------------------------------
CREATE TABLE `user` ( `user_no` BIGINT NOT NULL AUTO_INCREMENT, `user_id` VARCHAR(20) NOT NULL, `user_name` VARCHAR(50) NOT NULL, `user_password` VARCHAR(255) NOT NULL, `join_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, PRIMARY KEY (`user_no`), UNIQUE KEY `UK_user_user_id` (`user_id`));
CREATE TABLE `board` ( `article_no` BIGINT NOT NULL AUTO_INCREMENT, `user_no` BIGINT NOT NULL, `subject` VARCHAR(255) NOT NULL, `content` TEXT NOT NULL, `hit` INT NOT NULL DEFAULT 0, `register_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, PRIMARY KEY (`article_no`), FOREIGN KEY (`user_no`) REFERENCES `user`(`user_no`));

-- -----------------------------------------------------
-- 샘플 데이터
-- -----------------------------------------------------
INSERT INTO `user` (user_id, user_name, user_password) VALUES ('unknown', '탈퇴한 사용자', 'hashed_password'), ('ssafy', '김싸피', '1234'), ('admin', '관리자', 'admin');
INSERT INTO `board` (user_no, subject, content) VALUES (1, '첫 번째 글입니다.', '안녕하세요.'), (2, '공지사항입니다.', '사이트 규칙을 잘 지켜주세요.');