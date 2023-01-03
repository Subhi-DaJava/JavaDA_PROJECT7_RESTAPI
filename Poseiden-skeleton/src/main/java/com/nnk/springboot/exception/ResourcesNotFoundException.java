package com.nnk.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * Throw ResourceNotFoundException when the domain doesn't exist in DDB!
 * @author Subhi
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "This resource doesn't exist in DDB!")
public class ResourcesNotFoundException extends RuntimeException {
    public ResourcesNotFoundException(String message) {
    }
}
