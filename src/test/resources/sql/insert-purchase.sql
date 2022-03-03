insert into profile(id, name, surname, town, address, phone, cart_id)
VALUES (55, 'test' , 'test', null, 'address', 55555, null);

insert into purchase(id, price, unique_code, profile_id, delivered) values (222,  554, 'uniqueCode', 55, false);