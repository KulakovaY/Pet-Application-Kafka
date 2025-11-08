package ru.kulakova.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kulakova.entities.enums.PetColor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetDTO {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private String breed;
    private PetColor color;
    private Long ownerId;
}