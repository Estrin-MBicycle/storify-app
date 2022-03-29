INSERT INTO profile (id, name, surname, town, address, phone, cart_id)
VALUES (1, 'name', 'surname', 'town', 'address', 'phone', null);

INSERT INTO store (id, store_name, description, address, profile_id, deleted)
VALUES (1, 'store_name', 'description', 'address', 1, false);

INSERT INTO product(id, product_name, image, description, price, count, store_id)
VALUES(1, 'TestProduct', 'no image', 'Test', 55, 5, 1);

INSERT INTO purchase(id, purchase_date, price, unique_code, profile_id, delivered)
VALUES (1, CURDATE() - INTERVAL 5 DAY, 554, 'uniqueCode', 1, false);

INSERT INTO purchase(id, purchase_date, price, unique_code, profile_id, delivered)
VALUES (2, '2022-01-01', 554, 'uniqueCode1', 1, false);

INSERT INTO product_purchase(id, purchase, product, count)
VALUES (1, 1, 1, 1);

INSERT INTO product_purchase(id, purchase, product, count)
VALUES (2, 2, 1, 1);