package com.example.Library.Management.System.repository;

import com.example.Library.Management.System.entity.Report;
import com.example.Library.Management.System.entity.ReseaveBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report,Integer> {


    @Query("SELECT r FROM Report r WHERE r.book.id = :bookId AND r.member.id = :memberId")
    Report findissuedetails(@Param("bookId") String bookId, @Param("memberId") String memberId);
}
