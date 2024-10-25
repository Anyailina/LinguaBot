--liquibase formatted sql
--changeset annill:create_words_table

create table if not exists users
(
    id      bigserial not null primary key,
    chat_id bigserial not null
);
