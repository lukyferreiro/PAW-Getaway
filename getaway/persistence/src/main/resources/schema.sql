CREATE TABLE IF NOT EXISTS tags
(
    tagId SERIAL NOT NULL,
    tagName VARCHAR(30) NOT NULL,
    PRIMARY KEY (tagId),
    UNIQUE (tagName)
);

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

CREATE TABLE IF NOT EXISTS argentinaCities
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
    imgId INT,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (userId),
    UNIQUE(email),
    FOREIGN KEY (roleId) REFERENCES roles (roleId) ON DELETE CASCADE,
    FOREIGN KEY (imgId) REFERENCES images (imgId) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS experiences
(
    experienceId SERIAL NOT NULL,
    experienceName VARCHAR(255) NOT NULL,
    price DECIMAL,
    address VARCHAR(255) NOT NULL,
    description TEXT,
    siteUrl TEXT,
    cityId INT NOT NULL,
    categoryId INT NOT NULL,
    userId INT NOT NULL,
    PRIMARY KEY (experienceId),
    FOREIGN KEY (cityId) REFERENCES cities (cityId) ON DELETE CASCADE,
    FOREIGN KEY (categoryId) REFERENCES categories (categoryId) ON DELETE CASCADE,
    FOREIGN KEY (userId) REFERENCES users (userId) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS views
(
    experienceId INT NOT NULL,
    viewCount INT,
    PRIMARY KEY (experienceId),
    FOREIGN KEY (experienceId) REFERENCES experiences (experienceId) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS imagesExperiences
(
    imgId INT NOT NULL,
    experienceId INT NOT NULL,
    isCover BOOLEAN NOT NULL,
    PRIMARY KEY (imgId),
    FOREIGN KEY (imgId) REFERENCES images (imgId) ON DELETE CASCADE,
    FOREIGN KEY (experienceId) REFERENCES experiences (experienceId) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS tagExperience
(
    tagId INT NOT NULL,
    experienceId INT NOT NULL,
    FOREIGN KEY (tagId) REFERENCES tags (tagId) ON DELETE CASCADE,
    FOREIGN KEY (experienceId) REFERENCES experiences (experienceId) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS reviews
(
    reviewId SERIAL NOT NULL,
    title TEXT NOT NULL,
    description TEXT NOT NULL,
    score INT NOT NULL,
    experienceId INT NOT NULL,
    reviewDate DATE NOT NULL,
    PRIMARY KEY (reviewId),
    FOREIGN KEY (experienceId) REFERENCES experiences (experienceId) ON DELETE CASCADE
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
    PRIMARY KEY (userId),
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