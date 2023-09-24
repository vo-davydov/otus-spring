package ru.otus.service.impl;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Comment;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;
import ru.otus.service.CommentService;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Override
    @Transactional
    public Comment save(@NotNull String bookName, @NotNull String text) {
        var books = bookRepository.findBooksByName(bookName);
        if (books.size() > 1) {
            throw new RuntimeException("Ambiguous book name: " + bookName + ". There are " + books.size() + " books. " +
                    "Please write book id or book name + author name");
        }
        var book = books.get(0);
        var comment = new Comment(book, text);
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public Comment save(@NotNull String bookName, @NotNull String authorName, @NotNull String text) {
        var books = bookRepository.findBooksByName(bookName)
                .stream()
                .filter(b -> authorName.equalsIgnoreCase(b.getAuthor().getName()))
                .toList();

        if (books.size() > 1) {
            throw new RuntimeException("Ambiguous book name: " + bookName + ". There are " + books.size() + " books. " +
                    "Please write book id");
        }

        var comment = new Comment(books.get(0), text);
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public Comment saveByBookId(Long bookId, String text) {
        var book = bookRepository.findById(bookId);
        var comment = new Comment(book, text);
        return commentRepository.save(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findAllCommentByBookId(Long bookId) {
        return commentRepository.findCommentsByBookId(bookId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findAllCommentByBookName(String bookName) {
        return commentRepository.findCommentsByBook(bookName);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.delete(commentId);
    }

}
