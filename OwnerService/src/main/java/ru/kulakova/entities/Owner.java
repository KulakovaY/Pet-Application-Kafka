package ru.kulakova.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Owner{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "BirthDate")
    private LocalDate birthDate;

    @ElementCollection
    @CollectionTable(
            name = "OwnerPets",
            joinColumns = @JoinColumn(name = "OwmerId")
    )
    @Column(name = "PetId")
    private List<Long> petIds = new ArrayList<>();

    public void addPet(Long petId){
        petIds.add(petId);
    }

}
