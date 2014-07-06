# --- First database schema

# --- !Ups

CREATE TABLE users (
   id                   SERIAL PRIMARY KEY,
   username             VARCHAR(10) NOT NULL,
   password             VARCHAR(64) NOT NULL,
   first_name           VARCHAR(64) NOT NULL,
   last_name            VARCHAR(64) NOT NULL,
   email                VARCHAR(64) NOT NULL
);

CREATE TABLE applications (
   id                   SERIAL PRIMARY KEY,
   user_id              INTEGER NOT NULL,
   name                 VARCHAR(10) NOT NULL,
   version              VARCHAR(10) NOT NULL,
   foreign key (user_id) references users (id)
);

CREATE TABLE serials (
   id                   SERIAL PRIMARY KEY,
   application_id       INTEGER NOT NULL,
   serial_number        VARCHAR(64) NOT NULL,
   created_on           VARCHAR(64) NOT NULL,
   foreign key (application_id) references applications (id)
);

CREATE TABLE licenses (
   id                    SERIAL PRIMARY KEY,
   serial_id             INTEGER NOT NULL,
   created_on            VARCHAR(64) NOT NULL,
   active                VARCHAR(64) NOT NULL,
   licence_hash          VARCHAR(64) NOT NULL,
   public_key            VARCHAR(64) NOT NULL,
   private_key           VARCHAR(64) NOT NULL,
   foreign key (serial_id) references serials (id)
);

CREATE TABLE clients (
   id                   SERIAL PRIMARY KEY,
   username             VARCHAR(10) NOT NULL,
   password             VARCHAR(64) NOT NULL,
   first_name           VARCHAR(64) NOT NULL,
   last_name            VARCHAR(64) NOT NULL,
   email                VARCHAR(64) NOT NULL,
);

# --- !Downs

ALTER TABLE applications DROP CONSTRAINT applications_user_id_fkey;
ALTER TABLE serials DROP CONSTRAINT serials_application_id_fkey;
ALTER TABLE licenses DROP CONSTRAINT serials_serial_id_fkey;


DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS applications;
DROP TABLE IF EXISTS serials;
DROP TABLE IF EXISTS licenses;
DROP TABLE IF EXISTS clients;

