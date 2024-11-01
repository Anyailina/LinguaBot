--liquibase formatted sql
--changeset annill:create_words_table

create table if not exists users
(
    id        bigserial not null primary key,
    chat_id   bigserial not null,
    create_at date      not null default current_date,
    update_at date      not null default current_date
);
