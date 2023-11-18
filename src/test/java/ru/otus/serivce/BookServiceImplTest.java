package ru.otus.serivce;

//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.junit4.SpringRunner;
//import ru.otus.domain.Author;
//import ru.otus.domain.Book;
//import ru.otus.domain.Genre;
//import ru.otus.repository.AuthorRepository;
//import ru.otus.repository.BookRepository;
//import ru.otus.repository.GenreRepository;
//import ru.otus.exception.BookNotFoundException;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
//import static org.junit.jupiter.api.Assertions.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@DirtiesContext
//class BookServiceImplTest {
//
//    @Autowired
//    private BookService bookService;
//
//    @Autowired
//    private AuthorRepository authorRepository;
//
//    @Autowired
//    private GenreRepository genreRepository;
//
//    @Autowired
//    private BookRepository bookRepository;
//
//    private Genre expectedGenre;
//
//    private Author expectedAuthor;
//
//    private Book expectedBook;
//
//    @BeforeEach
//    public void setUp() {
//        var genre = new Genre("horror");
//        genreRepository.save(genre);
//        this.expectedGenre = genre;
//        var author = new Author("Ivan");
//        authorRepository.save(author);
//        this.expectedAuthor = author;
//        var book = new Book("IT", author, genre);
//        bookService.saveBook(book);
//        this.expectedBook = book;
//    }

//    @Test
//    void saveBook() {
//        var book = new Book("IT 2", expectedAuthor, expectedGenre);
//        bookService.saveBook(book);
//        var actualBook = bookRepository.findById(book.getId()).orElseThrow(BookNotFoundException::new);
//        assertThat(actualBook).usingRecursiveComparison().isEqualTo(book);
//    }
//
//    @Test
//    void getBookById() {
//        var id = expectedBook.getId();
//        var actualBook = bookService.getBookById(id);
//        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
//    }
//
//    @Test
//    void deleteBookById() {
//        var book = new Book("IT 2", expectedAuthor, expectedGenre);
//        bookRepository.save(book);
//        assertThatCode(() -> bookService.deleteBookById(book.getId()))
//                .doesNotThrowAnyException();
//        assertThrows(BookNotFoundException.class,
//                () -> bookRepository.findById(book.getId()).orElseThrow(BookNotFoundException::new));
//    }
//
//    @Test
//    void getBooks() {
//        var actualBooks = bookService.getBooks();
//        Assertions.assertTrue(actualBooks.contains(expectedBook));
//    }
//
//    @Test
//    void getBooksByAuthor() {
//        var actualBooks = bookService.getBooksByAuthor(expectedAuthor.getName());
//        Assertions.assertTrue(actualBooks.contains(expectedBook));
//    }
//
//    @Test
//    void getBooksByGenre() {
//        var actualBooks = bookService.getBooksByGenre(expectedGenre.getName());
//        Assertions.assertTrue(actualBooks.contains(expectedBook));
//    }
//}