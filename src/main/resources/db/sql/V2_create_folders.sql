--liquibase formatted sql
--changeset annill:create_folders_table

create table if not exists folders
(
    id        bigserial    not null primary key,
    name      varchar(255) not null,
    user_id   bigint,
    create_at date         not null default current_date,
    update_at date         not null default current_date,
    constraint user_fk foreign key (user_id) references users (id)
);
