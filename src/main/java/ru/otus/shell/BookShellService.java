package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.domain.Book;
import ru.otus.dto.BookDto;
import ru.otus.service.BookService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class BookShellService {

    private final BookService bookService;

    @ShellMethod(value = "Save book", key = {"s", "save"})
    public void saveBook(String name, String authorName, String genreName) {
        var bookDto = new BookDto(name, authorName, genreName);
        bookService.saveBook(bookDto);
    }

    @ShellMethod(value = "Update book", key = {"u", "update"})
    public void updateBook(Long id, String name, String authorName, String genreName) {
        var bookDto = new BookDto(id, name, authorName, genreName);
        bookService.updateBook(bookDto);
    }

    @ShellMethod(value = "Get by id book", key = {"g", "get"})
    public Book getBookById(Long id) {
        return bookService.getBookById(id);
    }

    @ShellMethod(value = "Get books by author", key = {"gb", "get by author"})
    public List<Book> getBooksByAuthor(String name) {
        return bookService.getBooksByAuthor(name);
    }

    @ShellMethod(value = "Delete book", key = {"d", "delete"})
    public void deleteBookById(Long id) {
        bookService.deleteBookById(id);
    }

    @ShellMethod(value = "Get all books", key = {"ga", "get all"})
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @ShellMethod(value = "Count books", key = {"c", "count"})
    public int count() {
        return bookService.countBooks();
    }

}
