TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE roles RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE images RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE userRoles RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO roles(roleId, roleName) VALUES (1, 'PROVIDER');
INSERT INTO roles(roleId, roleName) VALUES (2, 'USER');
INSERT INTO roles(roleId, roleName) VALUES (3, 'VERIFIED');
INSERT INTO roles(roleId, roleName) VALUES (4, 'NOT_VERIFIED');

-- Adding an image for updateProfileImg test
INSERT INTO images(imgid, imageobject) VALUES (15, '0x5678');
INSERT INTO images(imgid, imageobject) VALUES (16, '0x1234');
INSERT INTO images(imgid, imageobject) VALUES (17, '0x1234');
INSERT INTO images(imgid, imageobject) VALUES (18, '0x1234');

-- Creating user with userid 1
INSERT INTO users(userid, username, usersurname, email, imgid, password) VALUES (10, 'usuario', 'uno', 'uno@mail.com', 15, 'contra1');
INSERT INTO users(userid, username, usersurname, email, imgid, password) VALUES (20, 'usuario2', 'dos', 'dos@mail.com', 16, 'contra2');
INSERT INTO users(userid, username, usersurname, email, imgid, password) VALUES (30, 'usuario3', 'tres', 'tres@mail.com', 17, 'contra3');

-- Adding default_roles to userid 1
INSERT INTO userRoles(roleid, userid) VALUES (2,10);
INSERT INTO userRoles(roleid, userid) VALUES (4,10);

-- Adding default_roles to userid 2
INSERT INTO userRoles(roleid, userid) VALUES (2,20);
INSERT INTO userRoles(roleid, userid) VALUES (4,20);

-- Adding default_roles to userid 3
INSERT INTO userRoles(roleid, userid) VALUES (2,30);
INSERT INTO userRoles(roleid, userid) VALUES (4,30);

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

-- Add 1 experience of each category
-- Add 3 to adventure with different price ranges and cities for filter testing
INSERT INTO experiences(experienceid, experiencename, price, address, description, siteurl, cityid, categoryid, userid, email, imgid, observable, views)
VALUES (1, 'testaventura', 0, 'diraventura', null, null, 1, 1, 10, 'owner@mail.com', 1, true, 0);
INSERT INTO experiences(experienceid, experiencename, price, address, description, siteurl, cityid, categoryid, userid, email, imgid, observable, views)
VALUES (2, 'testgastro', 0, 'dirgastro', null, null, 1, 2, 10, 'owner@mail.com', 2, true, 0);

-- User 1 favs
INSERT INTO favuserexperience(userid, experienceid) VALUES (10, 1);
INSERT INTO favuserexperience(userid, experienceid) VALUES (10, 2);

-- User 1 views
INSERT INTO viewed(userid, experienceid) VALUES (10,1)
INSERT INTO viewed(userid, experienceid) VALUES (10,2)