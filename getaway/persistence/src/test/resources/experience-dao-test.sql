TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE experiences RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE cities RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE countries RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE categories RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE reviews RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE images RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE imagesExperiences RESTART IDENTITY AND COMMIT NO CHECK;

-- Add at least three users (in case we limit 1 review per experience per user)
INSERT INTO users(userid, username, usersurname, email, imgid, password) VALUES (1, 'owner', 'user', 'owner@mail.com', null, 'contra1');
INSERT INTO users(userid, username, usersurname, email, imgid, password) VALUES (2, 'owner2', 'user2', 'owner2@mail.com', null, 'contra2');
INSERT INTO users(userid, username, usersurname, email, imgid, password) VALUES (3, 'owner3', 'user3', 'owner3@mail.com', null, 'contra3');

-- Populate categories
INSERT INTO categories(categoryname) VALUES ('Aventura');
INSERT INTO categories(categoryname) VALUES ('Gastronomia');
INSERT INTO categories(categoryname) VALUES ('Hoteleria');
INSERT INTO categories(categoryname) VALUES ('Relax');
INSERT INTO categories(categoryname) VALUES ('Vida_nocturna');
INSERT INTO categories(categoryname) VALUES ('Historico');

-- Add country
INSERT INTO countries(countryname) VALUES ('Test Country');

-- Add at least two cities
INSERT INTO cities(cityname, countryid) VALUES('Test City One', 1);
INSERT INTO cities(cityname, countryid) VALUES('Test City Two', 1);

-- Add images (with value null), because all experiences are created that way from Service layer
INSERT INTO images(imgid, imageObject) VALUES(1, null);
INSERT INTO images(imgid, imageObject) VALUES(2, null);
INSERT INTO images(imgid, imageObject) VALUES(3, null);
INSERT INTO images(imgid, imageObject) VALUES(4, null);
INSERT INTO images(imgid, imageObject) VALUES(5, null);
INSERT INTO images(imgid, imageObject) VALUES(6, null);
INSERT INTO images(imgid, imageObject) VALUES(7, null);
INSERT INTO images(imgid, imageObject) VALUES(8, null);

-- Add 1 experience of each category
-- Add 3 to adventure with different price ranges and cities for filter testing
INSERT INTO experiences(experienceid, experiencename, price, address, description, siteurl, cityid, categoryid, userid, email)
VALUES (1, 'testaventura', 0, 'diraventura', null, null, 1, 1, 1, 'owner@mail.com');
INSERT INTO experiences(experienceid, experiencename, price, address, description, siteurl, cityid, categoryid, userid, email)
VALUES (2, 'testgastro', 1000, 'dirgastro', null, null, 1, 2, 1, 'owner@mail.com');
INSERT INTO experiences(experienceid, experiencename, price, address, description, siteurl, cityid, categoryid, userid, email)
VALUES (3, 'testhotel', 1000, 'dirhotel', null, null, 1, 3, 1, 'owner@mail.com');
INSERT INTO experiences(experienceid, experiencename, price, address, description, siteurl, cityid, categoryid, userid, email)
VALUES (4, 'testrelax', 10000, 'dirrelax', null, null, 2, 4, 1, 'owner@mail.com');
INSERT INTO experiences(experienceid, experiencename, price, address, description, siteurl, cityid, categoryid, userid, email)
VALUES (5, 'testnight', null, 'dirnight', null, null, 2, 5, 1, 'owner@mail.com');
INSERT INTO experiences(experienceid, experiencename, price, address, description, siteurl, cityid, categoryid, userid, email)
VALUES (6, 'testhist', 5000, 'dirhist', null, null, 2, 6, 2, 'owner2@mail.com');
INSERT INTO experiences(experienceid, experiencename, price, address, description, siteurl, cityid, categoryid, userid, email)
VALUES (7, 'testaventura2', 1500, 'diraventura2', null, null, 1, 1, 2, 'owner2@mail.com');
INSERT INTO experiences(experienceid, experiencename, price, address, description, siteurl, cityid, categoryid, userid, email)
VALUES (8, 'testaventura3', 2000, 'diraventura3', null, null, 2, 1, 2, 'owner2@mail.com');

INSERT INTO imagesExperiences(imgId, experienceId, isCover) VALUES (1,1,true);
INSERT INTO imagesExperiences(imgId, experienceId, isCover) VALUES (2,2,true);
INSERT INTO imagesExperiences(imgId, experienceId, isCover) VALUES (3,3,true);
INSERT INTO imagesExperiences(imgId, experienceId, isCover) VALUES (4,4,true);
INSERT INTO imagesExperiences(imgId, experienceId, isCover) VALUES (5,5,true);
INSERT INTO imagesExperiences(imgId, experienceId, isCover) VALUES (6,6,true);
INSERT INTO imagesExperiences(imgId, experienceId, isCover) VALUES (7,7,true);
INSERT INTO imagesExperiences(imgId, experienceId, isCover) VALUES (8,8,true);


-- Add 3 reviews to each adventure for review specific filter

-- ADV 1: avg_score=2
INSERT INTO reviews(reviewid, title, description, score, experienceid, reviewdate, userid)
VALUES (1, 'Title1', 'Desc1', 1, 1, '2022-01-01', 1);
INSERT INTO reviews(reviewid, title, description, score, experienceid, reviewdate, userid)
VALUES (2, 'Title2', 'Desc2', 2, 1, '2022-01-01', 2);
INSERT INTO reviews(reviewid, title, description, score, experienceid, reviewdate, userid)
VALUES (3, 'Title3', 'Desc3', 3, 1, '2022-01-01', 3);

-- ADV 2: avg_score=4
INSERT INTO reviews(reviewid, title, description, score, experienceid, reviewdate, userid)
VALUES (4, 'Title4', 'Desc4', 3, 7, '2022-01-01', 1);
INSERT INTO reviews(reviewid, title, description, score, experienceid, reviewdate, userid)
VALUES (5, 'Title5', 'Desc5', 4, 7, '2022-01-01', 2);
INSERT INTO reviews(reviewid, title, description, score, experienceid, reviewdate, userid)
VALUES (6, 'Title6', 'Desc6', 5, 7, '2022-01-01', 3);

-- ADV 3: avg_score=5
INSERT INTO reviews(reviewid, title, description, score, experienceid, reviewdate, userid)
VALUES (7, 'Title7', 'Desc7', 5, 8, '2022-01-01', 1);
INSERT INTO reviews(reviewid, title, description, score, experienceid, reviewdate, userid)
VALUES (8, 'Title8', 'Desc8', 5, 8, '2022-01-01', 2);
INSERT INTO reviews(reviewid, title, description, score, experienceid, reviewdate, userid)
VALUES (9, 'Title9', 'Desc9', 5, 8, '2022-01-01', 3);
