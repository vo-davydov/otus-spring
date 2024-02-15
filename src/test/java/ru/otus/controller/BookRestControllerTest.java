package ru.otus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.dto.BookDto;
import ru.otus.service.BookService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(BookRestController.class)
public class BookRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private Genre expectedGenre;

    private Author expectedAuthor;

    private Book expectedBook;

    private Book expectedBook2;

    @Captor
    private ArgumentCaptor<BookDto> bookDtoCaptor;

    @BeforeEach
    public void setUp() {
        expectedGenre = new Genre(1L, "horror");
        expectedAuthor = new Author(1L, "Stephen king");
        expectedBook = new Book(1L, "IT", expectedAuthor, expectedGenre);
        expectedBook2 = new Book(4L, "The Dark Tower", expectedAuthor, expectedGenre);
    }

    @Test
    public void testGetAllBooks() throws Exception {
        List<Book> expectedBooks = Arrays.asList(
                expectedBook,
                expectedBook2
        );
        Mockito.when(bookService.getBooks()).thenReturn(expectedBooks);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/")
                        .with(user("user").password("password").roles("USER"))
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(expectedBook.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(expectedBook.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(expectedBook2.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(expectedBook2.getName()))
                .andReturn();

        verify(bookService, Mockito.times(1)).getBooks();
        Mockito.verifyNoMoreInteractions(bookService);
    }

    @Test
    public void testGetBookById() throws Exception {
        long bookId = 1L;
        Mockito.when(bookService.getBookById(Mockito.anyLong())).thenReturn(expectedBook);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{id}", bookId)
                        .with(user("user").password("password").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(bookId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expectedBook.getName()))
                .andReturn();

        verify(bookService, Mockito.times(1)).getBookById(bookId);
        Mockito.verifyNoMoreInteractions(bookService);
    }

    @Test
    public void testSaveBook() throws Exception {
        BookDto bookDto = expectedBook2.toDto();
        Mockito.doNothing().when(bookService).saveBook(Mockito.any(BookDto.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/books/")

                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookDto))
                        .with(user("user").password("password").roles("USER"))
                )
                .andExpect(status().isOk())
                .andReturn();

        verify(bookService, Mockito.times(1)).saveBook(bookDto);
        Mockito.verifyNoMoreInteractions(bookService);
    }

    @Test
    public void testDeleteBook() throws Exception {
        long bookId = 1L;
        Mockito.doNothing().when(bookService).deleteBookById(Mockito.anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/{id}", bookId)
                        .with(user("user").password("password").roles("USER")))
                .andExpect(status().isOk())
                .andReturn();

        verify(bookService, Mockito.times(1)).deleteBookById(bookId);
        Mockito.verifyNoMoreInteractions(bookService);
    }

    @Test
    public void testEditBook() throws Exception {
        BookDto bookDto = new BookDto(1L, "Updated Book", "Test", "Test");
        String jsonPayload = "{\"id\": 1, \"name\": \"Updated Book\"}";

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/api/books/")
                .with(user("user").password("password").roles("USER"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload));

        resultActions.andExpect(status().isOk());

        verify(bookService).saveBook(bookDtoCaptor.capture());
        BookDto capturedBookDto = bookDtoCaptor.getValue();

        assertEquals(bookDto.getId(), capturedBookDto.getId());
        assertEquals(bookDto.getName(), capturedBookDto.getName());
    }
}

