package com.literalura.literalura.Modelo;

import jakarta.persistence.*;
import java.util.List;
import java.util.Map;


@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ElementCollection
    private static List<String> languages;
    private int downloadCount;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Author> authors;
    private Map<String, String> formats;
    // Constructor, getters y setters

    public Book() {
    }

    public Book(String title, List<String> languages, int downloadCount, List<Author> authors, Map<String, String> formats) {
        this.title = title;
        Book.languages = languages;
        this.downloadCount = downloadCount;
        this.authors = authors;
        this.formats = formats;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        Book.languages = languages;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Map<String, String> getFormats() {
        return formats;
    }

    public void setFormats(Map<String, String> formats) {
        this.formats = formats;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", languages=" + languages +
                ", downloadCount=" + downloadCount +
                ", formats=" + formats +
                '}';
    }

}
