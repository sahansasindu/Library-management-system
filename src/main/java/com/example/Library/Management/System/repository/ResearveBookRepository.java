package com.example.Library.Management.System.repository;

import com.example.Library.Management.System.entity.ReseaveBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ResearveBookRepository extends JpaRepository<ReseaveBook,Long> {

    @Query("SELECT r FROM ReseaveBook r WHERE r.book.bookId = :bookId AND r.member.member_id = :memberId")
    ReseaveBook findReservation(@Param("bookId") String bookId, @Param("memberId") String memberId);

    @Query("SELECT r FROM ReseaveBook r WHERE r.dueDate < :currentDate AND r.state = true")
    List<ReseaveBook> findExpiredReservations(@Param("currentDate") Date currentDate);

    @Query("SELECT r FROM ReseaveBook r WHERE r.member.member_id = :memberId and r.state=true ")
    List<ReseaveBook> findByMemberId(@Param("memberId") String memberId);

    List<ReseaveBook> findByStateTrue();

    org.springframework.data.domain.Page<ReseaveBook> findByStateTrue(org.springframework.data.domain.Pageable pageable);

    long countByStateTrue();
}