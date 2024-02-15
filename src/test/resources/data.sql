insert into AUTHOR (NAME)
values ('Stephen king');
insert into AUTHOR (NAME)
values ('Nassim Taleb');
insert into AUTHOR (NAME)
values ('Robert Martin');

insert into GENRE (NAME)
values ('horror');
insert into GENRE (NAME)
values ('non fiction');
insert into GENRE (NAME)
values ('programming');

insert into book (NAME, author_id, genre_id)
values ('IT', 1, 1);

insert into book (NAME, author_id, genre_id)
values ('The Black Swan: The Impact of the Highly Improbable', 2, 2);

insert into book (NAME, author_id, genre_id)
values ('Clean Code: A Handbook of Agile Software Craftsmanship', 3, 3);

insert into COMMENT (TEXT, BOOK_ID)
values ('BAD BAD', 1);


