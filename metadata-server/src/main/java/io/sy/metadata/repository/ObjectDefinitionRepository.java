package io.sy.metadata.repository;

import io.sy.metadata.entity.ObjectDefinitionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ObjectDefinitionRepository extends CrudRepository<ObjectDefinitionEntity, Long> {
    Optional<ObjectDefinitionEntity> findByName(String name);
}
