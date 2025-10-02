-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema ssafy_trip
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `ssafy_trip`;
CREATE SCHEMA IF NOT EXISTS `ssafy_trip` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `ssafy_trip` ;

-- -----------------------------------------------------
-- Table `ssafy_trip`.`sidos`: SSAFY 원본 스키마
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafy_trip`.`sidos` (
                                                    `no` int NOT NULL AUTO_INCREMENT  comment '시도번호',
                                                    `sido_code` int NOT NULL comment '시도코드',
                                                    `sido_name` varchar(20) DEFAULT NULL comment '시도이름',
                                                    PRIMARY KEY (`no`),
                                                    UNIQUE INDEX `sido_code_UNIQUE` (`sido_code` ASC) VISIBLE)
    ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `ssafy_trip`.`guguns`: SSAFY 원본 스키마
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafy_trip`.`guguns` (
                                                     `no` int NOT NULL AUTO_INCREMENT comment '구군번호',
                                                     `sido_code` int NOT NULL comment '시도코드',
                                                     `gugun_code` int NOT NULL comment '구군코드',
                                                     `gugun_name` varchar(20) DEFAULT NULL comment '구군이름',
                                                     PRIMARY KEY (`no`),
                                                     INDEX `guguns_sido_to_sidos_cdoe_fk_idx` (`sido_code` ASC) VISIBLE,
                                                     INDEX `gugun_code_idx` (`gugun_code` ASC) VISIBLE,
                                                     CONSTRAINT `guguns_sido_to_sidos_cdoe_fk`
                                                         FOREIGN KEY (`sido_code`)
                                                             REFERENCES `ssafy_trip`.`sidos` (`sido_code`))
    ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `ssafy_trip`.`contenttypes`: SSAFY 원본 스키마
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafy_trip`.`contenttypes` (
                                                           `content_type_id` int NOT NULL comment '콘텐츠타입번호',
                                                           `content_type_name` varchar(45) DEFAULT NULL comment '콘텐츠타입이름',
                                                           PRIMARY KEY (`content_type_id`))
    ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `ssafy_trip`.`attractions`: SSAFY 원본 스키마
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafy_trip`.`attractions` (
                                                          `no` int NOT NULL AUTO_INCREMENT  comment '명소코드',
                                                          `content_id` int DEFAULT NULL UNIQUE comment '콘텐츠번호',
                                                          `title` varchar(500) DEFAULT NULL comment '명소이름',
                                                          `content_type_id` int DEFAULT NULL comment '콘텐츠타입',
                                                          `area_code` int DEFAULT NULL comment '시도코드',
                                                          `si_gun_gu_code` int DEFAULT NULL comment '구군코드',
                                                          `first_image1` varchar(100) DEFAULT NULL comment '이미지경로1',
                                                          `first_image2` varchar(100) DEFAULT NULL comment '이미지경로2',
                                                          `map_level` int DEFAULT NULL comment '줌레벨',
                                                          `latitude` decimal(20,17) DEFAULT NULL comment '위도',
                                                          `longitude` decimal(20,17) DEFAULT NULL comment '경도',
                                                          `tel` varchar(20) DEFAULT NULL comment '전화번호',
                                                          `addr1` varchar(100) DEFAULT NULL comment '주소1',
                                                          `addr2` varchar(100) DEFAULT NULL comment '주소2',
                                                          `homepage` varchar(1000) DEFAULT NULL comment '홈페이지',
                                                          `overview` varchar(10000) DEFAULT NULL comment '설명',
                                                          PRIMARY KEY (`no`),
                                                          INDEX `attractions_typeid_to_types_typeid_fk_idx` (`content_type_id` ASC) VISIBLE,
                                                          INDEX `attractions_sido_to_sidos_code_fk_idx` (`area_code` ASC) VISIBLE,
                                                          INDEX `attractions_sigungu_to_guguns_gugun_fk_idx` (`si_gun_gu_code` ASC) VISIBLE,
                                                          CONSTRAINT `attractions_area_to_sidos_code_fk`
                                                              FOREIGN KEY (`area_code`)
                                                                  REFERENCES `ssafy_trip`.`sidos` (`sido_code`),
                                                          CONSTRAINT `attractions_sigungu_to_guguns_gugun_fk`
                                                              FOREIGN KEY (`si_gun_gu_code`)
                                                                  REFERENCES `ssafy_trip`.`guguns` (`gugun_code`),
                                                          CONSTRAINT `attractions_typeid_to_types_typeid_fk`
                                                              FOREIGN KEY (`content_type_id`)
                                                                  REFERENCES `ssafy_trip`.`contenttypes` (`content_type_id`))
    ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `ssafy_trip`.`user`
-- -----------------------------------------------------
CREATE TABLE `user` (
                        `user_no` BIGINT NOT NULL AUTO_INCREMENT,
                        `user_id` VARCHAR(20) NOT NULL,
                        `user_name` VARCHAR(50) NOT NULL,
                        `user_password` VARCHAR(255) NOT NULL,
                        `join_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        PRIMARY KEY (`user_no`),
                        UNIQUE INDEX `UK_user_user_id` (`user_id` ASC) VISIBLE
);

-- -----------------------------------------------------
-- Table `ssafy_trip`.`board`
-- -----------------------------------------------------
CREATE TABLE `board` (
                         `article_no` BIGINT NOT NULL AUTO_INCREMENT,
                         `user_no` BIGINT NOT NULL,
                         `subject` VARCHAR(255) NOT NULL,
                         `content` TEXT NOT NULL,
                         `hit` INT NOT NULL DEFAULT 0,
                         `register_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         PRIMARY KEY (`article_no`),
                         FOREIGN KEY (`user_no`) REFERENCES `user`(`user_no`)
);

-- 여행 계획 테이블
CREATE TABLE `plan` (
                        `plan_id` INT NOT NULL AUTO_INCREMENT,
                        `user_no` BIGINT NOT NULL,
                        `title` VARCHAR(100) NOT NULL,
                        `create_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        PRIMARY KEY (`plan_id`),
                        FOREIGN KEY (`user_no`) REFERENCES `user` (`user_no`)
);

-- 여행 계획에 포함된 관광지 목록 테이블
CREATE TABLE `plan_attraction` (
                                   `plan_id` INT NOT NULL,
                                   `content_id` INT NOT NULL,
                                   `order` INT NOT NULL COMMENT '경로 순서',
                                   PRIMARY KEY (`plan_id`, `content_id`),
                                   FOREIGN KEY (`plan_id`) REFERENCES `plan` (`plan_id`) ON DELETE CASCADE,
                                   FOREIGN KEY (`content_id`) REFERENCES `attractions` (`content_id`)
);


-- -----------------------------------------------------
-- 샘플 데이터
-- -----------------------------------------------------
INSERT INTO `user` (user_id, user_name, user_password) VALUES ('unknown', '탈퇴한 사용자', 'hashed_password'), ('ssafy', '김싸피', '1234'), ('admin', '관리자', 'admin');
INSERT INTO `board` (user_no, subject, content) VALUES (2, '첫 번째 글입니다.', '안녕하세요.'), (3, '공지사항입니다.', '사이트 규칙을 잘 지켜주세요.');

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

GRANT ALL PRIVILEGES ON ssafy_trip.* TO 'SSAFY'@'localhost';
FLUSH PRIVILEGES;

COMMIT;