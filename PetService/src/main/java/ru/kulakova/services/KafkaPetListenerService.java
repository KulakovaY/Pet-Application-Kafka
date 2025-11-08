package ru.kulakova.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import ru.kulakova.entities.Pet;
import ru.kulakova.entities.dto.PetDTO;
import ru.kulakova.entities.dto.TwoIdsDTO;
import ru.kulakova.entities.mappers.PetMapperM;
import ru.kulakova.repositories.PetRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class KafkaPetListenerService {

    private final PetRepository petRepository;
    private final PetMapperM petMapper;

    @Autowired
    public KafkaPetListenerService(PetRepository petRepository){
        this.petRepository = petRepository;
        this.petMapper = new PetMapperM();
    }

    @KafkaListener(topics = "pet-get-all", groupId = "pet")
    @SendTo
    public List<PetDTO> getAllPets() {
        return petRepository.findAll().stream()
                .map(petMapper::toDto)
                .collect(Collectors.toList());
    }

    @KafkaListener(topics = "pet-get-by-id", groupId = "pet")
    @SendTo
    public PetDTO getPetById(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No pet found with id: " + id));
        return petMapper.toDto(pet);
    }

    @KafkaListener(topics = "pet-create", groupId = "pet")
    @SendTo
    public PetDTO createPet(PetDTO dto) {
        Pet pet = petRepository.save(petMapper.toEntity(dto));
        return petMapper.toDto(pet);
    }

    @KafkaListener(topics = "pet-update", groupId = "pet")
    @SendTo
    public PetDTO updatePet(PetDTO dto) {
        if (!petRepository.existsById(dto.getId())){
            throw new EntityNotFoundException("Pet not found with id: " + dto.getId());
        }
        Pet pet = petRepository.save(petMapper.toEntity(dto));
        return petMapper.toDto(pet);
    }

    @KafkaListener(topics = "pet-delete-by-id", groupId = "pet")
    public void deletePetById(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pet not found with id: " + id));
        pet.getFriends().clear();

        List<Pet> allPets = petRepository.findAll();
        for (Pet otherPet : allPets) {
            if (otherPet.getFriends().remove(pet)) {
                petRepository.save(otherPet);
            }
        }

        petRepository.save(pet);
        petRepository.delete(pet);
    }

    @KafkaListener(topics = "pet-get-friends", groupId = "pet")
    @SendTo
    public List<PetDTO> getPetFriends(Long id) {
        if (!petRepository.existsById(id)){
            throw new EntityNotFoundException("Pet not found with id: " + id);
        }
        return petRepository.findFriendsByPetId(id).stream()
                .map(petMapper::toDto)
                .collect(Collectors.toList());
    }

    @KafkaListener(topics = "pet-set-owner", groupId = "pet")
    @SendTo
    public void setPetOwner(TwoIdsDTO dto) {
        PetDTO petDTO = getPetById(dto.getFirstId());
        if (petDTO.getOwnerId() != null) return;
        petDTO.setOwnerId(dto.getSecondId());
        petRepository.save(petMapper.toEntity(petDTO));
    }

    @KafkaListener(topics = "pet-make-friend", groupId = "pet")
    @SendTo
    public PetDTO makeFriend(TwoIdsDTO dto) {
        Long petId = dto.getFirstId();
        Long friendId = dto.getSecondId();
        if (petId.equals(friendId)) return null;
        Pet pet = petMapper.toEntity(getPetById(petId));
        Pet friend = petMapper.toEntity(getPetById(friendId));
        pet.addFriend(friend);
        petRepository.save(pet);
        return petMapper.toDto(pet);
    }

    @KafkaListener(topics = "pet-get-by-name", groupId = "pet")
    @SendTo
    public List<PetDTO> getPetByName(String name) {
        return petRepository.findByName(name).stream()
                .map(petMapper::toDto)
                .collect(Collectors.toList());
    }

    @KafkaListener(topics = "pet-get-by-color", groupId = "pet")
    @SendTo
    public List<PetDTO> getPetsByColor(String color) {
        return petRepository.findByColor(color).stream()
                .map(petMapper::toDto)
                .collect(Collectors.toList());
    }

    @KafkaListener(topics = "pet-get-by-breed", groupId = "pet")
    @SendTo
    public List<PetDTO> getPetsByBreed(String breed) {
        return petRepository.findByBreed(breed).stream()
                .map(petMapper::toDto)
                .collect(Collectors.toList());
    }

    @KafkaListener(topics = "pet-get-by-exact-age", groupId = "pet")
    @SendTo
    public List<PetDTO> getPetsByExactAge(String age) {
        return petRepository.findByExactAge(Integer.parseInt(age)).stream()
                .map(petMapper::toDto)
                .collect(Collectors.toList());
    }
}
