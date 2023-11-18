package ru.otus.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.exception.AuthorNotFoundException;
import ru.otus.exception.GenreNotFoundException;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "davydov", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertAuthors", author = "davydov")
    public void insertAuthors(AuthorRepository repository) {
        var author1 = new Author("Jack London");
        var author2 = new Author("Jordan Peterson");
        var author3 = new Author("Daniel Kahneman");

        repository.save(author1);
        repository.save(author2);
        repository.save(author3);
    }

    @ChangeSet(order = "003", id = "insertGenre", author = "davydov")
    public void insertGenres(GenreRepository repository) {
        var genre1 = new Genre("psychology");
        var genre2 = new Genre("economy");
        var genre3 = new Genre("adventures");

        repository.save(genre1);
        repository.save(genre2);
        repository.save(genre3);
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "davydov")
    public void insertBooks(AuthorRepository authorRepository, GenreRepository genreRepository,
                            BookRepository bookRepository) {
        var author1 = authorRepository.findAuthorByName("Jack London").stream().findAny()
                .orElseThrow(AuthorNotFoundException::new);
        var author2 = authorRepository.findAuthorByName("Jordan Peterson").stream().findAny()
                .orElseThrow(AuthorNotFoundException::new);;
        var author3 = authorRepository.findAuthorByName("Daniel Kahneman").stream().findAny()
                .orElseThrow(AuthorNotFoundException::new);;

        var genre1 = genreRepository.findGenreByName("psychology").stream().findAny()
                .orElseThrow(GenreNotFoundException::new);
        var genre2 = genreRepository.findGenreByName("economy").stream().findAny()
                .orElseThrow(GenreNotFoundException::new);
        var genre3 = genreRepository.findGenreByName("adventures").stream().findAny()
                .orElseThrow(GenreNotFoundException::new);

        bookRepository.save(new Book("White Fang", author1, genre3));
        bookRepository.save(new Book("The sea-wolf", author1, genre3));
        bookRepository.save(new Book("Thinking fast and slow", author3, genre2));
        bookRepository.save(new Book("12 Rules for Life", author2, genre1));
        bookRepository.save(new Book("12 More Rules for Life", author2, genre1));
    }

}
