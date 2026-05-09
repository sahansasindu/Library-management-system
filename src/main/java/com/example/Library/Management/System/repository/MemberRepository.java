package com.example.Library.Management.System.repository;

import com.example.Library.Management.System.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,String> {

    @Query("SELECT m FROM Member m WHERE m.member_id = :memberid")
    Member findByMemberId(@Param("memberid") String memberId);

    @Query("SELECT m FROM Member m WHERE LOWER(m.first_name) LIKE LOWER(CONCAT('%', :searchText, '%')) OR LOWER(m.nic_no) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    Page<Member> searchMembers(@Param("searchText") String searchText, Pageable pageable);
    
}
