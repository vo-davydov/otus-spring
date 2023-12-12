package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.otus.domain.Book;
import ru.otus.dto.BookDto;
import ru.otus.service.BookService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @GetMapping("/")
    public String listPage(Model model) {
        List<Book> books = bookService.getBooks();
        model.addAttribute("books", books);
        return "list";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable("id") long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book.toDto());
        return "edit";
    }

    @PostMapping("/edit")
    public String editBook(@ModelAttribute("book") BookDto bookDto,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        bookService.saveBook(bookDto);
        return "redirect:/books/";
    }

    @GetMapping("/add")
    public String addPage() {
        return "add";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute("book") BookDto bookDto,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add";
        }
        bookService.saveBook(bookDto);
        return "redirect:/books/";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") long id) {
        bookService.deleteBookById(id);
        return "redirect:/books/";
    }
}
