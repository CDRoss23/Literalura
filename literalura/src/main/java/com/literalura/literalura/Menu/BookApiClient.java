package com.literalura.literalura.Menu;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.literalura.literalura.Modelo.Book;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookApiClient implements CommandLineRunner {
    private HttpClient httpClient;
    private ObjectMapper objectMapper;

    public BookApiClient() {
        this.httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
        this.objectMapper = new ObjectMapper();
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
            System.out.println("1. Consultar libros");
            System.out.println("2. Salir");
            System.out.print("Seleccione una opción: ");

            int option = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (option) {
                case 1:
                    System.out.print("Ingrese el término de búsqueda: ");
                    String searchTerm = scanner.nextLine();
                    List<Book> books = getBooks("https://gutendex.com/books?search=" + searchTerm);
                    if (books != null && !books.isEmpty()) {
                        for (Book book : books) {
                            System.out.println(book);
                        }
                    } else {
                        System.out.println("No se encontraron libros.");
                    }
                    break;
                case 2:
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

    public List<Book> getBooks(String url) {
        HttpRequest request = buildRequest(url);

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Mapear la respuesta JSON a los objetos Java
                return objectMapper.readValue(response.body(), new TypeReference<List<Book>>() {});
            } else {
                System.out.println("Error: " + response.statusCode());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
