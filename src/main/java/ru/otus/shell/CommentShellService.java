package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.domain.Comment;
import ru.otus.service.CommentService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class CommentShellService {

    private final CommentService commentService;

    @ShellMethod(value = "Add comment", key = {"cs", "comment save"})
    public void addComment(String bookName, String comment) {
        commentService.save(bookName, comment);
    }

    @ShellMethod(value = "Get all comments by book", key = {"cga", "comment get all"})
    public List<Comment> getAllCommentByBook(String bookName) {
        return commentService.findAllCommentByBookName(bookName);
    }

    @ShellMethod(value = "Get all comments by book id", key = {"cgai", "comment get all by book id"})
    public List<Comment> getAllCommentByBookId(Long bookId) {
        return commentService.findAllCommentByBookId(bookId);
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
