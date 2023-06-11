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
