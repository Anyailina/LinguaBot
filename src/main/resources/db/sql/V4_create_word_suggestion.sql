--liquibase formatted sql
--changeset annill:create_word_suggestion_table

create table if not exists words_suggestion
(
    id
    bigserial
    not
    null
    primary
    key,
    phrase
    varchar
(
    255
) not null,
    translation varchar
(
    255
) not null
    )