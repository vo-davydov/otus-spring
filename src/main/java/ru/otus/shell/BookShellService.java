package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.domain.Book;
import ru.otus.dto.BookDto;
import ru.otus.service.BookService;
import ru.otus.service.PrintService;

@ShellComponent
@RequiredArgsConstructor
public class BookShellService {

    private final BookService bookService;

    private final PrintService<Book> printService;

    @ShellMethod(value = "Save book", key = {"s", "save"})
    public void saveBook(String name, String authorName, String genreName) {
        var bookDto = new BookDto(name, authorName, genreName);
        bookService.saveBook(bookDto);
    }

    @ShellMethod(value = "Save book by author id", key = {"si", "save by id"})
    public void saveBook(String name, Long authorId, String genreName) {
        var bookDto = new BookDto(name, authorId, genreName);
        bookService.saveBook(bookDto);
    }

    @ShellMethod(value = "Update book", key = {"u", "update"})
    public void updateBook(Long id, String name, String authorName, String genreName) {
        var bookDto = new BookDto(id, name, authorName, genreName);
        bookService.updateBook(bookDto);
    }

    @ShellMethod(value = "Get by id book", key = {"g", "get"})
    public void getBookById(Long id) {
        var book = bookService.getBookById(id);
        printService.print(book);
    }

    @ShellMethod(value = "Get books by author", key = {"gb", "get by author"})
    public void getBooksByAuthor(String name) {
        var books = bookService.getBooksByAuthor(name);
        books.forEach(printService::print);
    }

    @ShellMethod(value = "Delete book", key = {"d", "delete"})
    public void deleteBookById(Long id) {
        bookService.deleteBookById(id);
    }

    @ShellMethod(value = "Get all books", key = {"ga", "get all"})
    public void getBooks() {
        var books = bookService.getBooks();
        books.forEach(printService::print);
    }

    @ShellMethod(value = "Count books", key = {"c", "count"})
    public int count() {
        return bookService.countBooks();
    }

}
