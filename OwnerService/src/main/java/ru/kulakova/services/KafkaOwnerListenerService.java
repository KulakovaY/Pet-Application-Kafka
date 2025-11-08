package ru.kulakova.srevices;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import ru.kulakova.entities.Owner;
import ru.kulakova.entities.dto.*;
import ru.kulakova.entities.mappers.OwnerMapperM;
import ru.kulakova.repositories.OwnerRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class KafkaOwnerListenerService {

    private final OwnerRepository ownerRepository;
    private final OwnerMapperM ownerMapper;

    @Autowired
    public KafkaOwnerListenerService(
            OwnerRepository ownerRepository
    ) {
        this.ownerRepository = ownerRepository;
        this.ownerMapper = new OwnerMapperM();
    }

    @KafkaListener(topics = "owner-get-all", groupId = "owner")
    @SendTo
    public List<OwnerDTO> getAllOwners() {
        return  ownerRepository.findAll()
                .stream()
                .map(ownerMapper::toDto)
                .collect(Collectors.toList());
    }

    @KafkaListener(topics = "owner-get-by-id", groupId = "owner")
    @SendTo
    public OwnerDTO getOwnerById(String id) {
        Long ownerId = Long.parseLong(id);
        var owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("No owner found with id: " + id));
        return ownerMapper.toDto(owner);
    }

    @KafkaListener(topics = "owner-create", groupId = "owner")
    @SendTo
    public OwnerDTO createOwner(OwnerDTO ownerDTO) {
        Owner ownerEntity = ownerMapper.toEntity(ownerDTO);
        var owner = ownerRepository.save(ownerEntity);
        return ownerMapper.toDto(owner);
    }

    @KafkaListener(topics = "owner-update", groupId = "owner")
    @SendTo
    public OwnerDTO updateOwner(OwnerDTO ownerDTO) {
        if (!ownerRepository.existsById(ownerDTO.getId())){
            throw new EntityNotFoundException("Owner not found with id: " + ownerDTO.getId());
        }
        Owner ownerEntity = ownerMapper.toEntity(ownerDTO);
        var owner = ownerRepository.save(ownerEntity);
        return ownerMapper.toDto(owner);
    }

    @KafkaListener(topics = "owner-delete-by-id", groupId = "owner")
    public void deleteOwnerById(String id) {
        Long ownerId = Long.parseLong(id);
        if (!ownerRepository.existsById(ownerId)){
            throw new EntityNotFoundException("Owner not found with id: " + id);
        }
        ownerRepository.deleteById(ownerId);
    }

    @KafkaListener(topics = "owner-shelter-pet", groupId = "owner")
    @SendTo
    public OwnerDTO shelterPet(TwoIdsDTO idsDTO) {
        OwnerDTO ownerDTO = ownerMapper.toDto(ownerRepository.findById(idsDTO.getOwnerId())
                .orElseThrow(() -> new EntityNotFoundException("No owner found with " + idsDTO.getOwnerId())));
        Owner ownerEntity = ownerMapper.toEntity(ownerDTO);
        ownerEntity.addPet(idsDTO.getPetId());
        var owner = ownerRepository.save(ownerEntity);
        return ownerMapper.toDto(owner);
    }

    @KafkaListener(topics = "owner-get-by-name", groupId = "owner")
    @SendTo
    public List<OwnerDTO> getOwnerByName(String name) {
        return ownerRepository.findByName(name)
                .stream()
                .map(ownerMapper::toDto)
                .collect(Collectors.toList());
    }

    @KafkaListener(topics = "owner-get-by-exact-age", groupId = "owner")
    @SendTo
    public List<OwnerDTO> getOwnersByExactAge(String age) {
        return ownerRepository.findByExactAge(Integer.parseInt(age))
                .stream()
                .map(ownerMapper::toDto)
                .collect(Collectors.toList());
    }
}
