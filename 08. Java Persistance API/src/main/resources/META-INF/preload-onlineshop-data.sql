-- initialize data for com.jpa.relationmapping.Mapping01OneToOneUnidirectional
INSERT INTO invoice (id, amount) values (1, 699.59);
INSERT INTO invoice (id, amount) values (2, 67.20);
INSERT INTO invoice (id, amount) values (3, 29.56);
INSERT INTO invoice (id, amount) values (4, 125.00);
INSERT INTO orders (id, product, quantity, order_date, invoice_id) values (1, "iPhone 6S", 			1, "2020-02-03", 1);
INSERT INTO orders (id, product, quantity, order_date, invoice_id) values (2, "Nike Sneakers", 		2, "2020-03-05", 2);
INSERT INTO orders (id, product, quantity, order_date, invoice_id) values (3, "Power Cord Cable",	4, "2020-12-15", 3);
INSERT INTO orders (id, product, quantity, order_date, invoice_id) values (4, "Head Phone", 			1, "2020-08-17", 4);

-- initialize data for com.jpa.relationmapping.Mapping02OneToOneJoinColumn
INSERT INTO driving_license (id, license_no, license_type, expiry_date) values (1, 'TRANSACTION-20210507-01', 'Car'	,		'2025-05-07' );
INSERT INTO driving_license (id, license_no, license_type, expiry_date) values (2, 'TRANSACTION-20201113-21', 'Motorbike',	'2026-11-13' );
INSERT INTO driving_license (id, license_no, license_type, expiry_date) values (3, 'TRANSACTION-20190402-43', 'Bus',		'2025-04-02' );
INSERT INTO driving_license (id, license_no, license_type, expiry_date) values (4, 'TRANSACTION-20200831-74', 'Car',		'2024-08-31' );
INSERT INTO driver (id, license_key, name, gender, birth_date) values (1, 'TRANSACTION-20210507-01', 'Matt Robinson', 	'Male', 	'1998-05-07');
INSERT INTO driver (id, license_key, name, gender, birth_date) values (2, 'TRANSACTION-20201113-21', 'Jack Richer', 	'Male', 	'2003-11-13');
INSERT INTO driver (id, license_key, name, gender, birth_date) values (3, 'TRANSACTION-20190402-43', 'Jessica Parker', 	'Female',	'2005-04-02');
INSERT INTO driver (id, license_key, name, gender, birth_date) values (4, 'TRANSACTION-20200831-74', 'Chad Groom', 		'Male', 	'2008-08-31');

-- initialize data for com.jpa.relationmapping.Mapping03OneToOneBidirectional
INSERT INTO bills (id, amount) values (1, 182.55);
INSERT INTO bills (id, amount) values (2, 5);
INSERT INTO bills (id, amount) values (3, 129.15);
INSERT INTO bills (id, amount) values (4, 30);
INSERT INTO trans_statement (id, item, payment_date, bill_id) values (1, 'Electricity Usage', 	'2020-12-05', 1);
INSERT INTO trans_statement (id, item, payment_date, bill_id) values (2, 'Water Charges', 		'2020-12-01', 2);
INSERT INTO trans_statement (id, item, payment_date, bill_id) values (3, 'Internet Charge', 	'2020-12-07', 3);
INSERT INTO trans_statement (id, item, payment_date, bill_id) values (4, 'Phone Usage', 		'2020-12-15', 4);

-- initialize data for com.jpa.relationmapping.Mapping04OneToOneSharedMapping
INSERT INTO countries (capital_id, name, continents) values (10, 'Japan', 			'Asia');
INSERT INTO countries (capital_id, name, continents) values (12, 'China',  			'Asia');
INSERT INTO countries (capital_id, name, continents) values (13, 'Germany',			'Europe');
INSERT INTO countries (capital_id, name, continents) values (14, 'South Africa',	'Africa');
INSERT INTO capital (id, name, establish_date) values (10, 'Tokyo', 		'1889-12-05');
INSERT INTO capital (id, name, establish_date) values (12, 'Beijing', 		'1754-02-01');
INSERT INTO capital (id, name, establish_date) values (13, 'Berlin', 		'1945-04-07');
INSERT INTO capital (id, name, establish_date) values (14, 'Cape Town', 	'1956-08-15');

-- initialize data for com.jpa.relationmapping.Mapping05OneToOneJoinTable
INSERT INTO voter (id, name, birth_place, birth_date) values (1, 'John Wick', 	'	Jardani Jovonovich', '1964-09-02');
INSERT INTO voter (id, name, birth_place, birth_date) values (2, 'Natasha Romanova', 	'Stalingrad', '1984-04-05');
INSERT INTO voter (id, name, birth_place, birth_date) values (3, 'Linda P. Johnson',		 'Libya', '1951-10-21');
INSERT INTO voter (id, name, birth_place, birth_date) values (4, 'Anne P. Moberly', 	'New York', '1949-10-21');
INSERT INTO ballot (id, candidate, party, voting_date) values (10, 'Denis Graham', 'UK Independence Party', '2021-05-07');
INSERT INTO ballot (id, candidate, party, voting_date) values (11, 'Diane Morgan', 	'Labor Party', '2020-11-13');
INSERT INTO ballot (id, candidate, party, voting_date) values (12, 'Jim Nunn', 	'British National Party', '2019-04-02');
INSERT INTO ballot (id, candidate, party, voting_date) values (13, 'Carol Casby', 'Liberal Democrat', '2020-08-31');
INSERT INTO elections (voter_id, ballot_id) values (1, 10);
INSERT INTO elections (voter_id, ballot_id) values (2, 11);
INSERT INTO elections (voter_id, ballot_id) values (3, 12);
INSERT INTO elections (voter_id, ballot_id) values (4, 13);


-- initialize data for com.jpa.relationmapping.Mapping06OneToMany
INSERT INTO products (id, name, quantity) values (1, 'iPhone 6S', 		1);
INSERT INTO products (id, name, quantity) values (2, 'Nike Sneakers', 	2);
INSERT INTO products (id, name, quantity) values (3, 'iMac 24-inc M1', 	1);
INSERT INTO products (id, name, quantity) values (4, 'iPhone 12', 		1);
INSERT INTO products (id, name, quantity) values (5, 'Original AirPod', 3);
INSERT INTO products (id, name, quantity) values (6, 'Apple Watch 6', 	2);
INSERT INTO products_order(id, order_date) values(10, '2021-05-07');
INSERT INTO products_order(id, order_date) values(11, '2020-11-13');
INSERT INTO products_order(id, order_date) values(12, '2019-04-02');
INSERT INTO products_order_products(ProductOrder_id, products_id) values(10, 1);
INSERT INTO products_order_products(ProductOrder_id, products_id) values(10, 2);
INSERT INTO products_order_products(ProductOrder_id, products_id) values(10, 3);
INSERT INTO products_order_products(ProductOrder_id, products_id) values(11, 4);
INSERT INTO products_order_products(ProductOrder_id, products_id) values(12, 5);
INSERT INTO products_order_products(ProductOrder_id, products_id) values(12, 6);


-- initialize data for com.jpa.relationmapping.Mapping07OneToManyJoinTables
INSERT INTO my_products (id, name, quantity) values (1, 'iPhone 6S', 		1);
INSERT INTO my_products (id, name, quantity) values (2, 'Nike Sneakers', 	2);
INSERT INTO my_products (id, name, quantity) values (3, 'iMac 24-inc M1', 	1);
INSERT INTO my_products (id, name, quantity) values (4, 'iPhone 12', 		1);
INSERT INTO my_products (id, name, quantity) values (5, 'Original AirPod', 3);
INSERT INTO my_products (id, name, quantity) values (6, 'Apple Watch 6', 	2);
INSERT INTO my_products_order(id, order_date) values(10, '2021-05-07');
INSERT INTO my_products_order(id, order_date) values(11, '2020-11-13');
INSERT INTO my_products_order(id, order_date) values(12, '2019-04-02');
INSERT INTO my_mapping_products_order(order_id, product_id) values(10, 1);
INSERT INTO my_mapping_products_order(order_id, product_id) values(10, 2);
INSERT INTO my_mapping_products_order(order_id, product_id) values(10, 3);
INSERT INTO my_mapping_products_order(order_id, product_id) values(11, 4);
INSERT INTO my_mapping_products_order(order_id, product_id) values(12, 5);
INSERT INTO my_mapping_products_order(order_id, product_id) values(12, 6);


-- initialize data for com.jpa.relationmapping.Mapping08OneToManyEagerLoading
INSERT INTO my_2nd_products (id, name, quantity) values (1, 'iPhone 6S', 		1);
INSERT INTO my_2nd_products (id, name, quantity) values (2, 'Nike Sneakers', 	2);
INSERT INTO my_2nd_products (id, name, quantity) values (3, 'iMac 24-inc M1', 	1);
INSERT INTO my_2nd_products (id, name, quantity) values (4, 'iPhone 12', 		1);
INSERT INTO my_2nd_products (id, name, quantity) values (5, 'Original AirPod', 3);
INSERT INTO my_2nd_products (id, name, quantity) values (6, 'Apple Watch 6', 	2);
INSERT INTO my_2nd_order (id, order_date) values(10, '2021-05-07');
INSERT INTO my_2nd_order (id, order_date) values(11, '2020-11-13');
INSERT INTO my_2nd_order (id, order_date) values(12, '2019-04-02');
INSERT INTO my_2nd_mapping_products_order (order_id, product_id) values(10, 1);
INSERT INTO my_2nd_mapping_products_order (order_id, product_id) values(10, 2);
INSERT INTO my_2nd_mapping_products_order (order_id, product_id) values(10, 3);
INSERT INTO my_2nd_mapping_products_order (order_id, product_id) values(11, 4);
INSERT INTO my_2nd_mapping_products_order (order_id, product_id) values(12, 5);
INSERT INTO my_2nd_mapping_products_order (order_id, product_id) values(12, 6);

-- initialize data for com.jpa.relationmapping.Mapping09OneToManyLazyLoading
INSERT INTO my_3rd_products (id, name, quantity) values (1, 'iPhone 6S', 		1);
INSERT INTO my_3rd_products (id, name, quantity) values (2, 'Nike Sneakers', 	2);
INSERT INTO my_3rd_products (id, name, quantity) values (3, 'iMac 24-inc M1', 	1);
INSERT INTO my_3rd_products (id, name, quantity) values (4, 'iPhone 12', 		1);
INSERT INTO my_3rd_products (id, name, quantity) values (5, 'Original AirPod', 3);
INSERT INTO my_3rd_products (id, name, quantity) values (6, 'Apple Watch 6', 	2);
INSERT INTO my_3rd_order (id, order_date) values(10, '2021-05-07');
INSERT INTO my_3rd_order (id, order_date) values(11, '2020-11-13');
INSERT INTO my_3rd_order (id, order_date) values(12, '2019-04-02');
INSERT INTO my_3rd_mapping_products_order (order_id, product_id) values(10, 1);
INSERT INTO my_3rd_mapping_products_order (order_id, product_id) values(10, 2);
INSERT INTO my_3rd_mapping_products_order (order_id, product_id) values(10, 3);
INSERT INTO my_3rd_mapping_products_order (order_id, product_id) values(11, 4);
INSERT INTO my_3rd_mapping_products_order (order_id, product_id) values(12, 5);
INSERT INTO my_3rd_mapping_products_order (order_id, product_id) values(12, 6);

-- initialize data for com.jpa.relationmapping.Mapping10OneToManyJoinColumn
INSERT INTO my_4th_products (id, name, quantity, order_id) values (1, 'iPhone 6S', 			1,	10);
INSERT INTO my_4th_products (id, name, quantity, order_id) values (2, 'Nike Sneakers', 		2,	10);
INSERT INTO my_4th_products (id, name, quantity, order_id) values (3, 'iMac 24-inc M1', 	1,	10);
INSERT INTO my_4th_products (id, name, quantity, order_id) values (4, 'iPhone 12', 			1,	11);
INSERT INTO my_4th_products (id, name, quantity, order_id) values (5, 'Original AirPod',	3,	12);
INSERT INTO my_4th_products (id, name, quantity, order_id) values (6, 'Apple Watch 6', 		2,	12);
INSERT INTO my_4th_order (id, order_date) values(10, '2021-05-07');
INSERT INTO my_4th_order (id, order_date) values(11, '2020-11-13');
INSERT INTO my_4th_order (id, order_date) values(12, '2019-04-02');

-- initialize data for com.jpa.relationmapping.Mapping11RetrieveManyEntitiesInOrder
INSERT INTO my_5th_products (id, name, quantity, order_id) values (1, 'iPhone 6S', 			1,	10);
INSERT INTO my_5th_products (id, name, quantity, order_id) values (2, 'Nike Sneakers', 		2,	10);
INSERT INTO my_5th_products (id, name, quantity, order_id) values (3, 'iMac 24-inc M1', 	1,	10);
INSERT INTO my_5th_products (id, name, quantity, order_id) values (4, 'iPhone 12', 			1,	11);
INSERT INTO my_5th_products (id, name, quantity, order_id) values (5, 'Original AirPod',	3,	12);
INSERT INTO my_5th_products (id, name, quantity, order_id) values (6, 'Apple Watch 6', 		2,	12);
INSERT INTO my_5th_order (id, order_date) values(10, '2021-05-07');
INSERT INTO my_5th_order (id, order_date) values(11, '2020-11-13');
INSERT INTO my_5th_order (id, order_date) values(12, '2019-04-02');

-- initialize data for com.jpa.relationmapping.Mapping12PersistManyEntitiesInOrder
INSERT INTO my_6th_products (id, name, quantity, order_id, order_persistence) values (1, 'iPhone 6S', 			1,	10,	0);
INSERT INTO my_6th_products (id, name, quantity, order_id, order_persistence) values (2, 'Nike Sneakers', 		2,	10,	1);
INSERT INTO my_6th_products (id, name, quantity, order_id, order_persistence) values (3, 'iMac 24-inc M1', 		1,	10,	2);
INSERT INTO my_6th_products (id, name, quantity, order_id, order_persistence) values (4, 'iPhone 12', 			1,	11, 0);
INSERT INTO my_6th_products (id, name, quantity, order_id, order_persistence) values (5, 'Original AirPod',		3,	12, 0);
INSERT INTO my_6th_products (id, name, quantity, order_id, order_persistence) values (6, 'Apple Watch 6', 		2,	12, 1);
INSERT INTO my_6th_order (id, order_date) values(10, '2021-05-07');
INSERT INTO my_6th_order (id, order_date) values(11, '2020-11-13');
INSERT INTO my_6th_order (id, order_date) values(12, '2019-04-02');

-- initialize data for com.jpa.relationmapping.Mapping13ManyToOneUnidrectionalMapping
INSERT INTO my_7th_products (id, name, quantity, order_id) values (1, 'iPhone 6S', 			1,	10 );
INSERT INTO my_7th_products (id, name, quantity, order_id) values (2, 'Nike Sneakers', 		2,	10 );
INSERT INTO my_7th_products (id, name, quantity, order_id) values (3, 'iMac 24-inc M1', 	1,	10 );
INSERT INTO my_7th_products (id, name, quantity, order_id) values (4, 'iPhone 12', 			1,	11 );
INSERT INTO my_7th_products (id, name, quantity, order_id) values (5, 'Original AirPod',	3,	12 );
INSERT INTO my_7th_products (id, name, quantity, order_id) values (6, 'Apple Watch 6', 		2,	12 );
INSERT INTO my_7th_order (id, order_date) values(10, '2021-05-07');
INSERT INTO my_7th_order (id, order_date) values(11, '2020-11-13');
INSERT INTO my_7th_order (id, order_date) values(12, '2019-04-02');

-- initialize data for com.jpa.relationmapping.Mapping14ManyToOneMappingMultipleJoin
INSERT INTO my_8th_products (id, name, quantity, order_id, order_date) values (1, 'iPhone 6S', 			1,	10, '2021-05-07' );
INSERT INTO my_8th_products (id, name, quantity, order_id, order_date) values (2, 'Nike Sneakers', 		2,	10, '2021-05-07' );
INSERT INTO my_8th_products (id, name, quantity, order_id, order_date) values (3, 'iMac 24-inc M1', 	1,	10, '2021-05-07' );
INSERT INTO my_8th_products (id, name, quantity, order_id, order_date) values (4, 'iPhone 12', 			1,	11, '2020-11-13' );
INSERT INTO my_8th_products (id, name, quantity, order_id, order_date) values (5, 'Original AirPod',	3,	12, '2021-05-07' );
INSERT INTO my_8th_products (id, name, quantity, order_id, order_date) values (6, 'Apple Watch 6', 		2,	12, '2019-04-02' );
INSERT INTO my_8th_order (id, order_date) values(10, '2021-05-07');
INSERT INTO my_8th_order (id, order_date) values(11, '2020-11-13');
INSERT INTO my_8th_order (id, order_date) values(12, '2019-04-02');

-- initialize data for com.jpa.relationmapping.Mapping15ManyToOneJoinTable
INSERT INTO my_9th_products (id, name, quantity) values (1, 'iPhone 6S', 		1);
INSERT INTO my_9th_products (id, name, quantity) values (2, 'Nike Sneakers', 	2);
INSERT INTO my_9th_products (id, name, quantity) values (3, 'iMac 24-inc M1', 	1);
INSERT INTO my_9th_products (id, name, quantity) values (4, 'iPhone 12', 		1);
INSERT INTO my_9th_products (id, name, quantity) values (5, 'Original AirPod', 3);
INSERT INTO my_9th_products (id, name, quantity) values (6, 'Apple Watch 6', 	2);
INSERT INTO my_9th_order(id, order_date) values(10, '2021-05-07');
INSERT INTO my_9th_order(id, order_date) values(11, '2020-11-13');
INSERT INTO my_9th_order(id, order_date) values(12, '2019-04-02');
INSERT INTO my_9th_mapping(order_id, product_id) values(10, 1);
INSERT INTO my_9th_mapping(order_id, product_id) values(10, 2);
INSERT INTO my_9th_mapping(order_id, product_id) values(10, 3);
INSERT INTO my_9th_mapping(order_id, product_id) values(11, 4);
INSERT INTO my_9th_mapping(order_id, product_id) values(12, 5);
INSERT INTO my_9th_mapping(order_id, product_id) values(12, 6);

-- initialize data for com.jpa.relationmapping.Mapping16OneToManyAndManyToOneBidirectional
INSERT INTO my_10th_products (id, name, quantity, order_id) values (1, 'iPhone 6S', 		1, 10);
INSERT INTO my_10th_products (id, name, quantity, order_id) values (2, 'Nike Sneakers', 	2, 10);
INSERT INTO my_10th_products (id, name, quantity, order_id) values (3, 'iMac 24-inc M1', 	1, 10);
INSERT INTO my_10th_products (id, name, quantity, order_id) values (4, 'iPhone 12', 		1, 11);
INSERT INTO my_10th_products (id, name, quantity, order_id) values (5, 'Original AirPod', 	3, 12);
INSERT INTO my_10th_products (id, name, quantity, order_id) values (6, 'Apple Watch 6', 	2, 12);
INSERT INTO my_10th_order(id, order_date) values(10, '2021-05-07');
INSERT INTO my_10th_order(id, order_date) values(11, '2020-11-13');
INSERT INTO my_10th_order(id, order_date) values(12, '2019-04-02');

-- initialize data for com.jpa.relationmapping.Mapping17ConfiguringTheOwningAndOwenedSide
INSERT INTO my_11th_products (id, name, quantity, order_id) values (1, 'iPhone 6S', 		1, 10);
INSERT INTO my_11th_products (id, name, quantity, order_id) values (2, 'Nike Sneakers', 	2, 10);
INSERT INTO my_11th_products (id, name, quantity, order_id) values (3, 'iMac 24-inc M1', 	1, 10);
INSERT INTO my_11th_products (id, name, quantity, order_id) values (4, 'iPhone 12', 		1, 11);
INSERT INTO my_11th_products (id, name, quantity, order_id) values (5, 'Original AirPod', 	3, 12);
INSERT INTO my_11th_products (id, name, quantity, order_id) values (6, 'Apple Watch 6', 	2, 12);
INSERT INTO my_11th_order(id, order_date) values(10, '2021-05-07');
INSERT INTO my_11th_order(id, order_date) values(11, '2020-11-13');
INSERT INTO my_11th_order(id, order_date) values(12, '2019-04-02');

-- initialize data for com.jpa.relationmapping.Mapping18ManyToManyBidirectional
INSERT INTO customer (id, name) values (1, "Mac Gyver")
INSERT INTO customer (id, name) values (2, "Billy Jeans")
INSERT INTO customer (id, name) values (3, "Bruno Mars")
INSERT INTO customer (id, name) values (4, "Jennfer Lopez")
INSERT INTO customer (id, name) values (5, "Swift Tylor")
INSERT INTO my_12th_products (id, name, quantity) values (1, 'iPhone 6S', 		1);
INSERT INTO my_12th_products (id, name, quantity) values (2, 'Nike Sneakers', 	2);
INSERT INTO my_12th_products (id, name, quantity) values (3, 'iMac 24-inc M1', 	1);
INSERT INTO my_12th_products (id, name, quantity) values (4, 'iPhone 12', 		1);
INSERT INTO my_12th_products (id, name, quantity) values (5, 'Original AirPod', 3);
INSERT INTO customer_products (customer_id, product_id) values (1, 1);
INSERT INTO customer_products (customer_id, product_id) values (1, 2);
INSERT INTO customer_products (customer_id, product_id) values (1, 3);
INSERT INTO customer_products (customer_id, product_id) values (1, 4);
INSERT INTO customer_products (customer_id, product_id) values (1, 5);
INSERT INTO customer_products (customer_id, product_id) values (2, 1);
INSERT INTO customer_products (customer_id, product_id) values (2, 2);
INSERT INTO customer_products (customer_id, product_id) values (2, 3);
INSERT INTO customer_products (customer_id, product_id) values (2, 4);
INSERT INTO customer_products (customer_id, product_id) values (2, 5);
INSERT INTO customer_products (customer_id, product_id) values (3, 1);
INSERT INTO customer_products (customer_id, product_id) values (3, 2);
INSERT INTO customer_products (customer_id, product_id) values (3, 3);
INSERT INTO customer_products (customer_id, product_id) values (3, 4);
INSERT INTO customer_products (customer_id, product_id) values (3, 5);
INSERT INTO customer_products (customer_id, product_id) values (4, 1);
INSERT INTO customer_products (customer_id, product_id) values (4, 2);
INSERT INTO customer_products (customer_id, product_id) values (4, 3);
INSERT INTO customer_products (customer_id, product_id) values (4, 4);
INSERT INTO customer_products (customer_id, product_id) values (4, 5);
INSERT INTO customer_products (customer_id, product_id) values (5, 1);
INSERT INTO customer_products (customer_id, product_id) values (5, 2);
INSERT INTO customer_products (customer_id, product_id) values (5, 3);
INSERT INTO customer_products (customer_id, product_id) values (5, 4);
INSERT INTO customer_products (customer_id, product_id) values (5, 5);

-- initilaize data for com.jpa.query.Query01Native
INSERT INTO tb_categories values (221, 'Mobile Phones');
INSERT INTO tb_categories values (231, 'Fashion');
INSERT INTO tb_categories values (241, 'Home');
INSERT INTO tb_categories values (251, 'School');
INSERT INTO tb_products values (1001, 'iPhone 6S', 		699, 221);
INSERT INTO tb_products values (1002, 'Samsumg Galaxy', 299, 221);
INSERT INTO tb_products values (1003, 'Designer Skirt',  49, 231);
INSERT INTO tb_products values (1004, 'Jeans', 		  78.99, 231);
INSERT INTO tb_products values (1005, 'Scarf', 		  19.99, 231);
INSERT INTO tb_products values (1006, 'Belt', 			9.9, 231);
INSERT INTO tb_products values (1007, 'Sporinkler', 	 89, 241);
INSERT INTO tb_products values (1008, 'Notebook', 		  9, 241);
INSERT INTO tb_products values (1009, 'Pen', 		   4.99, 251);

-- initilaize data for com.jpa.query.Query09UsingPredefineQueries
INSERT INTO Categories values (221, 'Mobile Phones');
INSERT INTO Categories values (231, 'Fashion');
INSERT INTO Categories values (241, 'Home');
INSERT INTO Categories values (251, 'School');
INSERT INTO Product values (1001, 'iPhone 6S', 		699, 221);
INSERT INTO Product values (1002, 'Samsumg Galaxy', 299, 221);
INSERT INTO Product values (1003, 'Designer Skirt',  49, 231);
INSERT INTO Product values (1004, 'Jeans', 		  78.99, 231);
INSERT INTO Product values (1005, 'Scarf', 		  19.99, 231);
INSERT INTO Product values (1006, 'Belt', 			9.9, 231);
INSERT INTO Product values (1007, 'Sporinkler', 	 89, 241);
INSERT INTO Product values (1008, 'Notebook', 		  9, 241);
INSERT INTO Product values (1009, 'Pen', 		   4.99, 251);


