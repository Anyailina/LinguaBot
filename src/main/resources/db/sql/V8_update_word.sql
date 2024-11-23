--liquibase formatted sql
--changeset annill:add is_selected column

alter table words
    add column if not exists is_selected boolean;

ALTER TABLE words
    ALTER COLUMN is_selected SET DEFAULT false;