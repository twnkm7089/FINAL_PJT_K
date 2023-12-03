CREATE DATABASE `workout`;
USE `workout`;

-- 외래키 참조 문제
DROP TABLE IF EXISTS `workout`.`Score`;
DROP TABLE IF EXISTS `workout`.`User`;

DROP TABLE IF EXISTS `workout`.`Exercise`;


CREATE TABLE IF NOT EXISTS `workout`.`User` (
	`id` VARCHAR(30) NOT NULL,
    `nickname` VARCHAR(30) NOT NULL,
    `password` VARCHAR(30) NOT NULL,
    `email` VARCHAR(50) NOT NULL,
    `introduction` VARCHAR(100),
    PRIMARY KEY(`id`)
)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `workout`.`Score` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `userId` VARCHAR(30) NOT NULL,
    `totalScore` INT UNSIGNED NOT NULL DEFAULT 0,
    `todayScore` INT UNSIGNED NOT NULL DEFAULT 0,
    `lastRegDate` TIMESTAMP NOT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY (`userId`) REFERENCES `workout`.`User`(`id`)
)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `workout`.`Exercise` (
	`name` VARCHAR(30) NOT NULL,
    `score` INT NOT NULL,
    PRIMARY KEY(`name`)
)
ENGINE = InnoDB;

DESC `workout`.`User`;
DESC `workout`.`Score`;
DESC `workout`.`Exercise`;