package ru.kulakova.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kulakova.entities.Owner;

import java.util.List;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    @Query("SELECT o FROM Owner o WHERE o.name = :name")
    List<Owner> findByName(@Param("name") String name);

    @Query("SELECT o FROM Owner o WHERE FUNCTION('date_part', 'year', AGE(o.birthDate)) = :exactAge")
    List<Owner> findByExactAge(@Param("exactAge") int exactAge);

    @Query("SELECT o FROM Owner o WHERE :petId MEMBER OF o.petIds")
    List<Owner> findByPetId(@Param("petId") Long petId);
}
