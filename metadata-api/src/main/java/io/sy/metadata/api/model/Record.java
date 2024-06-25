package io.sy.metadata.api.model;

import io.sy.metadata.api.common.DataType;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import org.bson.Document;

import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
public class Record {
    private ObjectDefinition definition;
    @Singular private Map<String, Value> values;

    public static Record fromBson(Document bson, ObjectDefinition objectDefinition) {
        return Record.builder()
                .definition(objectDefinition)
                .values(
                        objectDefinition.getFields()
                                .stream()
                                .collect(
                                        Collectors.toMap(
                                                FieldDefinition::getName,
                                                fieldDefinition -> Value.builder()
                                                        .definition(fieldDefinition)
                                                        .value(readValue(bson, fieldDefinition.getName(), fieldDefinition.getType()))
                                                        .build()
                                        )
                                )
                )
                .build();
    }

    private static Object readValue(Document bson, String name, DataType type) {
        return switch (type) {
            case Integer -> bson.getInteger(name);
            case Double -> bson.getDouble(name);
            case Boolean -> bson.getBoolean(name);
            case String -> bson.getString(name);
            case Date -> bson.getDate(name);
        };
    }
}
