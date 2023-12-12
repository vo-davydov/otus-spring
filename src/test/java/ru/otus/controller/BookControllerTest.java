package ru.otus.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.dto.BookDto;
import ru.otus.service.BookService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Captor
    private ArgumentCaptor<BookDto> bookDtoCaptor;

    private Genre expectedGenre;

    private Author expectedAuthor;

    private Book expectedBook;

    @BeforeEach
    public void setUp() {
        expectedGenre = new Genre(1L, "horror");
        expectedAuthor = new Author(1L, "Stephen king");
        expectedBook = new Book(1L, "IT", expectedAuthor, expectedGenre);
    }

    @Test
    public void testListPage() throws Exception {
        List<Book> books = new ArrayList<>();
        books.add(expectedBook);

        given(bookService.getBooks()).willReturn(books);

        mockMvc.perform(get("/books/"))
                .andExpect(status().isOk())
                .andExpect(view().name("list"))
                .andExpect(model().attribute("books", books));
    }

    @Test
    public void testEditPage() throws Exception {
        long bookId = 1L;

        given(bookService.getBookById(bookId)).willReturn(expectedBook);

        mockMvc.perform(get("/books/edit/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"))
                .andExpect(model().attribute("book", expectedBook.toDto()));
    }

    @Test
    public void testEditBook() throws Exception {
        BookDto bookDto = expectedBook.toDto();

        mockMvc.perform(post("/books/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", String.valueOf(bookDto.getId()))
                        .param("name", bookDto.getName())
                        .param("authorName", bookDto.getAuthorName())
                        .param("genreName", bookDto.getGenreName()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/books/"));

        verify(bookService).saveBook(bookDto);
    }

    @Test
    public void testAddPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("add"));
    }

    @Test
    public void testAddBook() throws Exception {
        BookDto bookDto = expectedBook.toDto();

        mockMvc.perform(MockMvcRequestBuilders.post("/books/add")
                        .flashAttr("book", bookDto))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/books/"));

        verify(bookService).saveBook(bookDtoCaptor.capture());
        BookDto capturedBookDto = bookDtoCaptor.getValue();
        assertEquals(bookDto.getId(), capturedBookDto.getId());
        assertEquals(bookDto.getName(), capturedBookDto.getName());
        assertEquals(bookDto.getAuthorName(), capturedBookDto.getAuthorName());
        assertEquals(bookDto.getGenreName(), capturedBookDto.getGenreName());
    }

    @Test
    public void testDeleteBook() throws Exception {
        long bookId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.post("/books/delete/{id}", bookId))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/books/"));

        verify(bookService).deleteBookById(bookId);
    }
}

