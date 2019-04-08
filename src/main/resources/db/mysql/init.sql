CREATE DATABASE IF NOT EXISTS doordash;

ALTER DATABASE doordash
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;

USE doordash;

-- ****************** SqlDBM: MySQL ******************;
-- ***************************************************;
DROP TABLE IF EXISTS `CustomerAddress`;
DROP TABLE IF EXISTS `Customer`;
DROP TABLE IF EXISTS `Merchant`;
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
 `location`  point NOT NULL SRID 4326,
 `name`      varchar(120) NOT NULL ,
PRIMARY KEY (`id`),
SPATIAL INDEX(`location`)
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

CREATE TABLE `Merchant`
(
 `id`               varchar(36) DEFAULT (uuid()) NOT NULL,
 `created_at`     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 `updated_at`     TIMESTAMP DEFAULT NOW() ON UPDATE NOW(),
 `address_id`       varchar(36) NOT NULL ,
 `name`             varchar(100) NOT NULL ,
 `phone`            varchar(32) ,
 `banner_image_url` varchar(256) ,
PRIMARY KEY (`id`),
FOREIGN KEY (`address_id`) REFERENCES `Address` (`id`)
);

-- ***** Create random 1000 Customers and 1000 CustomerAddresses *****
drop procedure if exists generate_test_users_and_addresses;
DELIMITER $$
CREATE PROCEDURE generate_test_users_and_addresses(num int)

   BEGIN
      DECLARE cnt INT Default 1 ;
      DECLARE rand_name varchar(64);
      DECLARE cust_id varchar(64);
      DECLARE addr_id varchar(64);
      START TRANSACTION;
      WHILE cnt<=num DO
		SET @rand_name = (SELECT CONCAT("test_",REPLACE(UUID(),'-',''))) ;
		SET @cust_id = UUID();
        SET @addr_id = UUID();
        # insert user
        INSERT IGNORE INTO Customer (id,first_name,last_name,phone,email,verified_email,verified_phone) VALUES (@cust_id,@rand_name,'test','555-555-5555','test_'+rand()+'@domain.com',true,true);

        # insert address
        INSERT IGNORE INTO Address (id,address_1,city,state,country,location,`name`) VALUES (@addr_id,'Grace Cathedral','San Francisco','CA','USA',ST_SRID(POINT(-122.4131572,37.7919387), 4326),'Test customer at grace cathedral');

        # insert assoc table entry
        INSERT INTO CustomerAddress (customer_id,address_id) VALUES(@cust_id,@addr_id);

         SET cnt=cnt+1;
	  END WHILE;
      COMMIT;
END $$