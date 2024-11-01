--liquibase formatted sql
--changeset annill:create_word_suggestion_table
CREATE TABLE IF NOT EXISTS words_suggestion
(
    id          bigserial primary key,
    phrase      varchar(255) not null,
    translation varchar(255) not null,
    create_at   date         not null default current_date,
    update_at   date         not null default current_date
);