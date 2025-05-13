package com.km.app.complaint;

import jakarta.persistence.EntityNotFoundException;

class ComplaintNotFoundException extends EntityNotFoundException {

    ComplaintNotFoundException(Long id) {
        super(String.format("Complaint with id %s not found.", id));
    }
}
