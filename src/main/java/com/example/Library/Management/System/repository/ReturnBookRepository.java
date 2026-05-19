package com.example.Library.Management.System.repository;

import com.example.Library.Management.System.entity.ReturnBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReturnBookRepository extends JpaRepository<ReturnBook,Long> {

    @Query("SELECT r FROM ReturnBook r WHERE r.member.member_id = :memberId")
    List<ReturnBook> findByMemberId(@Param("memberId") String memberId);

    @Query("SELECT r FROM ReturnBook r WHERE r.member.member_id = :memberId")
    org.springframework.data.domain.Page<ReturnBook> findByMemberId(@Param("memberId") String memberId, org.springframework.data.domain.Pageable pageable);

    @Query("SELECT r FROM ReturnBook r WHERE r.penalty_amount > 0")
    List<ReturnBook> findAllWithFines();

    @Query("SELECT r FROM ReturnBook r WHERE " +
           "LOWER(r.member.member_id) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
           "LOWER(r.book.bookId) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    org.springframework.data.domain.Page<ReturnBook> searchReturnedBooks(@Param("searchText") String searchText, org.springframework.data.domain.Pageable pageable);
}
