TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE roles RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE images RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE userRoles RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE passwordresettoken RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE verificationtoken RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO roles(roleName)
VALUES ('PROVIDER');
INSERT INTO roles(roleName)
VALUES ('USER');
INSERT INTO roles(roleName)
VALUES ('VERIFIED');
INSERT INTO roles(roleName)
VALUES ('NOT_VERIFIED');

INSERT INTO images(imgid, imageobject)
VALUES (15, null);
INSERT INTO images(imgid, imageobject)
VALUES (16, null);

-- Creating user with userid 1
INSERT INTO users(userid, username, usersurname, email, imgid, password)
VALUES (1, 'usuario', 'uno', 'uno@mail.com', 15, 'contra1');
INSERT INTO users(userid, username, usersurname, email, imgid, password)
VALUES (2, 'usuario', 'dos', 'dos@mail.com', 16, 'contra2');

-- Adding default_roles to userid 1
INSERT INTO userRoles(roleid, userid)
VALUES (2, 1);
INSERT INTO userRoles(roleid, userid)
VALUES (4, 1);

-- Inserting a token to test get and delete
INSERT INTO passwordresettoken(passtokenid, passtokenuserid, passtoken, passtokenexpirationdate)
VALUES (1, 1, '12345', '2020-03-23 00:00:00.000000')

-- Inserting a verification token to test get and delete
INSERT INTO verificationtoken(verifid, verifuserid, veriftoken, verifexpirationdate)
VALUES
    (1, 1, '12345', '2020-03-23 00:00:00.000000')