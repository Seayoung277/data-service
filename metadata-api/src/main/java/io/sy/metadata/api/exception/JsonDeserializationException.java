package io.sy.metadata.api.exception;

public class JsonDeserializationException extends RuntimeException {
    public JsonDeserializationException(String message, Exception ex) {
        super(message, ex);
    }
}
