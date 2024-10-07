--liquibase formatted sql
--changeset annill:create_folders_table

create table if not exists folders
(
    id
    bigserial
    not
    null
    primary
    key,
    name
    varchar
(
    255
) not null
    );