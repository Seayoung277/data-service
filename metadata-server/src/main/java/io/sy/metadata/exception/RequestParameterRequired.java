package io.sy.metadata.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequestParameterRequired extends RuntimeException {
    public RequestParameterRequired(String message) {
        super(message);
    }
}
