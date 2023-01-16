TRUNCATE TABLE images RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO images(imgid, imageObject, imgMimeType) VALUES(1, '0x1234', 'JPG');
INSERT INTO images(imgid, imageObject, imgMimeType) VALUES(2, '0x5678', 'JPG');
INSERT INTO images(imgid, imageObject, imgMimeType) VALUES(3, '0x1256', 'JPG');
