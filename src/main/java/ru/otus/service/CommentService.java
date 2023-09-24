package ru.otus.service;

import ru.otus.domain.Comment;

import java.util.List;

public interface CommentService {

    Comment save(String bookName, String text);

    Comment save(String bookName, String authorName, String text);

    Comment saveByBookId(Long bookId, String text);

    List<Comment> findAllCommentByBookId(Long bookId);

    List<Comment> findAllCommentByBookName(String bookName);

    void deleteComment(Long commentId);
}
