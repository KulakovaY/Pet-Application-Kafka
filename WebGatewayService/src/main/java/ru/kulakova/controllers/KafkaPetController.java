package ru.kulakova.controllers;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.kulakova.entities.dto.PetDTO;
import ru.kulakova.entities.dto.TwoIdsDTO;

import java.util.List;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("/api/pets")
@PreAuthorize("isAuthenticated()")
public class KafkaPetController {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ReplyingKafkaTemplate<String, Object, Object> replyingKafkaTemplate;

    @Autowired
    public KafkaPetController(@Qualifier("replyingTemplate") ReplyingKafkaTemplate<String, Object, Object> replyingKafkaTemplate,
                              @Qualifier("kafkaTemplate") KafkaTemplate<String, Object> kafkaTemplate) {
        this.replyingKafkaTemplate = replyingKafkaTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping
    public List<PetDTO> getAllPets() throws ExecutionException, InterruptedException {
        ProducerRecord<String, Object> record = new ProducerRecord<>("pet-get-all", null);
        ConsumerRecord<String, Object> response = replyingKafkaTemplate.sendAndReceive(record).get();
        return (List<PetDTO>) response.value();
    }

    @GetMapping("/{id}")
    public PetDTO getPetById(@PathVariable("id") Long id) throws ExecutionException, InterruptedException {
        ProducerRecord<String, Object> record = new ProducerRecord<>("pet-get-by-id", id.toString());
        ConsumerRecord<String, Object> response = replyingKafkaTemplate.sendAndReceive(record).get();
        return (PetDTO) response.value();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PetDTO createPet(@RequestBody PetDTO petDTO) throws ExecutionException, InterruptedException {
        ProducerRecord<String, Object> record = new ProducerRecord<>("pet-create", petDTO);
        ConsumerRecord<String, Object> response = replyingKafkaTemplate.sendAndReceive(record).get();
        return (PetDTO) response.value();
    }

    @PutMapping("/{id}")
    public PetDTO updatePet(@RequestBody PetDTO petDTO) throws ExecutionException, InterruptedException {
        ProducerRecord<String, Object> record = new ProducerRecord<>("pet-update", petDTO);
        ConsumerRecord<String, Object> response = replyingKafkaTemplate.sendAndReceive(record).get();
        return (PetDTO) response.value();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePet(@PathVariable("id") Long id) {
        kafkaTemplate.send("pet-delete-by-id", id.toString());
    }

    @GetMapping("/{id}/friends")
    public List<PetDTO> getPetFriends(@PathVariable("id") Long id) throws ExecutionException, InterruptedException {
        ProducerRecord<String, Object> record = new ProducerRecord<>("pet-update", id.toString());
        ConsumerRecord<String, Object> response = replyingKafkaTemplate.sendAndReceive(record).get();
        return (List<PetDTO>) response.value();
    }

    @GetMapping("/search/by-name")
    public List<PetDTO> getPetByName(@RequestParam("name") String name) throws ExecutionException, InterruptedException {
        ProducerRecord<String, Object> record = new ProducerRecord<>("pet-get-by-name", name);
        ConsumerRecord<String, Object> response = replyingKafkaTemplate.sendAndReceive(record).get();
        return (List<PetDTO>) response.value();
    }

    @GetMapping("/search/by-color")
    public List<PetDTO> getPetsByColor(@RequestParam("color") String color) throws ExecutionException, InterruptedException {
        ProducerRecord<String, Object> record = new ProducerRecord<>("pet-get-by-color", color);
        ConsumerRecord<String, Object> response = replyingKafkaTemplate.sendAndReceive(record).get();
        return (List<PetDTO>) response.value();
    }

    @GetMapping("/search/by-breed")
    public List<PetDTO> getPetsByBreed(@RequestParam("breed") String breed) throws ExecutionException, InterruptedException {
        ProducerRecord<String, Object> record = new ProducerRecord<>("pet-get-by-breed", breed);
        ConsumerRecord<String, Object> response = replyingKafkaTemplate.sendAndReceive(record).get();
        return (List<PetDTO>) response.value();
    }

    @GetMapping("/search/by-age/exact")
    public List<PetDTO> getPetByExactAge(@RequestParam("age") int age) throws ExecutionException, InterruptedException {
        ProducerRecord<String, Object> record = new ProducerRecord<>("pet-get-by-exact-age", String.valueOf(age));
        ConsumerRecord<String, Object> response = replyingKafkaTemplate.sendAndReceive(record).get();
        return (List<PetDTO>) response.value();
    }


    @PostMapping("/{petId}/friends/{friendId}")
    public void makeFriends(
            @PathVariable("petId")  Long petId,
            @PathVariable("friendId") Long friendId) {
        kafkaTemplate.send("pet-make-friend", new TwoIdsDTO(petId, friendId));
    }
}
