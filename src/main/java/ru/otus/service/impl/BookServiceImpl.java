package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.dto.BookDto;
import ru.otus.exception.AmbiguousAuthorException;
import ru.otus.exception.AuthorNotFoundException;
import ru.otus.exception.BookNotFoundException;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;
import ru.otus.service.BookService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final GenreRepository genreRepository;

    private final AuthorRepository authorRepository;

    @Transactional
    @Override
    public void saveBook(BookDto bookDto) {
        var genre = genreRepository.findByName(bookDto.getGenreName());
        var author = getAuthor(bookDto).orElseThrow(AuthorNotFoundException::new);
        var book = new Book(bookDto.getId(), bookDto.getName(), author, genre);
        bookRepository.save(book);
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    @Transactional
    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Book> getBooksByAuthor(String name) {
        return bookRepository.findBooksByAuthorName(name);
    }

    private Optional<Author> getAuthor(BookDto bookDto) {
        Optional<Author> author;
        if (bookDto.getAuthorId() != null) {
            author = authorRepository.findById(bookDto.getAuthorId());
        } else {
            var authors = authorRepository.findByNameIgnoreCase(bookDto.getAuthorName());
            if (authors.size() > 1) {
                throw new AmbiguousAuthorException();
            } else if (authors.size() == 0 || authors.get(0) == null) {
                throw new AuthorNotFoundException();
            }
            author = Optional.of(authors.get(0));
        }

        return author;
    }
}
