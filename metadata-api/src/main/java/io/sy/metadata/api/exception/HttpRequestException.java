package io.sy.metadata.api.exception;

public class HttpRequestException extends RuntimeException {
    public HttpRequestException(String message) {
        super(message);
    }

    public HttpRequestException(String message, Exception ex) {
        super(message, ex);
    }
}
