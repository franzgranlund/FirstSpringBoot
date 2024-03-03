package fi.konstgjord.first.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.konstgjord.first.dto.CatDTO;
import fi.konstgjord.first.service.CatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cats")
public class CatController {
    private static final Logger logger = LoggerFactory.getLogger(CatController.class);

    private final ObjectMapper objectMapper;

    private final CatService catService;

    @Autowired
    public CatController(CatService catService, ObjectMapper objectMapper) {
        this.catService = catService;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public ResponseEntity<List<CatDTO>> getAllCats() {
        logger.info("getAllCats");
        List<CatDTO> cats = catService.getAllCats();
        return new ResponseEntity<>(cats, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatDTO> getCatById(@PathVariable Long id) {
        return catService.getCatById(id)
                .map(catDTO -> new ResponseEntity<>(catDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<CatDTO> createCat(@Validated @RequestBody CatDTO catDTO) throws JsonProcessingException {
        String catJson = objectMapper.writeValueAsString(catDTO);
        logger.info("createCat: {}", catJson);
        CatDTO createdCatDTO = catService.createCat(catDTO);
        return new ResponseEntity<>(createdCatDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCat(@PathVariable Long id) {
        catService.deleteCat(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}