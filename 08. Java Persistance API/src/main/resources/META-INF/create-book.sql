CREATE TABLE Book (id INT not null, title VARCHAR(255),	author VARCHAR(255), price FLOAT, primary key (id));
CREATE TABLE Author (id INT not null, name VARCHAR(255), primary key (id));
CREATE TABLE book05 (id INT not null, title VARCHAR(255),	author VARCHAR(255), price FLOAT, primary key (id));
CREATE TABLE author05 (id INT not null, name VARCHAR(255), birthDate DATE, primary key (id));
CREATE TABLE bookstore_table(gen_name VARCHAR(16) NOT NULL, gen_val INT NOT NULL);
INSERT into bookstore_table values ("book_id", 1);
INSERT into bookstore_table values ("author_id", 100);
