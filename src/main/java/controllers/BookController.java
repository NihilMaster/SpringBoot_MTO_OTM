package controllers;

import entities.BookEntity;
import entities.LibraryEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import repositories.BookRepository;
import repositories.LibraryRepository;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LibraryRepository libraryRepository;

    @GetMapping
    public ResponseEntity<Page<BookEntity>> getAllBooks (Pageable pageable) {
        return ResponseEntity.ok(bookRepository.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookEntity> getBookById (@PathVariable int id) {
        Optional<BookEntity> optionalBook = bookRepository.findById(id);
        return optionalBook.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.unprocessableEntity().build());
    }

    @PostMapping
    public ResponseEntity<BookEntity> saveBook (@Valid @RequestBody BookEntity book) {
        Optional<LibraryEntity> optionalLibrary = libraryRepository.findById(book.getLibrary().getId());
        if (!optionalLibrary.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        book.setLibrary(optionalLibrary.get());
        BookEntity savedBook = bookRepository.save(book);
        URI saveLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedBook.getId()).toUri();
        return ResponseEntity.created(saveLocation).body(savedBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookEntity> updateBook (@PathVariable int id, @Valid @RequestBody BookEntity book) {
        Optional<LibraryEntity> optionalLibrary = libraryRepository.findById(book.getLibrary().getId());
        if (!optionalLibrary.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        Optional<BookEntity> optionalBook = bookRepository.findById(id);
        if (!optionalBook.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        book.setId(id);
        book.setLibrary(optionalLibrary.get());
        bookRepository.save(book);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookEntity> deleteBook (@PathVariable int id) {
        Optional<BookEntity> optionalBook = bookRepository.findById(id);
        if (!optionalBook.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
