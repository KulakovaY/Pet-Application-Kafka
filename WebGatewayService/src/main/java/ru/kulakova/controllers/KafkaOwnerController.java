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
import ru.kulakova.entities.dto.OwnerDTO;
import ru.kulakova.entities.dto.TwoIdsDTO;

import java.util.List;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("/api/owners")
@PreAuthorize("isAuthenticated()")
public class KafkaOwnerController {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ReplyingKafkaTemplate<String, Object, Object> replyingKafkaTemplate;

    @Autowired
    public KafkaOwnerController(@Qualifier("replyingTemplate") ReplyingKafkaTemplate<String, Object, Object> replyingKafkaTemplate,
                                @Qualifier("kafkaTemplate") KafkaTemplate<String, Object> kafkaTemplate) {
        this.replyingKafkaTemplate = replyingKafkaTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping
    public List<OwnerDTO> getAllOwners() throws ExecutionException, InterruptedException {
        ProducerRecord<String, Object> record = new ProducerRecord<>("owner-get-all", null);
        ConsumerRecord<String, Object> response = replyingKafkaTemplate.sendAndReceive(record).get();
        return (List<OwnerDTO>) response.value();
    }


    @GetMapping("/{id}")
    public OwnerDTO getOwnerById(@PathVariable("id") Long id) throws ExecutionException, InterruptedException {
        ProducerRecord<String, Object> record = new ProducerRecord<>("owner-get-by-id", id);
        ConsumerRecord<String, Object> response = replyingKafkaTemplate.sendAndReceive(record).get();
        return (OwnerDTO) response.value();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OwnerDTO createOwner(@RequestBody OwnerDTO ownerDTO) throws ExecutionException, InterruptedException {
        ProducerRecord<String, Object> record = new ProducerRecord<>("owner-create", ownerDTO);
        ConsumerRecord<String, Object> response = replyingKafkaTemplate.sendAndReceive(record).get();
        return (OwnerDTO) response.value();
    }

    @PutMapping("/{id}")
    public OwnerDTO updateOwner(@RequestBody OwnerDTO ownerDTO) throws ExecutionException, InterruptedException {
        ProducerRecord<String, Object> record = new ProducerRecord<>("owner-update", ownerDTO);
        ConsumerRecord<String, Object> response = replyingKafkaTemplate.sendAndReceive(record).get();
        return (OwnerDTO) response.value();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOwner(@PathVariable("id") Long id) {
        kafkaTemplate.send("owner-delete-by-id", id.toString());
    }

    @GetMapping("/search/by-name")
    public List<OwnerDTO> getOwnerByName(
            @RequestParam("name") String name) throws ExecutionException, InterruptedException {
        ProducerRecord<String, Object> record = new ProducerRecord<>("owner-get-by-name", name);
        ConsumerRecord<String, Object> response = replyingKafkaTemplate.sendAndReceive(record).get();
        return (List<OwnerDTO>) response.value();
    }

    @GetMapping("/search/by-age/exact")
    public List<OwnerDTO> getOwnersByExactAge(
            @RequestParam("age") int age) throws ExecutionException, InterruptedException {
        ProducerRecord<String, Object> record = new ProducerRecord<>("owner-get-by-exact-age", String.valueOf(age));
        ConsumerRecord<String, Object> response = replyingKafkaTemplate.sendAndReceive(record).get();
        return (List<OwnerDTO>) response.value();
    }

    @PostMapping("/owners/{ownerId}/pets/{petId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void shelterPet(
            @PathVariable("ownerId") Long ownerId,
            @PathVariable("petId")Long petId){
        kafkaTemplate.send("owner-shelter-pet", new TwoIdsDTO(ownerId, petId));
        kafkaTemplate.send("pet-set-owner", new TwoIdsDTO(petId, ownerId));
    }

}
