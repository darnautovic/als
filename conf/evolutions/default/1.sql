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

CREATE TABLE clients (
   id                   SERIAL PRIMARY KEY,
   first_name           VARCHAR(64) NOT NULL,
   last_name            VARCHAR(64) NOT NULL,
   company              VARCHAR(64) NOT NULL
 );

CREATE TABLE keys (
   id                   SERIAL PRIMARY KEY,
   public_key            VARCHAR(1024) NOT NULL,
   private_key           VARCHAR(1024) NOT NULL
);

CREATE TABLE applications (
   id                   SERIAL PRIMARY KEY,
   user_id              INTEGER NOT NULL,
   key_id               INTEGER NOT NULL,
   name                 VARCHAR(20) NOT NULL,
   version              VARCHAR(10) NOT NULL,
   foreign key (user_id) references users (id),
   foreign key (key_id) references keys(id)
);


CREATE TABLE serials (
   id                   SERIAL PRIMARY KEY,
   application_id       INTEGER NOT NULL,
   serial_number        VARCHAR(64) NOT NULL,
   created_on           DATE NOT NULL,
   foreign key (application_id) references applications (id)
);

CREATE TABLE licenses (
   id                    SERIAL PRIMARY KEY,
   serial_id             INTEGER NOT NULL,
   client_id             INTEGER NOT NULL,
   created_on            VARCHAR(64) NOT NULL,
   active                BOOLEAN NOT NULL,
   licence_hash          VARCHAR(512) NOT NULL,
   signed_hash           VARCHAR(512) NOT NULL,
   foreign key (serial_id) references serials (id),
   foreign key (client_id) references clients(id)
);



CREATE TABLE sessions
(
  id SERIAL PRIMARY KEY,
  session_id VARCHAR(256) NOT NULL,
  created_on TIMESTAMP NOT NULL,
  expires_on TIMESTAMP NOT NULL,
  data TEXT,

  UNIQUE (session_id)
);

# --- !Downs

ALTER TABLE applications DROP CONSTRAINT applications_user_id_fkey;
ALTER TABLE applications DROP CONSTRAINT applications_key_id_fkey;
ALTER TABLE serials DROP CONSTRAINT serials_application_id_fkey;
ALTER TABLE licenses DROP CONSTRAINT serials_serial_id_fkey;
ALTER TABLE clients DROP CONSTRAINT serials_application_id_fkey;


DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS applications;
DROP TABLE IF EXISTS serials;
DROP TABLE IF EXISTS licenses;
DROP TABLE IF EXISTS clients;
DROP TABLE IF EXISTS sessions;

