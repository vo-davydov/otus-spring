package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dao.AuthorDao;
import ru.otus.dao.BookDao;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Book;
import ru.otus.dto.BookDto;
import ru.otus.service.BookService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    private final GenreDao genreDao;

    private final AuthorDao authorDao;

    @Override
    public void saveBook(BookDto bookDto) {
        var genre = genreDao.getByName(bookDto.getGenreName());
        var author = authorDao.getByName(bookDto.getAuthorName());
        var book = new Book(bookDto.getId(), bookDto.getName(), author, genre);
        bookDao.insert(book);
    }

    @Override
    public void updateBook(BookDto bookDto) {
        var genre = genreDao.getByName(bookDto.getGenreName());
        var author = authorDao.getByName(bookDto.getAuthorName());
        var book = new Book(bookDto.getId(), bookDto.getName(), author, genre);
        bookDao.update(book);
    }

    @Override
    public Book getBookById(Long id) {
        return bookDao.getById(id);
    }

    @Override
    public void deleteBookById(Long id) {
        bookDao.delete(id);
    }

    @Override
    public List<Book> getBooks() {
        return bookDao.getAll();
    }

    @Override
    public int countBooks() {
        return bookDao.count();
    }

    public List<Book> getBooksByAuthor(String name) {
        return bookDao.getBooksByAuthor(name);
    }
}
