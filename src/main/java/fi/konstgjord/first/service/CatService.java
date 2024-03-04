package fi.konstgjord.first.service;

import fi.konstgjord.first.dto.CatDTO;
import fi.konstgjord.first.mapper.CatMapper;
import fi.konstgjord.first.model.Cat;
import fi.konstgjord.first.model.Color;
import fi.konstgjord.first.repository.CatRepository;
import fi.konstgjord.first.repository.ColorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CatService {
    private static final Logger logger = LoggerFactory.getLogger(CatService.class);

    private final CatRepository catRepository;
    private final ColorRepository colorRepository;
    private final CatMapper catMapper;

    @Autowired
    public CatService(CatRepository catRepository, ColorRepository colorRepository, CatMapper catMapper) {
        this.catRepository = catRepository;
        this.colorRepository = colorRepository;
        this.catMapper = catMapper;
    }

    public List<CatDTO> getAllCats() {
        logger.info("getAllCats()");
        return catRepository.findAll().stream()
                .map(catMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<CatDTO> getCatById(Long id) {
        return catRepository.findById(id).map(catMapper::toDTO);
    }

    public List<CatDTO> getCatsByColorName(String colorName) {
        Color color = colorRepository.findByColorName(colorName);
        if (color != null) {
            // Assuming you have a method to convert Cat entities to CatDTOs
            return catRepository.findAllByColor(color).stream()
                    .map(catMapper::toDTO)
                    .collect(Collectors.toList());
        }
        return List.of(); // Return an empty list if color is not found
    }

    public CatDTO createCat(CatDTO catDTO) {
        logger.info("createCat()");
        Cat cat = new Cat();
        cat.setName(catDTO.name());

        Optional<Color> oColor = colorRepository.findById(catDTO.colorId());
        if (oColor.isEmpty()) {
            return null;
        }

        cat.setColor(oColor.get());

        return catMapper.toDTO(catRepository.save(cat));
    }

    public void deleteCat(Long id) {
        catRepository.deleteById(id);
    }
}
