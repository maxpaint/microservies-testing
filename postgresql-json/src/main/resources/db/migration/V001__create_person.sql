CREATE SEQUENCE person_seq
  START 1
  INCREMENT 50;

CREATE TABLE person (
  id         BIGINT NOT NULL,
  email      VARCHAR(255),
  first_name VARCHAR(255),
  last_name  VARCHAR(255),
  CONSTRAINT person_pk PRIMARY KEY (id)
);

CREATE SEQUENCE greeting_message_seq
  START 1
  INCREMENT 50;

CREATE TABLE greeting_message (
  id        BIGINT NOT NULL,
  message   VARCHAR(255),
  person_id BIGINT REFERENCES person (id),
  CONSTRAINT greeting_message_pk PRIMARY KEY (id)
);