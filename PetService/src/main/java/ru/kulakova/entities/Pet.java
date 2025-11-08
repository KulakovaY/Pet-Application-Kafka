package ru.kulakova.entities;

import jakarta.persistence.*;
import lombok.*;
import ru.kulakova.enums.PetColor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "BirthDate")
    private LocalDate birthDate;

    @Column(name = "Breed")
    private String breed;

    @Enumerated(EnumType.STRING)
    @Column(name = "Color")
    private PetColor color;

    @JoinColumn(name = "OwnerId", nullable = true)
    private Long ownerId;

    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "PetFriends",
            joinColumns = @JoinColumn(name = "PetId"),
            inverseJoinColumns = @JoinColumn(name = "FriendId")
    )
    private Set<Pet> friends = new HashSet<>();

    public void addFriend(Pet pet) {
        friends.add(pet);
    }
}