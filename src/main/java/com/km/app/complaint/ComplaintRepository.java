package com.km.app.complaint;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    Optional<Complaint> findByProductIdAndReporter(String productId, String reporter);
}
