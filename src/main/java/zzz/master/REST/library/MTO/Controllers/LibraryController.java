package zzz.master.REST.library.MTO.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import zzz.master.REST.library.MTO.Entities.LibraryEntity;
import zzz.master.REST.library.MTO.Repositories.LibraryRepository;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/library")
public class LibraryController {

    @Autowired
    private LibraryRepository libraryRepository;

    @GetMapping
    public ResponseEntity<Page<LibraryEntity>> getAllLibraries(Pageable pageable) {
        return ResponseEntity.ok(libraryRepository.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibraryEntity> getLibraryById(@PathVariable int id) {
        Optional<LibraryEntity> optionalLibrary = libraryRepository.findById(id);
        return optionalLibrary.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.unprocessableEntity().build());
    }

    @PostMapping
    public ResponseEntity<LibraryEntity> saveLibrary(@Valid @RequestBody LibraryEntity library) {
        LibraryEntity savedLibrary = libraryRepository.save(library);
        URI saveLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedLibrary.getId()).toUri();
        return ResponseEntity.created(saveLocation).body(savedLibrary);
    }

    @PutMapping("{id}")
    public ResponseEntity<LibraryEntity> updateLibrary(@PathVariable int id, @Valid @RequestBody LibraryEntity library) {
        Optional<LibraryEntity> optionalLibrary = libraryRepository.findById(id);
        if (!optionalLibrary.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        library.setId(id);
        libraryRepository.save(library);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<LibraryEntity> deleteLibrary(@PathVariable int id) {
        Optional<LibraryEntity> optionalLibrary = libraryRepository.findById(id);
        if (!optionalLibrary.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        libraryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
