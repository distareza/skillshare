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
INSERT INTO driver (id, license_key, name, gender, birth_date) values (2, 'TRANSACTION-20201113-21', 'Jack Richer', 		'Male', 	'2003-11-13');
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
INSERT INTO purchases_bills (statement_id, amount) values (10, 182.55);
INSERT INTO purchases_bills (statement_id, amount) values (12, 5);
INSERT INTO purchases_bills (statement_id, amount) values (13, 129.15);
INSERT INTO purchases_bills (statement_id, amount) values (14, 30);
INSERT INTO purchases_statement (id, item, payment_date) values (10, 'Electricity Usage', 	'2020-12-05');
INSERT INTO purchases_statement (id, item, payment_date) values (12, 'Water Charges', 		'2020-12-01');
INSERT INTO purchases_statement (id, item, payment_date) values (13, 'Internet Charge', 		'2020-12-07');
INSERT INTO purchases_statement (id, item, payment_date) values (14, 'Phone Usage', 			'2020-12-15');

-- initialize data for com.jpa.relationmapping.Mapping05OneToOneJoinTable
INSERT INTO purchases_invoice (id, amount) values (1, 1335);
INSERT INTO purchases_invoice (id, amount) values (2, 849);
INSERT INTO purchases_invoice (id, amount) values (3, 1398);
INSERT INTO purchases_invoice (id, amount) values (4, 477);
INSERT INTO purchases_order (id, product, quantity, order_date) values (10, 'iMac 24-inch M1', 	1, '2021-05-07');
INSERT INTO purchases_order (id, product, quantity, order_date) values (11, 'iPhone 12', 		1, '2020-11-13');
INSERT INTO purchases_order (id, product, quantity, order_date) values (12, 'Apple Watch 6', 	2, '2019-04-02');
INSERT INTO purchases_order (id, product, quantity, order_date) values (13, 'Original AirPod', 	3, '2020-08-31');
INSERT INTO purchases_mapping_order_invoice (invoice_id, order_id) values (1, 10);
INSERT INTO purchases_mapping_order_invoice (invoice_id, order_id) values (2, 11);
INSERT INTO purchases_mapping_order_invoice (invoice_id, order_id) values (3, 12);
INSERT INTO purchases_mapping_order_invoice (invoice_id, order_id) values (4, 13);


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




