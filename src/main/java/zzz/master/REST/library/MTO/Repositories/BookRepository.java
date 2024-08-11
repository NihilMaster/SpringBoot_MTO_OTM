package zzz.master.REST.library.MTO.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import zzz.master.REST.library.MTO.Entities.BookEntity;
import zzz.master.REST.library.MTO.Entities.LibraryEntity;

public interface BookRepository extends JpaRepository<BookEntity, Integer> {
    Page<BookEntity> findAllByLibrary(LibraryEntity library, Pageable pageable);
}
