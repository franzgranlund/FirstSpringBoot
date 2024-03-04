package fi.konstgjord.first.repository;

import fi.konstgjord.first.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ColorRepository extends JpaRepository<Color, Long> {
    // We do not need to manually implement the method if it follows
    // Spring Data JPAs query method conventions.
    Color findByColorName(String colorName);

    // It is also possible to do it like this
    @Query("SELECT c FROM Color c WHERE c.colorName = :colorName")
    Color findByZeColor(@Param("colorName") String colorName);
}