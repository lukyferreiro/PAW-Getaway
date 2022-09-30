TRUNCATE TABLE countries RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE cities RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO countries(countryname) VALUES ('TestCountryFirst');
INSERT INTO countries(countryname) VALUES ('TestCountrySecond');

INSERT INTO cities(cityname, countryid) VALUES('Test FirstOne', 1);
INSERT INTO cities(cityname, countryid) VALUES('Test FirstTwo', 1);
INSERT INTO cities(cityname, countryid) VALUES('Test SecondOne', 2);
INSERT INTO cities(cityname, countryid) VALUES('Test SecondTwo', 2);