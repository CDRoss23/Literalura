package com.literalura.literalura.ClienteHTTP;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.literalura.literalura.Modelo.Author;
import com.literalura.literalura.Modelo.Book;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookApiClient implements CommandLineRunner {
    private HttpClient httpClient;
    private ObjectMapper objectMapper;
    private List<Book> catalog;
    private List<Author> authorsCatalog;

    public BookApiClient() {
        this.httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
        this.objectMapper = new ObjectMapper();
        this.catalog = new ArrayList<>();
        this.authorsCatalog = new ArrayList<>();
    }

    public static void main(String[] args) {
        SpringApplication.run(BookApiClient.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Menu:");
            System.out.println("1. Consultar libro por título");
            System.out.println("2. Listar todos los libros");
            System.out.println("3. Listar libros por idioma");
            System.out.println("4. Listar todos los autores");
            System.out.println("5. Listar autores vivos en un año determinado");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            int option = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (option) {
                case 1:
                    System.out.print("Ingrese el título del libro: ");
                    String title = scanner.nextLine();
                    Book book = searchBookByTitle(title);
                    if (book != null) {
                        catalog.add(book);
                        authorsCatalog.add(book.getAuthors().get(0)); // Consideramos solo el primer autor
                        displayBookDetails(book);
                    } else {
                        System.out.println("No se encontró el libro.");
                    }
                    break;
                case 2:
                    listAllBooks();
                    break;
                case 3:
                    System.out.print("Ingrese el idioma (código de idioma, ej. 'en'): ");
                    String language = scanner.nextLine();
                    listBooksByLanguage(language);
                    break;
                case 4:
                    listAllAuthors();
                    break;
                case 5:
                    System.out.print("Ingrese el año para buscar autores vivos: ");
                    int year = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    listLivingAuthorsInYear(year);
                    break;
                case 6:
                    running = false;
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida, por favor intente de nuevo.");
            }
        }
        scanner.close();
    }

    public HttpRequest buildRequest(String url) {
        return HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .timeout(Duration.ofSeconds(10))
                        .header("Content-Type", "application/json")
                        .GET()
                        .build();
    }

    public Book searchBookByTitle(String title) {
        // Sustituir espacios por guiones bajos
        String formattedTitle = title.replace(" ", "_");
        String url = "https://gutendex.com/books?title=" + formattedTitle;
        HttpRequest request = buildRequest(url);

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonNode root = objectMapper.readTree(response.body());
                JsonNode results = root.get("results");
                if (results.isArray() && results.size() > 0) {
                    return objectMapper.readValue(results.get(0).toString(), Book.class);
                }
            } else {
                System.out.println("Error: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void displayBookDetails(Book book) {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(book.getId()).append("\n")
          .append("Título: ").append(book.getTitle()).append("\n")
          .append("Autor: ").append(book.getAuthors().get(0).getName()).append("\n")
          .append("Año de nacimiento del autor: ").append(book.getAuthors().get(0).getBirthYear()).append("\n")
          .append("Año de fallecimiento del autor: ").append(book.getAuthors().get(0).getDeathYear()).append("\n")
          .append("Idioma: ").append(Book.getLanguages().get(0)).append("\n")
          .append("Número de descargas: ").append(book.getDownloadCount()).append("\n")
          .append("Enlaces de descarga: \n");
        
        book.getFormats().forEach((format, link) -> sb.append(format).append(": ").append(link).append("\n"));
        
        System.out.println(sb.toString());
    }

    public void listAllBooks() {
        if (catalog.isEmpty()) {
            System.out.println("No hay libros en el catálogo.");
        } else {
            for (Book book : catalog) {
                System.out.println(book);
            }
        }
    }

    public void listBooksByLanguage(String language) {
        final List<Book> booksByLanguage = catalog.stream()
                                            .filter(book -> Book.getLanguages().contains(language))
                                            .collect(Collectors.toList());

        if (booksByLanguage.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma especificado.");
        } else {
            for (Book book : booksByLanguage) {
                System.out.println(book);
            }
        }
    }

    public void listAllAuthors() {
        if (authorsCatalog.isEmpty()) {
            System.out.println("No hay autores en el catálogo.");
        } else {
            for (Author author : authorsCatalog) {
                System.out.println(author);
            }
        }
    }

    public void listLivingAuthorsInYear(int year) {
        List<Author> livingAuthors = authorsCatalog.stream()
                                                    .filter(author -> (author.getBirthYear() != null && author.getBirthYear() <= year) &&
                                                                      (author.getDeathYear() == null || author.getDeathYear() > year))
                                                    .collect(Collectors.toList());

        if (livingAuthors.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año especificado.");
        } else {
            for (Author author : livingAuthors) {
                System.out.println(author);
            }
        }
    }
}
