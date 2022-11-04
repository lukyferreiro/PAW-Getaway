CREATE TABLE IF NOT EXISTS categories
(
    categoryId SERIAL NOT NULL,
    categoryName VARCHAR(20) NOT NULL,
    PRIMARY KEY (categoryId),
    UNIQUE (categoryName)
);

CREATE TABLE IF NOT EXISTS countries
(
    countryId SERIAL NOT NULL,
    countryName VARCHAR(255) NOT NULL,
    PRIMARY KEY (countryId),
    UNIQUE (countryName)
);

CREATE TABLE IF NOT EXISTS cities
(
    cityId SERIAL NOT NULL,
    cityName VARCHAR(255) NOT NULL,
    countryId INT NOT NULL,
    PRIMARY KEY (cityId),
    FOREIGN KEY (countryId) REFERENCES countries (countryId) ON DELETE CASCADE
);

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
    imgId INT NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (userId),
    UNIQUE(email),
    FOREIGN KEY (imgId) REFERENCES images (imgId) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS experiences
(
    experienceId SERIAL NOT NULL,
    experienceName VARCHAR(50) NOT NULL,
    price DECIMAL,
    address VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    description TEXT,
    siteUrl TEXT,
    cityId INT NOT NULL,
    categoryId INT NOT NULL,
    userId INT NOT NULL,
    imgId INT NOT NULL,
    observable BOOLEAN NOT NULL DEFAULT TRUE,
    views INT NOT NULL DEFAULT 0,
    PRIMARY KEY (experienceId),
    UNIQUE(experienceName, address, cityId),
    FOREIGN KEY (cityId) REFERENCES cities (cityId) ON DELETE CASCADE,
    FOREIGN KEY (categoryId) REFERENCES categories (categoryId) ON DELETE CASCADE,
    FOREIGN KEY (userId) REFERENCES users (userId) ON DELETE CASCADE,
    FOREIGN KEY (imgId) REFERENCES images (imgId) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS reviews
(
    reviewId SERIAL NOT NULL,
    title TEXT NOT NULL,
    description TEXT NOT NULL,
    score INT NOT NULL,
    reviewDate DATE NOT NULL,
    experienceId INT NOT NULL,
    userId INT NOT NULL,
    PRIMARY KEY (reviewId),
    FOREIGN KEY (experienceId) REFERENCES experiences (experienceId) ON DELETE CASCADE,
    FOREIGN KEY (userId) REFERENCES users (userId) ON DELETE CASCADE
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

CREATE TABLE IF NOT EXISTS verificationToken
(
    verifId SERIAL,
    verifUserId INT NOT NULL,
    verifToken TEXT,
    verifExpirationDate TIMESTAMP NOT NULL,
    FOREIGN KEY (verifUserId) REFERENCES USERS (userId) ON DELETE CASCADE,
    PRIMARY KEY (verifId)
);

CREATE TABLE IF NOT EXISTS passwordResetToken
(
    passTokenId SERIAL,
    passTokenUserId INT NOT NULL,
    passToken TEXT,
    passTokenExpirationDate TIMESTAMP NOT NULL,
    FOREIGN KEY (passTokenUserId) REFERENCES USERS (userId) ON DELETE CASCADE,
    PRIMARY KEY (passTokenId)
);

CREATE TABLE IF NOT EXISTS favuserexperience(
    userId INT NOT NULL,
    experienceId INT NOT NULL,
    PRIMARY KEY (userId, experienceId),
    FOREIGN KEY (userId) REFERENCES users (userId) ON DELETE CASCADE,
    FOREIGN KEY (experienceId) REFERENCES experiences (experienceId) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS viewed
(
    experienceId INT NOT NULL,
    userId INT NOT NULL,
    PRIMARY KEY (experienceId, userId),
    FOREIGN KEY (experienceId) REFERENCES experiences (experienceId) ON DELETE CASCADE,
    FOREIGN KEY (userId) REFERENCES users (userId) ON DELETE CASCADE
    );