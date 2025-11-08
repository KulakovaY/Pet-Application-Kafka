package ru.kulakova.entities.mappers;

import ru.kulakova.entities.Pet;
import ru.kulakova.entities.dto.PetDTO;

public class PetMapperM {
    public Pet toEntity(PetDTO dto) {
        Pet pet = new Pet();
        pet.setName(dto.getName());
        pet.setBirthDate(dto.getBirthDate());
        pet.setBreed(dto.getBreed());
        pet.setColor(dto.getColor());
        pet.setOwnerId(dto.getOwnerId());

        return pet;
    }

    public PetDTO toDto(Pet entity) {
        if (entity == null) return null;

        PetDTO dto = new PetDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setBirthDate(entity.getBirthDate());
        dto.setBreed(entity.getBreed());
        dto.setColor(entity.getColor());
        dto.setOwnerId(entity.getOwnerId() != null ? entity.getOwnerId() : null);

        return dto;
    }
}