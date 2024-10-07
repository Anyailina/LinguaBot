--liquibase formatted sql
--changeset annill:create_folder_word_table

create table if not exists folder_word
(
    id        bigserial not null primary key,
    folder_id bigint    not null,
    word_id   bigint    not null,
    foreign key (folder_id) references folders (id) on delete cascade,
    foreign key (word_id) references words (id) on delete cascade
);