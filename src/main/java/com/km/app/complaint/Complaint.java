package com.km.app.complaint;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"productId", "reporter"}))
class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String productId;

    @Column(length = 1000)
    String content;
    LocalDateTime createdAt;
    String reporter;
    String country;
    int reportCount;
}
