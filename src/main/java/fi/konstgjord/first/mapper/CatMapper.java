package fi.konstgjord.first.mapper;

import fi.konstgjord.first.dto.CatDTO;
import fi.konstgjord.first.model.Cat;
import org.springframework.stereotype.Component;

@Component
public class CatMapper {

    public CatDTO toDTO(Cat cat) {
        return new CatDTO(cat.getId(), cat.getName(), cat.getColor().getId());
    }
}
