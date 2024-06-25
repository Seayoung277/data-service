package io.sy.metadata.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.sy.metadata.api.common.DataType;
import io.sy.metadata.api.exception.JsonDeserializationException;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldDefinition {
    private final String name;
    private final DataType type;
    private String description;

    public static FieldDefinition fromJson(String json) throws JsonDeserializationException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, FieldDefinition.class);
        } catch (JsonProcessingException ex) {
            throw new JsonDeserializationException("Unable to deserialize JSON string to FieldDefinition", ex);
        }
    }
}
