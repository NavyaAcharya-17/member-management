package com.surest.member_management.exception;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ResourceAlreadyExistsException extends RuntimeException{

    public ResourceAlreadyExistsException() {
        super("Resource Already Exists");
    }
}
