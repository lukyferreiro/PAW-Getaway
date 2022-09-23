TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE experiences RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE argentinacities RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE countries RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE categories RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO users(userid, username, usersurname, email, imgid, password) VALUES (1, 'owner', 'user', 'owner@mail.com', null, 'contra1');

INSERT INTO categories(categoryname) VALUES ('Aventura');
INSERT INTO categories(categoryname) VALUES ('Gastronomia');
INSERT INTO categories(categoryname) VALUES ('Hoteleria');
INSERT INTO categories(categoryname) VALUES ('Relax');
INSERT INTO categories(categoryname) VALUES ('Vida_nocturna');
INSERT INTO categories(categoryname) VALUES ('Historico');

INSERT INTO countries(countryname) VALUES ('Test Country');

INSERT INTO argentinacities(cityname, countryid) VALUES('Test City One', 1);
INSERT INTO argentinacities(cityname, countryid) VALUES('Test City Two', 1);

INSERT INTO experiences(experienceid, experiencename, price, address, description, siteurl, cityid, categoryid, userid, hasimage)
VALUES (1, 'testaventura', 0, 'diraventura', null, null, 1, 1, 1, false);
INSERT INTO experiences(experienceid, experiencename, price, address, description, siteurl, cityid, categoryid, userid, hasimage)
VALUES (2, 'testgastro', 1000, 'dirgastro', null, null, 1, 2, 1, false);
INSERT INTO experiences(experienceid, experiencename, price, address, description, siteurl, cityid, categoryid, userid, hasimage)
VALUES (3, 'testhotel', 1000, 'dirhotel', null, null, 1, 3, 1, false);
INSERT INTO experiences(experienceid, experiencename, price, address, description, siteurl, cityid, categoryid, userid, hasimage)
VALUES (4, 'testrelax', 10000, 'dirrelax', null, null, 2, 4, 1, false);
INSERT INTO experiences(experienceid, experiencename, price, address, description, siteurl, cityid, categoryid, userid, hasimage)
VALUES (5, 'testnight', null, 'dirnight', null, null, 2, 5, 1, false);
INSERT INTO experiences(experienceid, experiencename, price, address, description, siteurl, cityid, categoryid, userid, hasimage)
VALUES (6, 'testhist', 5000, 'dirhist', null, null, 2, 6, 1, false);
INSERT INTO experiences(experienceid, experiencename, price, address, description, siteurl, cityid, categoryid, userid, hasimage)
VALUES (7, 'testaventura2', 1500, 'diraventura2', null, null, 2, 1, 1, false);

