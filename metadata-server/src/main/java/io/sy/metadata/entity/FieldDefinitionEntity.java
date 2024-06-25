package io.sy.metadata.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sy.metadata.api.model.FieldDefinition;
import io.sy.metadata.api.common.DataType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldDefinitionEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    private DataType type;

    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private ObjectDefinitionEntity object;

    public static FieldDefinitionEntity fromInput(FieldDefinition fieldDefinition) {
        return FieldDefinitionEntity.builder()
                .name(fieldDefinition.getName())
                .description(fieldDefinition.getDescription())
                .type(fieldDefinition.getType())
                .build();
    }

    public static FieldDefinitionEntity fromInputForObject(FieldDefinition fieldDefinition, ObjectDefinitionEntity objectDefinitionEntity) {
        return FieldDefinitionEntity.builder()
                .object(objectDefinitionEntity)
                .name(fieldDefinition.getName())
                .description(fieldDefinition.getDescription())
                .type(fieldDefinition.getType())
                .build();
    }
}
