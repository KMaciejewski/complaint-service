package com.km.app.complaint;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "complaints", uniqueConstraints = @UniqueConstraint(columnNames = {"productId", "reporter"}))
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String productId;

    @Column(length = 1000)
    @Size(max = 1000)
    private String content;

    private LocalDateTime createdAt;
    private String reporter;
    private String country;
    private int reportCount;
}
