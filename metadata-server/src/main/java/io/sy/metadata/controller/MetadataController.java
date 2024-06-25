package io.sy.metadata.controller;

import com.mongodb.client.MongoDatabase;
import io.sy.metadata.api.model.FieldDefinition;
import io.sy.metadata.api.model.ObjectDefinition;
import io.sy.metadata.entity.FieldDefinitionEntity;
import io.sy.metadata.entity.ObjectDefinitionEntity;
import io.sy.metadata.exception.ObjectDefinitionNotFoundException;
import io.sy.metadata.repository.FieldDefinitionRepository;
import io.sy.metadata.repository.ObjectDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metadata")
public class MetadataController {
    private final MongoDatabase mongoDatabase;
    private final ObjectDefinitionRepository objectDefinitionRepository;
    private final FieldDefinitionRepository fieldDefinitionRepository;

    @Autowired
    public MetadataController(
            MongoDatabase mongoDatabase,
            ObjectDefinitionRepository objectDefinitionRepository,
            FieldDefinitionRepository fieldDefinitionRepository
    ) {
        this.mongoDatabase = mongoDatabase;
        this.objectDefinitionRepository = objectDefinitionRepository;
        this.fieldDefinitionRepository = fieldDefinitionRepository;
    }

    @GetMapping("/objects")
    public Iterable<ObjectDefinitionEntity> getAllObjectDefinitions() {
        return objectDefinitionRepository.findAll();
    }

    @PostMapping("/object")
    public ObjectDefinitionEntity createObjectDefinition(@RequestBody ObjectDefinition objectDefinition) {
        mongoDatabase.createCollection(objectDefinition.getName());
        return objectDefinitionRepository.save(ObjectDefinitionEntity.fromInput(objectDefinition));
    }

    @GetMapping("/object/{name}")
    public ObjectDefinitionEntity getObjectDefinitionById(@PathVariable String name) {
        return objectDefinitionRepository.findByName(name).orElseThrow(ObjectDefinitionNotFoundException::new);
    }

    @DeleteMapping("/object/{name}")
    public ResponseEntity<Void> deleteObjectDefinitionByName(@PathVariable String name) {
        objectDefinitionRepository.findByName(name).ifPresentOrElse(
                objectDefinitionEntity -> {
                    mongoDatabase.getCollection(objectDefinitionEntity.getName()).drop();
                    objectDefinitionRepository.deleteById(objectDefinitionEntity.getId());
                },
                () -> { throw new ObjectDefinitionNotFoundException(); }
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/object/{name}/fields")
    public Iterable<FieldDefinitionEntity> getAllFieldsByObjectName(@PathVariable String name) {
        return fieldDefinitionRepository.findAllByObject_Name(name);
    }

    @PostMapping("/object/{name}/field")
    public FieldDefinitionEntity createFieldDefinition(@PathVariable String name, @RequestBody FieldDefinition fieldDefinition) {
        ObjectDefinitionEntity objectDefinitionEntity = objectDefinitionRepository.findByName(name).orElseThrow(ObjectDefinitionNotFoundException::new);
        return fieldDefinitionRepository.save(FieldDefinitionEntity.fromInputForObject(fieldDefinition, objectDefinitionEntity));
    }
}
