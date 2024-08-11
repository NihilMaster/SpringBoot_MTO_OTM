package zzz.master.REST.library.MTO.Controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import zzz.master.REST.library.MTO.Entities.BookEntity;
import zzz.master.REST.library.MTO.Entities.LibraryEntity;
import zzz.master.REST.library.MTO.Repositories.BookRepository;
import zzz.master.REST.library.MTO.Repositories.LibraryRepository;


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

    @GetMapping("/library/{id}")
    public ResponseEntity<Page<BookEntity>> getBooksByLibraryId (@PathVariable int id, Pageable pageable) {
        Optional<LibraryEntity> optionalLibrary = libraryRepository.findById(id);
        if (!optionalLibrary.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(bookRepository.findAllByLibrary(optionalLibrary.get(), pageable));
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
