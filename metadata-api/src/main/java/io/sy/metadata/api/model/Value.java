package io.sy.metadata.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Value {
    private FieldDefinition definition;
    private Object value;
}
