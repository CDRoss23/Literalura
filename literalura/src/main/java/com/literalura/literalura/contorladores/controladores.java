package com.literalura.literalura.contorladores;

import com.literalura.literalura.Modelo.Author;
import com.literalura.literalura.Modelo.Book;
import com.literalura.literalura.servicios.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class controladores {
    @Autowired
    private BookService bookService;

    @GetMapping("/search")
    public Book searchBookByTitle(@RequestParam String title) {
        return bookService.searchBookByTitle(title);
    }

    @GetMapping("/")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/authors")
    public List<Author> getAllAuthors() {
        return bookService.getAllAuthors();
    }

    @GetMapping("/language")
    public List<Book> getBooksByLanguage(@RequestParam String language) {
        return bookService.getBooksByLanguage(language);
    }

    @GetMapping("/authors/{year}")
    public List<Author> getLivingAuthorsInYear(@PathVariable int year) {
        return bookService.getLivingAuthorsInYear(year);
    }
}
