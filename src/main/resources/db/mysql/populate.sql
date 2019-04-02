-- ****** Create Restaurant Addresses --

-- ****** MCDONALDS ****
INSERT IGNORE INTO Address (address_1,city,state,country,location,`name`) VALUES ('1100 Fillmore St','San Francisco','CA','USA',POINT(37.778173,-122.4439419),'McDonalds on Fillmore');
INSERT IGNORE INTO Address (address_1,city,state,country,location,name) VALUES ('302 Potrero Ave','San Francisco','CA','USA',POINT(37.7654262,-122.410096),'McDonalds on Potrero');
INSERT IGNORE INTO Address (address_1,city,state,country,location,`name`) VALUES ('2801 Mission St','San Francisco','CA','USA',POINT(37.7520976,-122.4202066),'McDonalds on Mission');

-- ***** CHIPOTLES *****
INSERT IGNORE INTO Address (address_1,city,state,country,location,`name`) VALUES ('2675 Geary Blvd','San Francisco','CA','USA',POINT(37.7821724,-122.4486524),'Chipotle on Geary');
INSERT IGNORE INTO Address (address_1,city,state,country,location,`name`) VALUES ("232 O'Farrell St",'San Francisco','CA','USA',POINT(37.7865367,-122.4091356),"Chipotle on O'Farrell");
INSERT IGNORE INTO Address (address_1,city,state,country,location,`name`) VALUES ('50 California St','San Francisco','CA','USA',POINT(37.7940529,-122.3994977),'Chipotle on California');

-- ***** PANDA EXPRESS *****
INSERT IGNORE INTO Address (address_1,city,state,country,location,`name`) VALUES ('1480 Fillmore St','San Francisco','CA','USA',POINT(37.7831613,-122.4346283),'Panda on Fillmore');
INSERT IGNORE INTO Address (address_1,city,state,country,location,`name`) VALUES ('865 Market St','San Francisco','CA','USA',POINT(37.7839502,-122.4079934),'Panda on Market');

-- ***** App User Addresses *****
-- Lives at apple store @ union square
INSERT IGNORE INTO Address (address_1,city,state,country,location,`name`) VALUES ('300 Post St','San Francisco','CA','USA',POINT(37.7940529,-122.3994977),'Customer at union square sf');

INSERT IGNORE INTO Address (address_1,city,state,country,location,`name`) VALUES ('472 Water St','Oakland','CA','USA',POINT(37.7950691,-122.2799896),'Customer too far away, jack london sq');


-- ***** Create App User *****
INSERT IGNORE INTO Customer (first_name,last_name,phone,email,verified_email,verified_phone) VALUES ('Robbie','Irribarren','555-555-5555','email@domain.com',true,true);
INSERT IGNORE INTO Customer (first_name,last_name,phone,email,verified_email,verified_phone) VALUES ('William','Shakespear','555-555-5555','william@domain.com',true,true);

-- ***** Address for customer William *****
INSERT INTO CustomerAddress (customer_id,address_id) SELECT c.id, a.id FROM
(SELECT id FROM Address WHERE address_1 = '472 Water St' LIMIT 1) a,
(SELECT id FROM Customer WHERE first_name="William" LIMIT 1) c;

-- ***** Address for customer Robbie *****
INSERT INTO CustomerAddress (customer_id,address_id) SELECT c.id, a.id FROM
(SELECT id FROM Address WHERE address_1 = '300 Post St' LIMIT 1) a,
(SELECT id FROM Customer WHERE first_name="Robbie" LIMIT 1) c;

