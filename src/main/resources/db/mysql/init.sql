CREATE DATABASE IF NOT EXISTS doordash;

ALTER DATABASE doordash
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;

USE doordash;

-- ****************** SqlDBM: MySQL ******************;
-- ***************************************************;
DROP TABLE IF EXISTS `CustomerAddress`;

DROP TABLE IF EXISTS `Customer`;


DROP TABLE IF EXISTS `Address`;

-- ************************************** `Customer`

CREATE TABLE `Customer`
(
 `id`             varchar(36) DEFAULT (uuid()) NOT NULL,
 `created_at`     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 `updated_at`     TIMESTAMP DEFAULT NOW() ON UPDATE NOW(),
 `first_name`     varchar(45) NOT NULL ,
 `last_name`      varchar(45) ,
 `phone`          varchar(32) NOT NULL ,
 `email`          varchar(100) NOT NULL ,
 `verified_email` int ,
 `verified_phone` int ,
PRIMARY KEY (`id`)
);

-- ************************************** `Address`

CREATE TABLE `Address`
(
 `id`        varchar(36) DEFAULT (uuid()) NOT NULL,
 `created_at`     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 `updated_at`     TIMESTAMP DEFAULT NOW() ON UPDATE NOW(),
 `address_1` varchar(120) NOT NULL ,
 `address_2` varchar(120) ,
 `address_3` varchar(120) ,
 `city`      varchar(120) NOT NULL ,
 `state`     varchar(120) ,
 `country`   varchar(32) NOT NULL ,
 `location`  point NOT NULL ,
 `name`      varchar(120) NOT NULL ,
PRIMARY KEY (`id`)
);






-- ************************************** `CustomerAddress`

CREATE TABLE `CustomerAddress`
(
 `id`          varchar(36) DEFAULT (uuid()) NOT NULL,
 `created_at`     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 `updated_at`     TIMESTAMP DEFAULT NOW() ON UPDATE NOW(),
 `customer_id` varchar(36) NOT NULL ,
 `address_id`  varchar(36) NOT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`customer_id`) REFERENCES `Customer` (`id`),
FOREIGN KEY (`address_id`) REFERENCES `Address` (`id`)
);


