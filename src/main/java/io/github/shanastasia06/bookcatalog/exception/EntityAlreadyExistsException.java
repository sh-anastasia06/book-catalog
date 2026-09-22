package io.github.shanastasia06.bookcatalog.exception;

import org.springframework.http.HttpStatus;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
