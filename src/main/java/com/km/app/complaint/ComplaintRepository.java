package com.km.app.complaint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // To translate JPA/Hibernate exceptions to Spring's DataAccessException
interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    @Query("SELECT c.id FROM Complaint c WHERE c.productId = :productId AND c.reporter = :reporter")
    Optional<Long> findIdByProductIdAndReporter(@Param("productId") String productId, @Param("reporter") String reporter);

    @Modifying
    @Query("UPDATE Complaint c SET c.reportCount = c.reportCount + 1 WHERE c.id = :id")
    void incrementReportCount(@Param("id") long id);

    @Modifying
    @Query("UPDATE Complaint c SET c.content = :content WHERE c.id = :id")
    int updateContent(@Param("id") Long id, @Param("content") String content);
}
