package com.km.app.exception;

import jakarta.persistence.EntityNotFoundException;

public class ComplaintNotFoundException extends EntityNotFoundException {

    public ComplaintNotFoundException(Long id) {
        super(String.format("Complaint with id %s not found.", id));
    }
}
