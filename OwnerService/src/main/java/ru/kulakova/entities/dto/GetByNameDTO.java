package ru.kulakova.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetByNameDTO {
    private String name;
    private int page;
    private int size;
}