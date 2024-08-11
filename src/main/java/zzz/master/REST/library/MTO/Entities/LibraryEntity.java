package zzz.master.REST.library.MTO.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "libraries")
public class LibraryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String name;

    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL)
    private Set<BookEntity> books = new HashSet<>(); // Una librería puede tener muchos libros

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public Set<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(Set<BookEntity> books) {
        this.books = books;
        for(BookEntity book : books) {
            book.setLibrary(this);
        }
    }
}
