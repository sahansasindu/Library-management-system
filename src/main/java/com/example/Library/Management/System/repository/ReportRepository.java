package com.example.Library.Management.System.repository;

import com.example.Library.Management.System.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report,Integer> {

    @Query("SELECT r FROM Report r WHERE r.book.bookId = :bookId AND r.member.member_id = :memberId")
    Report findissuedetails(@Param("bookId") String bookId, @Param("memberId") String memberId);

    @Query("SELECT r FROM Report r WHERE r.member.member_id = :memberId")
    List<Report> findByMemberId(@Param("memberId") String memberId);

    @Query("SELECT r FROM Report r WHERE r.member.member_id = :memberId")
    org.springframework.data.domain.Page<Report> findByMemberId(@Param("memberId") String memberId, org.springframework.data.domain.Pageable pageable);

    @Query("SELECT r FROM Report r WHERE " +
           "LOWER(r.member.member_id) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
           "LOWER(r.book.bookId) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    org.springframework.data.domain.Page<Report> searchIssuedBooks(@Param("searchText") String searchText, org.springframework.data.domain.Pageable pageable);
}
