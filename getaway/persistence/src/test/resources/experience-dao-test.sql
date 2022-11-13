TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE experiences RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE cities RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE countries RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE categories RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE reviews RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE images RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE favuserexperience RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE viewed RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO roles(roleId, roleName) VALUES (1, 'PROVIDER');
INSERT INTO roles(roleId, roleName) VALUES (2, 'USER');
INSERT INTO roles(roleId, roleName) VALUES (3, 'VERIFIED');
INSERT INTO roles(roleId, roleName) VALUES (4, 'NOT_VERIFIED');

INSERT INTO images(imgid, imageObject) VALUES(15, '0x1234');
INSERT INTO images(imgid, imageObject) VALUES(16, '0x1234');
INSERT INTO images(imgid, imageObject) VALUES(17, '0x1234');

-- Add at least three users (in case we limit 1 review per experience per user)
INSERT INTO users(userid, username, usersurname, email, imgid, password) VALUES (1, 'owner', 'user', 'owner@mail.com', 15, 'contra1');
INSERT INTO users(userid, username, usersurname, email, imgid, password) VALUES (2, 'owner2', 'user2', 'owner2@mail.com', 16, 'contra2');
INSERT INTO users(userid, username, usersurname, email, imgid, password) VALUES (3, 'owner3', 'user3', 'owner3@mail.com', 17, 'contra3');

-- Adding default_roles to userid 1
INSERT INTO userRoles(roleid, userid) VALUES (2,1);
INSERT INTO userRoles(roleid, userid) VALUES (4,1);

-- Adding default_roles to userid 2
INSERT INTO userRoles(roleid, userid) VALUES (2,2);
INSERT INTO userRoles(roleid, userid) VALUES (4,2);

-- Adding default_roles to userid 3
INSERT INTO userRoles(roleid, userid) VALUES (2,3);
INSERT INTO userRoles(roleid, userid) VALUES (4,3);

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
INSERT INTO cities(cityid, cityname, countryid) VALUES(3, 'Test City Three', 1);

-- Add images (with value null) for consistency
INSERT INTO images(imgid, imageObject) VALUES(1, '0x1234');
INSERT INTO images(imgid, imageObject) VALUES(2, '0x1234');
INSERT INTO images(imgid, imageObject) VALUES(3, '0x1234');
INSERT INTO images(imgid, imageObject) VALUES(5, '0x1234');
INSERT INTO images(imgid, imageObject) VALUES(7, '0x1234');
INSERT INTO images(imgid, imageObject) VALUES(8, '0x1234');
INSERT INTO images(imgid, imageObject) VALUES(50, '0x1234');
INSERT INTO images(imgid, imageObject) VALUES(51, '0x1234');

-- Add 1 experience of each category
-- Add 3 to adventure with different price ranges and cities for filter testing
INSERT INTO experiences(experienceid, experiencename, price, address, description, siteurl, cityid, categoryid, userid, email, imgid, observable, views)
VALUES (1, 'testaventura', 0, 'diraventura', null, null, 1, 1, 1, 'owner@mail.com', 1, true, 0);
INSERT INTO experiences(experienceid, experiencename, price, address, description, siteurl, cityid, categoryid, userid, email, imgid, observable, views)
VALUES (2, 'testgastro', 0, 'dirgastro', null, null, 1, 2, 1, 'owner@mail.com', 2, true, 0);
INSERT INTO experiences(experienceid, experiencename, price, address, description, siteurl, cityid, categoryid, userid, email, imgid, observable, views)
VALUES (3, 'testgastro2', 0, 'dirgastro2', null, null, 1, 2, 2, 'owner@mail.com', 3, true, 0);
INSERT INTO experiences(experienceid, experiencename, price, address, description, siteurl, cityid, categoryid, userid, email, imgid, observable, views)
VALUES (7, 'testaventura2', 1500, 'diraventura2', null, null, 1, 1, 2, 'owner2@mail.com', 7, true, 0);
INSERT INTO experiences(experienceid, experiencename, price, address, description, siteurl, cityid, categoryid, userid, email, imgid, observable, views)
VALUES (8, 'testaventura3', 2000, 'diraventura3', null, null, 2, 1, 2, 'owner2@mail.com', 8, true, 0);

INSERT INTO experiences(experienceid, experiencename, price, address, description, siteurl, cityid, categoryid, userid, email, imgid, observable, views)
VALUES (50, 'todelete', null, 'delete', null, null, 3, 6, 2, 'owner@mail.com', 50, true, 0);
INSERT INTO experiences(experienceid, experiencename, price, address, description, siteurl, cityid, categoryid, userid, email, imgid, observable, views)
VALUES (51, 'torecommend', null, 'recommend1', null, null, 3, 1, 2, 'owner@mail.com', 51, true, 0);

-- User 1 favs
INSERT INTO favuserexperience(userid, experienceid) VALUES (1, 1);
INSERT INTO favuserexperience(userid, experienceid) VALUES (1, 2);
-- User 2 favs (experience with id number 8 will be recommended, experience with id 1 and 2 will be ignored)
INSERT INTO favuserexperience(userid, experienceid) VALUES (2, 1);
INSERT INTO favuserexperience(userid, experienceid) VALUES (2, 2);
INSERT INTO favuserexperience(userid, experienceid) VALUES (2, 8);

-- User 1 views
INSERT INTO viewed(userid, experienceid) VALUES (1,1)
INSERT INTO viewed(userid, experienceid) VALUES (1,2)
-- User 2 views (experience with id 7 will be recommended, experience with id 8 will be in alreadyAdded list)
INSERT INTO viewed(userid, experienceid) VALUES (2,1)
INSERT INTO viewed(userid, experienceid) VALUES (2,8)
INSERT INTO viewed(userid, experienceid) VALUES (2,7)

-- Add 3 reviews to each adventure for review specific filter

-- -- ADV 1: avg_score=2
INSERT INTO reviews(reviewid, title, description, score, experienceid, reviewdate, userid)
VALUES (1, 'Title1', 'Desc1', 1, 1, '2022-01-01', 1);
INSERT INTO reviews(reviewid, title, description, score, experienceid, reviewdate, userid)
VALUES (2, 'Title2', 'Desc2', 2, 1, '2022-01-01', 2);
INSERT INTO reviews(reviewid, title, description, score, experienceid, reviewdate, userid)
VALUES (3, 'Title3', 'Desc3', 3, 1, '2022-01-01', 3);
--
-- -- ADV 2: avg_score=4
INSERT INTO reviews(reviewid, title, description, score, experienceid, reviewdate, userid)
VALUES (4, 'Title4', 'Desc4', 3, 7, '2022-01-01', 1);
INSERT INTO reviews(reviewid, title, description, score, experienceid, reviewdate, userid)
VALUES (5, 'Title5', 'Desc5', 4, 7, '2022-01-01', 2);
INSERT INTO reviews(reviewid, title, description, score, experienceid, reviewdate, userid)
VALUES (6, 'Title6', 'Desc6', 5, 7, '2022-01-01', 3);
--
-- -- ADV 3: avg_score=5
INSERT INTO reviews(reviewid, title, description, score, experienceid, reviewdate, userid)
VALUES (7, 'Title7', 'Desc7', 5, 8, '2022-01-01', 1);
INSERT INTO reviews(reviewid, title, description, score, experienceid, reviewdate, userid)
VALUES (8, 'Title8', 'Desc8', 5, 8, '2022-01-01', 2);
INSERT INTO reviews(reviewid, title, description, score, experienceid, reviewdate, userid)
VALUES (9, 'Title9', 'Desc9', 5, 8, '2022-01-01', 3);
