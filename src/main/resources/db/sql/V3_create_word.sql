--liquibase formatted sql
--changeset annill:create_words_table

create table if not exists words
(
    id          bigserial    not null primary key,
    name        varchar(255) not null,
    translation varchar(255) not null,
    folder_id   bigint,
    constraint folder_fk foreign key (folder_id) references folders (id)
)

