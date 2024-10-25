--liquibase formatted sql
--changeset annill:create_folders_table

create table if not exists folders
(
    id      bigserial    not null primary key,
    name    varchar(255) not null,
    user_id bigint,
    constraint user_fk foreign key (user_id) references users (id)
);
