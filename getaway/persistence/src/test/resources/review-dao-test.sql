TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE experiences RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE cities RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE countries RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE categories RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE reviews RESTART IDENTITY AND COMMIT NO CHECK;

-- Add at least three users (in case we limit 1 review per experience per user)
INSERT INTO users(userid, username, usersurname, email, imgid, password) VALUES (1, 'owner', 'user', 'owner@mail.com', null, 'contra1');
INSERT INTO users(userid, username, usersurname, email, imgid, password) VALUES (2, 'owner2', 'user2', 'owner2@mail.com', null, 'contra2');
INSERT INTO users(userid, username, usersurname, email, imgid, password) VALUES (3, 'owner3', 'user3', 'owner3@mail.com', null, 'contra3');
-- Populate categories
INSERT INTO categories(categoryid, categoryname) VALUES (1, 'Aventura');
INSERT INTO categories(categoryid, categoryname) VALUES (2, 'Gastronomia');
INSERT INTO categories(categoryid, categoryname) VALUES (3, 'Hoteleria');
INSERT INTO categories(categoryid, categoryname) VALUES (4, 'Relax');
INSERT INTO categories(categoryid, categoryname) VALUES (5, 'Vida_nocturna');
INSERT INTO categories(categoryid, categoryname) VALUES (6, 'Historico');

-- Add country
INSERT INTO countries(countryid, countryname) VALUES (1, 'Test Country');

-- Add at least two cities
INSERT INTO cities(cityid, cityname, countryid) VALUES(1, 'Test City One', 1);
INSERT INTO cities(cityid, cityname, countryid) VALUES(2, 'Test City Two', 1);

INSERT INTO images(imgid, imageObject) VALUES(1, '0x1234');
INSERT INTO images(imgid, imageObject) VALUES(7, '0x1234');
INSERT INTO images(imgid, imageObject) VALUES(8, '0x1234');

-- Add 3 to adventure with different price ranges and cities for filter testing
INSERT INTO experiences(experienceid, experiencename, price, address, description, siteurl, cityid, categoryid, userid, email, imgid, observable, views)
VALUES (1, 'testaventura', 0, 'diraventura', null, null, 1, 1, 1, 'owner@mail.com', 1, true, 0);
-- INSERT INTO experiences(experienceid, experiencename, price, address, description, siteurl, cityid, categoryid, userid, email, imgid,observable, views)
-- VALUES (7, 'testaventura2', 1500, 'diraventura2', null, null, 1, 1, 2, 'owner2@mail.com', 7, true, 0);
-- INSERT INTO experiences(experienceid, experiencename, price, address, description, siteurl, cityid, categoryid, userid, email, imgid, observable, views)
-- VALUES (8, 'testaventura3', 2000, 'diraventura3', null, null, 2, 1, 2, 'owner2@mail.com', 8, true, 0);

-- Add 3 reviews to each adventure for review specific filter

-- ADV 1: avg_score=2
INSERT INTO reviews(reviewid, title, description, score, experienceid, reviewdate, userid)
VALUES (1, 'Title1', 'Desc1', 1, 1, '2022-01-01 00:00:00', 1);
INSERT INTO reviews(reviewid, title, description, score, experienceid, reviewdate, userid)
VALUES (2, 'Title2', 'Desc2', 2, 1, '2022-01-01 00:00:00', 2);
INSERT INTO reviews(reviewid, title, description, score, experienceid, reviewdate, userid)
VALUES (3, 'Title3', 'Desc3', 3, 1, '2022-01-01 00:00:00', 3);