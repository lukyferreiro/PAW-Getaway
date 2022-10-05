TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE experiences RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE cities RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE countries RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE categories RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE images RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE imagesExperiences RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO images(imgid, imageObject) VALUES(10, null);
INSERT INTO images(imgid, imageObject) VALUES(11, null);

INSERT INTO users(userid, username, usersurname, email, imgid, password) VALUES (1, 'owner', 'user', 'owner@mail.com', 10, 'contra1');
INSERT INTO users(userid, username, usersurname, email, imgid, password) VALUES (2, 'owner2', 'user2', 'owner2@mail.com', 11, 'contra2');

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

-- Add images (with value null) for consistency
INSERT INTO images(imgid, imageObject) VALUES(1, null);
INSERT INTO images(imgid, imageObject) VALUES(5, null);
INSERT INTO images(imgid, imageObject) VALUES(8, null);

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
