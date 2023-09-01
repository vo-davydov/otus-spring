package ru.otus.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;
import ru.otus.service.CommentService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@DisplayName("Comment service для работы с комментариями должно ")
@SpringBootTest
class CommentServiceImplTest {

    @Autowired
    private CommentService commentService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private CommentRepository commentRepository;

    private Comment expectedComment;

    private Genre expectedGenre;

    private Author expectedAuthor;

    private Book expectedBook;

    private List<Comment> comments;

    @BeforeEach
    public void init() {
        expectedComment = new Comment(1L, "THE BEST BOOK EVER!");
        expectedGenre = new Genre(1L, "horror");
        expectedAuthor = new Author(1L, "Stephen king");
        comments = List.of();
        expectedBook = new Book(1L, "IT", expectedAuthor, expectedGenre, comments);
    }

    @Test
    void save() {
        when(bookRepository.findBooksByName("IT")).thenReturn(List.of(expectedBook));
        when(commentRepository.findCommentsByBook("IT")).thenReturn(List.of(expectedComment));
        commentService.save("IT", "THE BEST BOOK EVER!");
        var actualComment = commentService.findAllCommentByBookName("IT");
        assertEquals(1, actualComment.size());
        var actualCommentText = actualComment.get(0).getText();
        assertNotNull(actualCommentText);
        assertEquals(expectedComment.getText(), actualCommentText);
    }

    @Test
    void findAllCommentByBookName() {
        when(commentRepository.findCommentsByBook("IT")).thenReturn(List.of(expectedComment));
        var allComments = commentService.findAllCommentByBookName("IT");
        assertEquals(1, allComments.size());
        var actualComment = allComments.get(0);
        assertNotNull(actualComment);
        assertEquals(actualComment.getText(), "THE BEST BOOK EVER!");
    }

    @Test
    void findAllCommentByBookId() {
        when(commentRepository.findCommentsByBookId(1L)).thenReturn(List.of(expectedComment));
        var allComments = commentService.findAllCommentByBookId(1L);
        assertEquals(1, allComments.size());
        var actualComment = allComments.get(0);
        assertNotNull(actualComment);
        assertEquals(actualComment.getText(), "THE BEST BOOK EVER!");
    }

    @Test
    void deleteBookById() {
        assertThatCode(() -> commentService.deleteComment(1L))
                .doesNotThrowAnyException();
    }
}