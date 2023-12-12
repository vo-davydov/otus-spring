package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.dto.BookDto;
import ru.otus.exception.AmbiguousAuthorException;
import ru.otus.exception.BookNotFoundException;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;
import ru.otus.service.BookService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final GenreRepository genreRepository;

    private final AuthorRepository authorRepository;

    @Transactional
    @Override
    public void saveBook(BookDto bookDto) {
        var genre = findOrCreateNewGenre(bookDto);
        var author = findOrCreateNewAuthor(bookDto);
        createOrUpdateBook(bookDto, author, genre);
    }

    private void createOrUpdateBook(BookDto bookDto, Author author, Genre genre) {
        var book = bookDto.getId() != null ? bookRepository.findById(bookDto.getId()).orElse(null) : null;
        if (book == null) {
            bookRepository.save(new Book(bookDto.getName(), author, genre));
        } else {
            book.setGenre(genre);
            book.setAuthor(author);
            book.setName(bookDto.getName());
            bookRepository.save(book);
        }
    }

    private Genre findOrCreateNewGenre(BookDto bookDto) {
        var genre = genreRepository.findByName(bookDto.getGenreName());
        if (genre == null) {
            return genreRepository.save(new Genre(bookDto.getGenreName()));
        } else {
            genre.setName(bookDto.getGenreName());
            return genreRepository.save(genre);
        }
    }

    private Author findOrCreateNewAuthor(BookDto bookDto) {
        var authors = authorRepository.findByNameIgnoreCase(bookDto.getAuthorName());
        if (authors.size() > 1) {
            throw new AmbiguousAuthorException();
        }
        var author = authors.size() == 0 ? null : authors.get(0);
        if (author == null) {
            return authorRepository.save(new Author(bookDto.getAuthorName()));
        } else {
            author.setName(bookDto.getAuthorName());
            return authorRepository.save(author);
        }
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

}
