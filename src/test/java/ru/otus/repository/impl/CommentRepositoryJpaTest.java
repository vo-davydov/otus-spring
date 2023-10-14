package ru.otus.repository.impl;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.exception.CommentNotFoundException;
import ru.otus.repository.CommentRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Jpa-based repository for working with comment")
@DataJpaTest
class CommentRepositoryJpaTest {

    @Autowired
    private CommentRepository repositoryJpa;

    @Autowired
    private TestEntityManager em;

    private static final long EXPECTED_COMMENT_ID = 2L;

    @Test
    void save() {
        var book = new Book("D&D Player's Handbook", null, null);
        Comment expectedComment = new Comment(book, "WOW!!!");
        repositoryJpa.save(expectedComment);

        var actualComment = em.find(Comment.class, EXPECTED_COMMENT_ID);
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @Test
    void findById() {
        var actualComment = repositoryJpa.findById(1L);
        var expectedComment = em.find(Comment.class, 1L);
        unproxyFields(expectedComment);
        assertThat(actualComment).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @Test
    void delete() {
        assertThatCode(() -> repositoryJpa.deleteById(1L))
                .doesNotThrowAnyException();
    }

    @Test
    void findCommentsByBook() {
        var actualComments = repositoryJpa.findCommentsByBook("IT");
        Comment expectedComment = em.find(Comment.class, 1L);
        unproxyFields(expectedComment);
        assertEquals(1L, actualComments.size());
        assertThat(actualComments.get(0)).usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @Test
    void findCommentsByBookId() {
        var actualComment = repositoryJpa.findById(1L).orElseThrow(CommentNotFoundException::new);
        var expectedComment = em.find(Comment.class, 1L);
        unproxyFields(expectedComment);
        assertNotNull(actualComment);
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(expectedComment);
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

    private void unproxyFields(Comment comment) {
        assertNotNull(comment.getBook());

        var unproxyBook = (Book) Hibernate.unproxy(comment.getBook());
        unproxyFields(unproxyBook);
        comment.setBook(unproxyBook);
    }
}