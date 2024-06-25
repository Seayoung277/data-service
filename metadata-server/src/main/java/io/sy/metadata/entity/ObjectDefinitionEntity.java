package io.sy.metadata.entity;

import io.sy.metadata.api.model.ObjectDefinition;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObjectDefinitionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String description;

    @OneToMany(mappedBy = "object", cascade = CascadeType.ALL)
    private List<FieldDefinitionEntity> fields;

    public static ObjectDefinitionEntity fromInput(ObjectDefinition objectDefinition) {
        ObjectDefinitionEntity objectDefinitionEntity = ObjectDefinitionEntity.builder()
                .name(objectDefinition.getName())
                .description(objectDefinition.getDescription())
                .fields(
                        objectDefinition.getFields().stream()
                                .map(FieldDefinitionEntity::fromInput)
                                .toList()
                )
                .build();
        for (FieldDefinitionEntity fieldDefinitionEntity: objectDefinitionEntity.getFields()) {
            fieldDefinitionEntity.setObject(objectDefinitionEntity);
        }
        return objectDefinitionEntity;
    }
}
