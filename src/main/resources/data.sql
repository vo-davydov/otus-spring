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