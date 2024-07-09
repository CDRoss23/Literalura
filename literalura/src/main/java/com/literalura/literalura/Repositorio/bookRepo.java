package com.literalura.literalura.Repositorio;

import com.literalura.literalura.Modelo.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface bookRepo extends JpaRepository<Book, Long> {
    // Método para buscar libro por título
    Book findByTitle(String title);

    // Otros métodos personalizados según necesidades
}
