CREATE DATABASE bookstoredb;
CREATE DATABASE onlineshoppingdb;
CREATE DATABASE universitydb;
CREATE DATABASE companydb;

GRANT ALL PRIVILEGES ON bookstoredb.* TO 'user'@'%';
GRANT ALL PRIVILEGES ON onlineshoppingdb.* TO 'user'@'%';
GRANT ALL PRIVILEGES ON universitydb.* TO 'user'@'%';
GRANT ALL PRIVILEGES ON companydb.* TO 'user'@'%';