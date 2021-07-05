-- initialize data for com.jpa.relationmapping.Mapping01OneToOneUnidirectional
INSERT INTO invoice (id, amount) values (1, 699.59);
INSERT INTO invoice (id, amount) values (2, 67.20);
INSERT INTO invoice (id, amount) values (3, 29.56);
INSERT INTO invoice (id, amount) values (4, 125.00);
INSERT INTO product_order (id, product, quantity, orderDate, invoice_id) values (1, "iPhone 6S", 		1, "2020-02-03", 1);
INSERT INTO product_order (id, product, quantity, orderDate, invoice_id) values (2, "Nike Sneakers", 	2, "2020-03-05", 2);
INSERT INTO product_order (id, product, quantity, orderDate, invoice_id) values (3, "Power Cord Cable",	4, "2020-12-15", 3);
INSERT INTO product_order (id, product, quantity, orderDate, invoice_id) values (4, "Head Phone", 		1, "2020-08-17", 4);

-- initialize data for com.jpa.relationmapping.Mapping02OneToOneJoinColumn
INSERT INTO transactions (id, transaction_no, amount) values (1, 'TRANSACTION-20210507-01', 1335);
INSERT INTO transactions (id, transaction_no, amount) values (2, 'TRANSACTION-20201113-21', 849);
INSERT INTO transactions (id, transaction_no, amount) values (3, 'TRANSACTION-20190402-43', 1398);
INSERT INTO transactions (id, transaction_no, amount) values (4, 'TRANSACTION-20200831-74', 477);
INSERT INTO purchase_products (id, invoice_key, product, quantity, order_date) values (1, 'TRANSACTION-20210507-01', 'iMac 24-inch M1', 	1, '2021-05-07');
INSERT INTO purchase_products (id, invoice_key, product, quantity, order_date) values (2, 'TRANSACTION-20201113-21', 'iPhone 12', 			1, '2020-11-13');
INSERT INTO purchase_products (id, invoice_key, product, quantity, order_date) values (3, 'TRANSACTION-20190402-43', 'Apple Watch 6', 		2, '2019-04-02');
INSERT INTO purchase_products (id, invoice_key, product, quantity, order_date) values (4, 'TRANSACTION-20200831-74', 'Original AirPod', 	3, '2020-08-31');

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