package io.sy.metadata.repository;

import io.sy.metadata.entity.FieldDefinitionEntity;
import org.springframework.data.repository.CrudRepository;

public interface FieldDefinitionRepository extends CrudRepository<FieldDefinitionEntity, Long> {
    Iterable<FieldDefinitionEntity> findAllByObject_Name(String name);

}
