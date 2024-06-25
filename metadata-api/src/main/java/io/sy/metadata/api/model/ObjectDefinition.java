package io.sy.metadata.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.sy.metadata.api.exception.JsonDeserializationException;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class ObjectDefinition {
    private final String name;
    @Singular private final List<FieldDefinition> fields;
    private String description;

    public static ObjectDefinition fromJson(String json) throws JsonDeserializationException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, ObjectDefinition.class);
        } catch (JsonProcessingException ex) {
            throw new JsonDeserializationException("Unable to deserialize JSON string to ObjectDefinition", ex);
        }
    }
}
