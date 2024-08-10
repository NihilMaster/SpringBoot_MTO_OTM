package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import entities.LibraryEntity;

public interface LibraryRepository extends JpaRepository<LibraryEntity, Integer> {
}
