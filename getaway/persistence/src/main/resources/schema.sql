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

CREATE TABLE IF NOT EXISTS roles
(
    roleId SERIAL NOT NULL,
    roleName VARCHAR(20) NOT NULL,
    PRIMARY KEY (roleId),
    UNIQUE (roleName)
    );

CREATE TABLE countries
(
    countryId SERIAL NOT NULL,
    countryName VARCHAR(255) NOT NULL,
    PRIMARY KEY (countryId),
    UNIQUE (countryName)
);

CREATE TABLE cities
(
    cityId SERIAL NOT NULL,
    cityName VARCHAR(255) NOT NULL,
    countryId INT NOT NULL,
    PRIMARY KEY (cityId),
    FOREIGN KEY (countryId) REFERENCES countries (countryId) ON DELETE CASCADE,
    UNIQUE (countryId)
);

CREATE TABLE images
(
    imgId SERIAL,
    image BLOB,
    PRIMARY KEY (imgId),
);

CREATE TABLE users
(
    userId SERIAL,
    userName VARCHAR(50) NOT NULL,
    userSurname VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL,
    imgId INT,
    role_id INT NOT NULL,
    password VARCHAR(255),
    PRIMARY KEY (userId),
    UNIQUE(email),
    FOREIGN KEY (roleId) REFERENCES roles (roleId) ON DELETE CASCADE,
    FOREIGN KEY (imgId) REFERENCES images (imgId)
);

CREATE TABLE experiences
(
    experienceId SERIAL,
    experienceName VARCHAR(255) NOT NULL,
    price DECIMAL NOT NULL,
    address VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    cityId INT NOT NULL,
    categoryId INT NOT NULL,
    userId INT NOT NULL,
    PRIMARY KEY (experienceId),
    FOREIGN KEY (cityId) REFERENCES cities (cityId) ON DELETE CASCADE,
    FOREIGN KEY (categoryId) REFERENCES categories (categoryId) ON DELETE ,
    FOREIGN KEY (userId) REFERENCES users (userId) ON DELETE CASCADE
);

CREATE TABLE imagesExperiences
(
    imgId INT NOT NULL,
    experienceId INT NOT NULL,
    isCover BOOLEAN NOT NULL,
    PRIMARY KEY (imgId),
    FOREIGN KEY (imgId) REFERENCES images (imgId) ON DELETE CASCADE
        FOREIGN KEY (experienceId) REFERENCES experiences (experienceId) ON DELETE CASCADE
);

CREATE TABLE tagExperience
(
    tagId INT NOT NULL,
    experienceId INT NOT NULL,
    FOREIGN KEY (tagId) REFERENCES tags (tagId) ON DELETE CASCADE,
    FOREIGN KEY (experienceId) REFERENCES experiences (experienceId) ON DELETE CASCADE
);

CREATE TABLE reviews
(
    reviewId SERIAL,
    description TEXT,
    score INT NOT NULL,
    experienceId INT NOT NULL,
    reviewDate DATE NOT NULL,
    PRIMARY KEY (reviewId),
    FOREIGN KEY (experienceId) REFERENCES experiences (experienceId) ON DELETE CASCADE
);
