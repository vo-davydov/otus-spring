package ru.otus.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.Book;
import ru.otus.service.OutputService;
import ru.otus.service.PrintService;

@Service
@AllArgsConstructor
public class BookPrintService implements PrintService<Book> {

    private final OutputService outputService;

    @Override
    public void print(Book book) {
        var authorId = book.getAuthor() != null ? book.getAuthor().getId() : null;
        var authorName = book.getAuthor() != null ? book.getAuthor().getName() : null;
        var genreId = book.getGenre() != null ? book.getGenre().getId() : null;
        var genreName = book.getGenre() != null ? book.getGenre().getName() : null;
        outputService.outputString("""
                        <><><><><><><><><><><>\s
                        Book id %d, name %s\s
                        Author id %d, name %s\s
                        Genre id %d, name %s
                        <><><><><><><><><><><>\s
                        """, book.getId(), book.getName(),
                authorId, authorName, genreId, genreName);
    }

}
