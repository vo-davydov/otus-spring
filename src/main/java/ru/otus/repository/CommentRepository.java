package ru.otus.repository;

import ru.otus.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    Optional<Comment> findById(long id);

    void delete(Long id);

    List<Comment> findCommentsByBook(String name);

    List<Comment> findCommentsByBookId(Long bookId);

}
