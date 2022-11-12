TRUNCATE TABLE images RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO images(imgid, imageObject) VALUES(1, '0x1234');
INSERT INTO images(imgid, imageObject) VALUES(2, '0x5678');
INSERT INTO images(imgid, imageObject) VALUES(3, '0x1256');
