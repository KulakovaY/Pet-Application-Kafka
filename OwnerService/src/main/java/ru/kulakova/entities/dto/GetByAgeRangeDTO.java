package ru.kulakova.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetByAgeRangeDTO {
    private int minAge;
    private int maxAge;
    private int page;
    private int size;
}
