TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE experiences RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE cities RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE countries RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE categories RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE images RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE imagesExperiences RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO images(imgid, imageObject) VALUES(10, '0x1234');
INSERT INTO images(imgid, imageObject) VALUES(11, '0x5678');

INSERT INTO users(userid, username, usersurname, email, imgid, password) VALUES (1, 'owner', 'user', 'owner@mail.com', 10, 'contra1');
INSERT INTO users(userid, username, usersurname, email, imgid, password) VALUES (2, 'owner2', 'user2', 'owner2@mail.com', 11, 'contra2');

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

-- Add images (with value null) for consistency
INSERT INTO images(imgid, imageObject) VALUES(1, '0x9123');
INSERT INTO images(imgid, imageObject) VALUES(5, '0x4567');
INSERT INTO images(imgid, imageObject) VALUES(8, '0x8912');

INSERT INTO experiences(experienceid, experiencename, price, address, description, siteurl, cityid, categoryid, userid, email)
VALUES (1, 'testaventura', 0, 'diraventura', null, null, 1, 1, 1, 'owner@mail.com');
INSERT INTO experiences(experienceid, experiencename, price, address, description, siteurl, cityid, categoryid, userid, email)
VALUES (5, 'testnight', null, 'dirnight', null, null, 2, 5, 1, 'owner@mail.com');
INSERT INTO experiences(experienceid, experiencename, price, address, description, siteurl, cityid, categoryid, userid, email)
VALUES (8, 'testaventura3', 2000, 'diraventura3', null, null, 2, 1, 2, 'owner2@mail.com');

-- Add imagesExperiences for consistency
INSERT INTO imagesExperiences(imgId, experienceId, isCover) VALUES (1,1,true);
INSERT INTO imagesExperiences(imgId, experienceId, isCover) VALUES (5,5,true);
INSERT INTO imagesExperiences(imgId, experienceId, isCover) VALUES (8,8,true);
