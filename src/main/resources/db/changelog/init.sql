--liquibase formatted sql
-- changeset derkachev:1000000-1
-- comment: Initial creation of table
CREATE TABLE news
(
    id   BIGSERIAL PRIMARY KEY,
    text VARCHAR NOT NULL
);
