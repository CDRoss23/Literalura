package com.literalura.literalura.servicios;

import com.literalura.literalura.Modelo.Author;
import com.literalura.literalura.Modelo.Book;
import com.literalura.literalura.Repositorio.authorRepo;
import com.literalura.literalura.Repositorio.bookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class BookService {
    private final String apiUrl = "https://gutendex.com/books/";

    @Autowired
    private bookRepo bookRepository;

    @Autowired
    private authorRepo authorRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Book searchBookByTitle(String title) {
        String url = apiUrl + "?title=" + title;
        Book book = restTemplate.getForObject(url, Book.class);

        if (book != null) {
            saveBookAndAuthors(book);
        }

        return book;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public List<Book> getBooksByLanguage(String language) {
        // Implementación según sea necesario
        return null;
    }

    public List<Author> getLivingAuthorsInYear(int year) {
        // Implementación según sea necesario
        return null;
    }

    private void saveBookAndAuthors(Book book) {
        bookRepository.save(book);
        if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
            authorRepository.saveAll(book.getAuthors());
        }
    }
}
