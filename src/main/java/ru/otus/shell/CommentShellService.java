package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.domain.Comment;
import ru.otus.service.CommentService;
import ru.otus.service.PrintService;

@ShellComponent
@RequiredArgsConstructor
public class CommentShellService {

    private final CommentService commentService;

    private final PrintService<Comment> printService;

    @ShellMethod(value = "Add comment", key = {"cs", "comment save"})
    public void addComment(String bookName, String comment) {
        commentService.save(bookName, comment);
    }

    @ShellMethod(value = "Add comment by book name and author name", key = {"csa", "comment save"})
    public void addComment(String bookName, String authorName, String comment) {
        commentService.save(bookName, authorName, comment);
    }

    @ShellMethod(value = "Get all comments by book", key = {"cga", "comment get all"})
    public void getAllCommentByBook(String bookName) {
        commentService.findAllCommentByBookName(bookName).forEach(printService::print);
    }

    @ShellMethod(value = "Get all comments by book id", key = {"cgai", "comment get all by book id"})
    public void getAllCommentByBookId(Long bookId) {
        commentService.findAllCommentByBookId(bookId).forEach(printService::print);
    }

    @ShellMethod(value = "Add comment by book id", key = {"csi", "comment save by book id"})
    public void addCommentByBookId(Long bookId, String comment) {
        commentService.saveByBookId(bookId, comment);
    }

    @ShellMethod(value = "Delete comment by id", key = {"cd", "delete comment"})
    public void deleteComment(Long commentId) {
        commentService.deleteComment(commentId);
    }

}
