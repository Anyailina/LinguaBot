--liquibase formatted sql
--changeset annill:fill_tables

insert into words (name, translation)
values ('apple', 'яблоко'),
       ('banana', 'банан'),
       ('car', 'машина'),
       ('dog', 'собака'),
       ('cat', 'кот'),
       ('house', 'дом'),
       ('book', 'книга'),
       ('tree', 'дерево'),
       ('river', 'река'),
       ('mountain', 'гора'),
       ('computer', 'компьютер'),
       ('sun', 'солнце'),
       ('moon', 'луна'),
       ('star', 'звезда'),
       ('water', 'вода');

insert into folders (name)
values ('Fruits'),
       ('Animals'),
       ('Vehicles'),
       ('Buildings'),
       ('Nature'),
       ('Technology'),
       ('Astronomy'),
       ('Daily Objects'),
       ('Weather'),
       ('Countries'),
       ('Colors'),
       ('Emotions'),
       ('Sports'),
       ('Food'),
       ('Furniture');

insert into folder_word(folder_id, word_id)
values (1, 1),  -- Fruits -> apple
       (1, 2),  -- Fruits -> banana
       (2, 4),  -- Animals -> dog
       (2, 5),  -- Animals -> cat
       (3, 3),  -- Vehicles -> car
       (4, 6),  -- Buildings -> house
       (5, 8),  -- Nature -> tree
       (5, 9),  -- Nature -> river
       (5, 10), -- Nature -> mountain
       (6, 11), -- Technology -> computer
       (7, 12), -- Astronomy -> sun
       (7, 13), -- Astronomy -> moon
       (7, 14), -- Astronomy -> star
       (5, 15), -- Nature -> water
       (9, 12); -- Weather -> sun