--liquibase formatted sql
--changeset annill:update_user_table

alter table users
    add column if not exists first_name varchar(255);
alter table users
    add column if not exists user_name varchar(255);