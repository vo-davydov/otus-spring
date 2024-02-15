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

insert into USER_ROLES (USER_ID, ROLE_ID)
values (1, 1);

insert into USER_ROLES (USER_ID, ROLE_ID)
values (1, 2);

insert into USER_ROLES (USER_ID, ROLE_ID)
values (2, 2);