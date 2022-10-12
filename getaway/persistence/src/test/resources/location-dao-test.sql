TRUNCATE TABLE countries RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE cities RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO countries(countryid, countryname) VALUES (1,'TestCountryFirst');
INSERT INTO countries(countryid, countryname) VALUES (2,'TestCountrySecond');

INSERT INTO cities(cityid, cityname, countryid) VALUES(1, 'Test FirstOne', 1);
INSERT INTO cities(cityid, cityname, countryid) VALUES(2, 'Test FirstTwo', 1);
INSERT INTO cities(cityid, cityname, countryid) VALUES(3, 'Test SecondOne', 2);
INSERT INTO cities(cityid, cityname, countryid) VALUES(4, 'Test SecondTwo', 2);