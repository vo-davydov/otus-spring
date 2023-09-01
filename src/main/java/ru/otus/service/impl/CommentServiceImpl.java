package ru.otus.service.impl;

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
    public Comment save(String bookName, String text) {
        var books = bookRepository.findBooksByName(bookName);
        if (books.size() == 0) {
            throw new RuntimeException("There is no books with name: " + bookName);
        }
        if (books.size() > 1) {
            throw new RuntimeException("Ambiguous book name: " + bookName + ". There are " + books.size() + " books. " +
                    "Please write book id");
        }
        var book = books.get(0);
        var comment = new Comment(book.getId(), text);
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public Comment saveByBookId(Long bookId, String text) {
        var comment = new Comment(bookId, text);
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findAllCommentByBookId(Long bookId) {
        return commentRepository.findCommentsByBookId(bookId);
    }

    @Override
    public List<Comment> findAllCommentByBookName(String bookName) {
        return commentRepository.findCommentsByBook(bookName);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.delete(commentId);
    }

}
