package com.example.Library.Management.System.repository;

import com.example.Library.Management.System.entity.ReseaveBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface ResearveBookRepository extends JpaRepository<ReseaveBook,Long> {

    @Query("SELECT r FROM ReseaveBook r WHERE r.book.id = :bookId AND r.member.id = :memberId")
    ReseaveBook findReservation(@Param("bookId") String bookId, @Param("memberId") String memberId);



}