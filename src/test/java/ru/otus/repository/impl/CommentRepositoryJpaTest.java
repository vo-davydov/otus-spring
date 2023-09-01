package ru.otus.repository.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Comment;
import ru.otus.repository.CommentRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Jpa-based repository for working with comment")
@DataJpaTest
@Import(CommentRepositoryJpa.class)
class CommentRepositoryJpaTest {

    @Autowired
    private CommentRepository repositoryJpa;

    private static final long EXPECTED_COMMENT_ID = 2L;

    @Test
    void save() {
        Comment expectedComment = new Comment(2L, "WOW!");
        repositoryJpa.save(expectedComment);
        var actualComment = repositoryJpa.findById(EXPECTED_COMMENT_ID);
        assertNotNull(actualComment.get());
        assertThat(actualComment.get()).usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @Test
    void findById() {
        Comment expectedComment = new Comment(1L, "BAD BAD", 1L);
        Optional<Comment> actualCommentOpt = repositoryJpa.findById(1L);
        assertNotNull(actualCommentOpt.get());
        var actualComment = actualCommentOpt.get();
        assertNotNull(actualComment);
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @Test
    void findAll() {
        Comment expectedComment = new Comment(1L, "BAD BAD", 1L);
        var actualComments = repositoryJpa.findAll();
        assertEquals(1L, actualComments.size());
        assertThat(actualComments.get(0)).usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @Test
    void delete() {
        assertThatCode(() -> repositoryJpa.delete(1L))
                .doesNotThrowAnyException();
    }

    @Test
    void findCommentsByBook() {
        Comment expectedComment = new Comment(1L, "BAD BAD", 1L);
        var actualComments = repositoryJpa.findCommentsByBook("IT");
        assertEquals(1L, actualComments.size());
        assertThat(actualComments.get(0)).usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @Test
    void findCommentsByBookId() {
        Comment expectedComment = new Comment(1L, "BAD BAD", 1L);
        var actualComment = repositoryJpa.findById(1L);
        assertNotNull(actualComment.get());
        assertThat(actualComment.get()).usingRecursiveComparison().isEqualTo(expectedComment);
    }
}