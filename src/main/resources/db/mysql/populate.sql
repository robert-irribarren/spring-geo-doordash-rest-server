-- ****** Create Restaurant Addresses --
SET @mcdonald_image = 'https://armhc.org/wp-content/uploads/2016/02/ronald-mcdonald-house-charities-atlanta-mcdonalds-logo.450.jpg';
SET @chipotle_image = 'https://image.cnbcfm.com/api/v1/image/103987468-CMG_Chorizo_Burrito_4x62.jpg?v=1529472850&w=740&h=493';
SET @panda_image = 'https://media-cdn.tripadvisor.com/media/photo-w/04/ab/76/b4/panda-express.jpg';
-- ****** MCDONALDS ****
INSERT IGNORE INTO Address (address_1,city,state,country,location,`name`) VALUES ('1100 Fillmore St','San Francisco','CA','USA',ST_SRID(POINT(-122.4439419,37.778173), 4326),'McDonalds on Fillmore');
INSERT IGNORE INTO Address (address_1,city,state,country,location,`name`) VALUES ('302 Potrero Ave','San Francisco','CA','USA',ST_SRID(POINT(-122.410096,37.7654262), 4326),'McDonalds on Potrero');
INSERT IGNORE INTO Address (address_1,city,state,country,location,`name`) VALUES ('2801 Mission St','San Francisco','CA','USA',ST_SRID(POINT(-122.4202066,37.7520976), 4326),'McDonalds on Mission');

INSERT IGNORE INTO Merchant (`name`,phone,address_id,banner_image_url) VALUES ('McDonalds','555-555-5555',(
SELECT id FROM Address WHERE address_1='1100 Fillmore St'
),@mcdonald_image);

INSERT IGNORE INTO Merchant (`name`,phone,address_id,banner_image_url) VALUES ('McDonalds','555-555-5555',(
SELECT id FROM Address WHERE address_1='302 Potrero Ave'
),@mcdonald_image);

INSERT IGNORE INTO Merchant (`name`,phone,address_id,banner_image_url) VALUES ('McDonalds','555-555-5555',(
SELECT id FROM Address WHERE address_1='2801 Mission St'
),@mcdonald_image);

-- ***** CHIPOTLES *****
INSERT IGNORE INTO Address (address_1,city,state,country,location,`name`) VALUES ('2675 Geary Blvd','San Francisco','CA','USA',ST_SRID(POINT(-122.4486524,37.7821724), 4326),'Chipotle on Geary');
INSERT IGNORE INTO Address (address_1,city,state,country,location,`name`) VALUES ("232 O'Farrell St",'San Francisco','CA','USA',ST_SRID(POINT(-122.4091356,37.7865367), 4326),"Chipotle on O'Farrell");
INSERT IGNORE INTO Address (address_1,city,state,country,location,`name`) VALUES ('50 California St','San Francisco','CA','USA',ST_SRID(POINT(-122.3994977,37.7940529), 4326),'Chipotle on California');

INSERT IGNORE INTO Merchant (`name`,phone,address_id,banner_image_url) VALUES ('Chipotle','555-555-5555',(
SELECT id FROM Address WHERE address_1='2675 Geary Blvd'
),@chipotle_image);

INSERT IGNORE INTO Merchant (`name`,phone,address_id,banner_image_url) VALUES ('Chipotle','555-555-5555',(
SELECT id FROM Address WHERE address_1="232 O'Farrell St"
),@chipotle_image);

INSERT IGNORE INTO Merchant (`name`,phone,address_id,banner_image_url) VALUES ('Chipotle','555-555-5555',(
SELECT id FROM Address WHERE address_1='50 California St'
),@chipotle_image);

-- ***** PANDA EXPRESS *****
INSERT IGNORE INTO Address (address_1,city,state,country,location,`name`) VALUES ('1480 Fillmore St','San Francisco','CA','USA',ST_SRID(POINT(-122.4346283,37.7831613), 4326),'Panda on Fillmore');
INSERT IGNORE INTO Address (address_1,city,state,country,location,`name`) VALUES ('865 Market St','San Francisco','CA','USA',ST_SRID(POINT(-122.4079934,37.7839502), 4326),'Panda on Market');

INSERT IGNORE INTO Merchant (`name`,phone,address_id,banner_image_url) VALUES ('Panda Express','555-555-5555',(
SELECT id FROM Address WHERE address_1='1480 Fillmore St'
),@chipotle_image);

INSERT IGNORE INTO Merchant (`name`,phone,address_id,banner_image_url) VALUES ('Panda Express','555-555-5555',(
SELECT id FROM Address WHERE address_1='865 Market St'
),@chipotle_image);

-- ***** App User Addresses *****
-- Lives at apple store @ union square
INSERT IGNORE INTO Address (address_1,city,state,country,location,`name`) VALUES ('300 Post St','San Francisco','CA','USA',ST_SRID(POINT(-122.3994977,37.7940529), 4326),'Customer at union square sf');

INSERT IGNORE INTO Address (address_1,city,state,country,location,`name`) VALUES ('472 Water St','Oakland','CA','USA',ST_SRID(POINT(-122.2799896,37.7950691), 4326),'Customer too far away, jack london sq');


-- ***** Create App User *****
INSERT IGNORE INTO Customer (first_name,last_name,phone,email,verified_email,verified_phone) VALUES ('Robbie','Irribarren','555-555-5555','email@domain.com',true,true);
INSERT IGNORE INTO Customer (first_name,last_name,phone,email,verified_email,verified_phone) VALUES ('William','Shakespear','555-555-5555','william@domain.com',true,true);

-- ***** Address for customer William *****
INSERT INTO CustomerAddressMap (customer_id,address_id) SELECT c.id, a.id FROM
(SELECT id FROM Address WHERE address_1 = '472 Water St' LIMIT 1) a,
(SELECT id FROM Customer WHERE first_name="William" LIMIT 1) c;

-- ***** Address for customer Robbie *****
INSERT INTO CustomerAddressMap (customer_id,address_id) SELECT c.id, a.id FROM
(SELECT id FROM Address WHERE address_1 = '300 Post St' LIMIT 1) a,
(SELECT id FROM Customer WHERE first_name="Robbie" LIMIT 1) c;

-- ***** Create random 1000 Customers and 1000 CustomerAddressMaps *****
CALL generate_test_users_and_addresses(1000)


