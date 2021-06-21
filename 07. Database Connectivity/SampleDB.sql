create database SampleDB;
GRANT ALL PRIVILEGES ON SampleDB.* TO 'user'@'%'

use SampleDB;

create table users ( 			 
		first_name varchar(50),  
		last_name varchar(50), 	
 		email varchar(50), 		 
 		phone_number varchar(50)
 	);
insert into users values('Sidney', 'Cohen', 'sidney_cohen84@hotmail.com', '555-390-7366');
insert into users values('Lucas', 'Brenner', 'locas_brenner84@hotmail.com', '555-110-8046');

CREATE TABLE employee_data (
    emp_id int,
    emp_name varchar(45),
    designation varchar(45),
    salary double,
    primary key (emp_id)
);

CREATE TABLE Products (
    product_id INT,
    product_name VARCHAR(255),
    price DECIMAL,
    primary key (product_id)
);

truncate Products;

insert into Products values (101, 'Mother Board', 79), 
(102, 'Mouse', 15), (103, 'HDMI Cable', 5), (104, 'Keyboard', 12), (105, 'USB Cable', 3), (106, 'VGA Cable', 3), (107, 'LCD Monitor', 159);

CREATE TABLE ProductBrand (
    product_id INT,
    brand_name VARCHAR(255),
    primary key (product_id, brand_name)
);

INSERT INTO ProductBrand values (101, 'Qenel'), (102, 'Sonical'), (103, 'Diallonic'), (104, 'Sonical'), (105, 'Diallonic'), (106, 'Diallonic'), (107, 'Qenel');

CREATE TABLE ProductSupplier (
    product_id INT,
    supplier_name VARCHAR(255),
    primary key (product_id, supplier_name)
);

CREATE TABLE PuppyInfo (
    puppy_id INT,
    puppy_Breed VARCHAR(255),
    puppy_photo LONGBLOB,
    primary key (puppy_id)
);

INSERT into PuppyInfo (puppy_id, puppy_breed) values (102, 'Pug');
INSERT into PuppyInfo (puppy_id, puppy_breed) values (120, 'Labrador');

alter table PuppyInfo add puppy_desc longtext;



create table Student (
    stud_id INT,
    stud_name VARCHAR(255),
    dept_id INT,
    email VARCHAR(255),
    primary key (stud_id)
);


create table StudentDepartment (
    dept_id INT,
    stud_id INT,
    primary key (dept_id, stud_id),
    foreign key (stud_id) references Student(stud_id)
);


CREATE PROCEDURE Create_Tables (IN stud_id INT, stud_name VARCHAR(255), dept_id INT, email VARCHAR(255))
BEGIN
    INSERT INTO Student (stud_id, stud_name, dept_id, email) VALUES (stud_id, stud_name, dept_id, email);
    INSERT INTO StudentDepartment (dept_id, stud_id) VALUES (dept_id, stud_id);
END;
    
call Create_Tables(101, 'Alan Alford', 10020, 'alan_alford63@hotmail.com');

