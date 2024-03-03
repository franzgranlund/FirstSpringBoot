package fi.konstgjord.first.repository;

import fi.konstgjord.first.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color, Long> {
    // You can add custom query methods here if needed
}