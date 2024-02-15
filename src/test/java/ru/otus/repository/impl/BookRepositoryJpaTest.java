package ru.otus.repository.impl;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.exception.BookNotFoundException;
import ru.otus.repository.BookRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Jpa-based repository for working with book")
@DataJpaTest
class BookRepositoryJpaTest {

    @Autowired
    private BookRepository repository;

    @Autowired
    private TestEntityManager em;

    private static final long EXPECTED_BOOK_ID = 4L;

    private static final int EXPECTED_QUERIES_COUNT = 1;

    @Test
    void save() {
        Book actualBook = new Book("Hearts of Three", null,
                null);
        repository.save(actualBook);
        var expectedBook = em.find(Book.class, EXPECTED_BOOK_ID);
        assertNotNull(expectedBook);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    void findById() {
        Book expectedBook = em.find(Book.class, 1L);
        var actualBook = repository.findById(1L).orElseThrow(BookNotFoundException::new);
        unproxyFields(actualBook);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    void findAll() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);
        Book firstExpectedBook = new Book(1L, "IT",
                new Author(1L, "Stephen king"), new Genre(1L, "horror"));
        Book secondExpectedBook = new Book(2L, "The Black Swan: The Impact of the Highly Improbable",
                new Author(2L, "Nassim Taleb"), new Genre(2L, "non fiction"));
        Book thirdExpectedBook = new Book(3L, "Clean Code: A Handbook of Agile Software Craftsmanship",
                new Author(3L, "Robert Martin"), new Genre(3L, "programming"));
        var expectedBooks = List.of(firstExpectedBook, secondExpectedBook, thirdExpectedBook);
        var actualBooks = repository.findAll();
        actualBooks.forEach(this::unproxyFields);
        assertThat(actualBooks).usingRecursiveComparison().isEqualTo(expectedBooks);
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @Test
    void delete() {
        assertThatCode(() -> repository.deleteById(1L))
                .doesNotThrowAnyException();
    }

    @Test
    void findBooksByAuthor() {
        Book expectedBook = em.find(Book.class, 1L);
        var books = repository.findBooksByAuthorName("Stephen king");
        assertEquals(1, books.size());
        assertNotNull(books.get(0));
        var actualBook = books.get(0);
        unproxyFields(actualBook);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    void findBooksByName() {
        Book expectedBook = em.find(Book.class, 1L);
        var books = repository.findBooksByName("IT");
        assertEquals(1, books.size());
        assertNotNull(books.get(0));
        var actualBook = books.get(0);
        unproxyFields(actualBook);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    private void unproxyFields(Book book) {
        assertNotNull(book);
        assertNotNull(book.getAuthor());
        var unproxyAuthor = (Author) Hibernate.unproxy(book.getAuthor());
        book.setAuthor(unproxyAuthor);
        assertNotNull(book.getGenre());
        var unproxyGenre = (Genre) Hibernate.unproxy(book.getGenre());
        book.setGenre(unproxyGenre);
    }
}