--liquibase formatted sql
--changeset annill:update_word_table

alter table words
    add column if not exists is_learned boolean default false;

alter table words
    add column if not exists quantity_repeat integer default 0;

