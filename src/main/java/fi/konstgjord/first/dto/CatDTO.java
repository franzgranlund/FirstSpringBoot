package fi.konstgjord.first.dto;

import jakarta.validation.constraints.NotBlank;
public record CatDTO(Long id, @NotBlank(message = "Name cannot be blank") String name, Long colorId) {}