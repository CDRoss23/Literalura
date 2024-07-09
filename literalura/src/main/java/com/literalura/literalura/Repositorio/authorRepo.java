package com.literalura.literalura.Repositorio;

import com.literalura.literalura.Modelo.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface authorRepo extends JpaRepository<Author, Long> {
    // Otros métodos según necesidades
}
