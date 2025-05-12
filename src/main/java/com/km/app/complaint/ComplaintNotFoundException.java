package com.km.app.complaint;

class ComplaintNotFoundException extends RuntimeException {

    ComplaintNotFoundException(Long id) {
        super("Complaint with id " + id + " not found.");
    }
}
