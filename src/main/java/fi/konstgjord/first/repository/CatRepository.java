package fi.konstgjord.first.repository;

import fi.konstgjord.first.model.Cat;
import fi.konstgjord.first.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CatRepository extends JpaRepository<Cat, Long> {
    // We do not need to manually implement the method if it follows
    // Spring Data JPA's query method conventions.
    List<Cat> findAllByColor(Color color);
}