package zzz.master.REST.library.MTO.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zzz.master.REST.library.MTO.Entities.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, Integer> {
}
