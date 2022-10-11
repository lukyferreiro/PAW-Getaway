TRUNCATE TABLE categories RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO categories(categoryid, categoryname) VALUES (1, 'Aventura');
INSERT INTO categories(categoryid, categoryname) VALUES (2, 'Gastronomia');
INSERT INTO categories(categoryid, categoryname) VALUES (3, 'Hoteleria');
INSERT INTO categories(categoryid, categoryname) VALUES (4, 'Relax');
INSERT INTO categories(categoryid, categoryname) VALUES (5, 'Vida_nocturna');
INSERT INTO categories(categoryid, categoryname) VALUES (6, 'Historico');