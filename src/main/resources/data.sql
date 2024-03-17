insert into AUTHOR (NAME)
values ('Jack London');
insert into AUTHOR (NAME)
values ('Jordan Peterson');
insert into AUTHOR (NAME)
values ('Daniel Kahneman');

insert into GENRE (NAME)
values ('psychology');
insert into GENRE (NAME)
values ('economy');
insert into GENRE (NAME)
values ('adventures');

insert into book (NAME, author_id, genre_id)
values ('White Fang', 1, 3);
insert into book (NAME, author_id, genre_id)
values ('The sea-wolf', 1, 3);

insert into book (NAME, author_id, genre_id)
values ('Thinking fast and slow', 3, 3);

insert into book (NAME, author_id, genre_id)
values ('12 Rules for Life', 2, 1);
insert into book (NAME, author_id, genre_id)
values ('12 More Rules for Life', 2, 1);

insert into COMMENT (TEXT, BOOK_ID)
values ('OMG the best book', 1);
insert into COMMENT (TEXT, BOOK_ID)
values ('better not read at all', 2);

insert into ROLE (ROLE)
values ('ROLE_ADMIN');
insert into ROLE (ROLE)
values ('ROLE_USER');

--adminka
insert into SYS_USER (username, password, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled)
values ('admin', '$2a$10$fENzVZSPiZEjKWjSopzW1e2Qz2Kx9MusWR8ziMeRl9AeURPqypYia', true, true, true, true);
--123456
insert into SYS_USER (username, password, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled)
values ('user', '$2a$10$DC.czE1pkzgLgi9AdauH0uRPLbOHPQFtlzeLhhUvgG7KQ/bF3k2Zm', true, true, true, true);

insert into SYS_USER (username, password, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled)
values ('testuser', '$2a$10$DC.czE1pkzgLgi9AdauH0uRPLbOHPQFtlzeLhhUvgG7KQ/bF3k2Zm', true, true, true, true);

insert into USER_ROLES (USER_ID, ROLE_ID)
values (1, 1);

insert into USER_ROLES (USER_ID, ROLE_ID)
values (1, 2);

insert into USER_ROLES (USER_ID, ROLE_ID)
values (2, 2);

insert into USER_ROLES (USER_ID, ROLE_ID)
values (3, 2);

INSERT INTO acl_sid (principal, sid)
VALUES (0, 'ROLE_USER'),
       (0, 'ROLE_ADMIN');

INSERT INTO acl_class (id, class)
VALUES (1, 'ru.otus.domain.Author');
INSERT INTO acl_class (id, class)
VALUES (2, 'ru.otus.domain.Genre');
INSERT INTO acl_class (id, class)
VALUES (3, 'ru.otus.domain.Book');

INSERT INTO acl_object_identity
(object_id_class, object_id_identity,
 parent_object, owner_sid, entries_inheriting)
VALUES (1, 1, NULL, 2, 0),
       (1, 2, NULL, 2, 0),
       (1, 3, NULL, 2, 0),

       (2, 1, NULL, 2, 0),
       (2, 2, NULL, 2, 0),
       (2, 3, NULL, 2, 0),

       (3, 1, NULL, 2, 0),
       (3, 2, NULL, 2, 0),
       (3, 3, NULL, 2, 0),
       (3, 4, NULL, 2, 0),
       (3, 5, NULL, 2, 0);

INSERT INTO acl_entry
(acl_object_identity, ace_order,
 sid, mask, granting, audit_success, audit_failure)
VALUES
    (1, 1, 2, 1, 1, 1, 1),
    (2, 2, 2, 1, 1, 1, 1),
    (3, 3, 2, 1, 1, 1, 1),
    (4, 1, 2, 1, 1, 1, 1),
    (5, 2, 2, 1, 1, 1, 1),
    (6, 1, 2, 1, 1, 1, 1),
    (7, 2, 2, 1, 1, 1, 1),
    (8, 1, 2, 1, 1, 1, 1),
    (9, 2, 2, 1, 1, 1, 1),
    (10, 3, 2, 1, 1, 1, 1),
    (11, 1, 2, 1, 1, 1, 1),
    (1, 2, 2, 2, 1, 1, 1),
    (2, 3, 2, 2, 1, 1, 1),
    (3, 4, 2, 2, 1, 1, 1),
    (4, 5, 2, 2, 1, 1, 1),
    (5, 6, 2, 2, 1, 1, 1),
    (6, 7, 2, 2, 1, 1, 1),
    (7, 8, 2, 2, 1, 1, 1),
    (8, 9, 2, 2, 1, 1, 1),
    (9, 10, 2, 2, 1, 1, 1),
    (10, 11, 2, 2, 1, 1, 1),
    (11, 12, 2, 2, 1, 1, 1);