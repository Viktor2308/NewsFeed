--liquibase formatted sql
-- changeset derkachev:1000000-1
-- comment: Initial creation of table
CREATE TABLE news
(
    news_id BIGSERIAL PRIMARY KEY,
    title   VARCHAR      NOT NULL,
    text    VARCHAR      NOT NULL,
    created TIMESTAMP(0) NOT NULL,
    category_id BIGINT
);
CREATE TABLE category
(
    category_id BIGSERIAL PRIMARY KEY,
    category    VARCHAR NOT NULL
);
