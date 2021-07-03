CREATE DATABASE bookstoredb;
CREATE DATABASE onlineshoppingdb;

GRANT ALL PRIVILEGES ON bookstoredb.* TO 'user'@'%';
GRANT ALL PRIVILEGES ON onlineshoppingdb.* TO 'user'@'%';