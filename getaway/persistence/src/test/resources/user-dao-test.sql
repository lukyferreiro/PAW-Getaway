TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE roles RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE images RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE userRoles RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO roles(roleId, roleName) VALUES (1, 'PROVIDER');
INSERT INTO roles(roleId, roleName) VALUES (2, 'USER');
INSERT INTO roles(roleId, roleName) VALUES (3, 'VERIFIED');
INSERT INTO roles(roleId, roleName) VALUES (4, 'NOT_VERIFIED');

-- Adding an image for updateProfileImg test
INSERT INTO images(imgid, imageobject) VALUES (1, '0x1234');
INSERT INTO images(imgid, imageobject) VALUES (15, '0x5678');

-- Creating user with userid 1
INSERT INTO users(userid, username, usersurname, email, imgid, password) VALUES (1, 'usuario', 'uno', 'uno@mail.com', 15, 'contra1');

-- Adding default_roles to userid 1
INSERT INTO userRoles(roleid, userid) VALUES (2,1);
INSERT INTO userRoles(roleid, userid) VALUES (4,1);