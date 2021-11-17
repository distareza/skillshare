CREATE DATABASE FantabulousScienceFairDB;

USE FantabulousScienceFairDB;

CREATE TABLE registrations(
    student_id        INT NOT NULL,
    student_name      VARCHAR(255) NOT NULL,
    university_name   VARCHAR(255) NOT NULL,
    project_area      VARCHAR(25) NOT NULL,
    email             VARCHAR(255) NOT NULL,
    password VARCHAR(25) NOT NULL,
    PRIMARY KEY ( student_id )
);
