TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE roles RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE images RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE userRoles RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE verificationtoken RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO roles(roleId, roleName) VALUES (1, 'PROVIDER');
INSERT INTO roles(roleId, roleName) VALUES (2, 'USER');
INSERT INTO roles(roleId, roleName) VALUES (3, 'VERIFIED');
INSERT INTO roles(roleId, roleName) VALUES (4, 'NOT_VERIFIED');

INSERT INTO images(imgid, imageobject) VALUES (15, '0x5678');
INSERT INTO images(imgid, imageobject) VALUES (16, '0x5678');

-- Creating user with userid 1
INSERT INTO users(userid, username, usersurname, email, imgid, password) VALUES (1, 'usuario', 'uno', 'uno@mail.com', 15, 'contra1');
INSERT INTO users(userid, username, usersurname, email, imgid, password) VALUES (2, 'usuario2', 'dos', 'dos@mail.com', 16, 'contra2');

-- Adding default_roles to userid 1
INSERT INTO userRoles(roleid, userid) VALUES (2,1);
INSERT INTO userRoles(roleid, userid) VALUES (4,1);

-- Inserting a token to test get and delete
INSERT INTO verificationtoken(verifid, verifuserid, veriftoken, verifexpirationdate)  VALUES
(1, 2, '12345', '2020-03-23 00:00:00.000000')