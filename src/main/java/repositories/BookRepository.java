package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import entities.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, Integer> {
}
