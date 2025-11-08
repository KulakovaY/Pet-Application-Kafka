package ru.kulakova.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kulakova.entities.Pet;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query("SELECT p FROM Pet p WHERE p.name = :name")
    List<Pet> findByName(@Param("name") String name);

    @Query("SELECT p FROM Pet p WHERE p.color = :color")
    List<Pet> findByColor(@Param("color") String color);

    @Query("SELECT p FROM Pet p WHERE p.breed = :breed")
    List<Pet> findByBreed(@Param("breed") String breed);

    @Query("SELECT p FROM Pet p WHERE FUNCTION('date_part', 'year', AGE(p.birthDate)) = :exactAge")
    List<Pet> findByExactAge(@Param("age") int age);

    @Query("SELECT f FROM Pet p JOIN p.friends f WHERE p.id = :petId")
    List<Pet> findFriendsByPetId(@Param("petId") Long petId);
}