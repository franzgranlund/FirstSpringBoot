package fi.konstgjord.first.repository;

import fi.konstgjord.first.model.Cat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatRepository extends JpaRepository<Cat, Long> {
    // You can add custom query methods here if needed
}