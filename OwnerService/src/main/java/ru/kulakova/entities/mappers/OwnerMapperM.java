package ru.kulakova.entities.mappers;

import ru.kulakova.entities.dto.OwnerDTO;
import ru.kulakova.entities.Owner;

public class OwnerMapperM {

    public Owner toEntity(OwnerDTO dto) {
        if (dto == null) return null;

        Owner owner = new Owner();
        owner.setId(dto.getId());
        owner.setName(dto.getName());
        owner.setBirthDate(dto.getBirthDate());
        owner.setPetIds(dto.getPetIds());
        return owner;
    }

    public OwnerDTO toDto(Owner entity) {
        if (entity == null) return null;

        OwnerDTO dto = new OwnerDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setBirthDate(entity.getBirthDate());
        dto.setPetIds(entity.getPetIds());

        return dto;
    }
}