package zzz.master.REST.library.MTO.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zzz.master.REST.library.MTO.Entities.LibraryEntity;

public interface LibraryRepository extends JpaRepository<LibraryEntity, Integer> {
}
