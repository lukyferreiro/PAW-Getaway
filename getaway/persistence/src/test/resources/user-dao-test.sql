TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE roles RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE images RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE userRoles RESTART IDENTITY AND COMMIT NO CHECK;

CREATE TABLE IF NOT EXISTS images
(
    imgId SERIAL NOT NULL,
    imageObject BYTEA,
    PRIMARY KEY (imgId)
    );

CREATE TABLE IF NOT EXISTS users
(
    userId SERIAL NOT NULL,
    userName VARCHAR(50) NOT NULL,
    userSurname VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL,
    imgId INT,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (userId),
    UNIQUE(email),
    FOREIGN KEY (imgId) REFERENCES images (imgId) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS roles
(
    roleId SERIAL NOT NULL,
    roleName VARCHAR(20) NOT NULL,
    PRIMARY KEY (roleId),
    UNIQUE (roleName)
    );

CREATE TABLE IF NOT EXISTS userRoles
(
    roleId INT NOT NULL,
    userId INT NOT NULL,
    PRIMARY KEY (userId, roleId),
    FOREIGN KEY (userId) REFERENCES users (userId) ON DELETE CASCADE,
    FOREIGN KEY (roleId) REFERENCES roles (roleId) ON DELETE CASCADE
    );

INSERT INTO roles(roleName) VALUES ('PROVIDER');
INSERT INTO roles(roleName) VALUES ('USER');
INSERT INTO roles(roleName) VALUES ('VERIFIED');
INSERT INTO roles(roleName) VALUES ('NOT_VERIFIED');

-- Creating user with userid 1
INSERT INTO users(userid, username, usersurname, email, imgid, password) VALUES (1, 'usuario', 'uno', 'uno@mail.com', null, 'contra1');

-- Adding default_roles to userid 1
INSERT INTO userRoles(roleid, userid) VALUES (2,1);
INSERT INTO userRoles(roleid, userid) VALUES (4,1);

-- Adding an image for updateProfileImg test
INSERT INTO images(imgid, imageobject) VALUES (15, null)