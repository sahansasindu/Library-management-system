package com.example.Library.Management.System.repository;

import com.example.Library.Management.System.entity.Member;
import com.example.Library.Management.System.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User>findByEmail(String email);

    Optional<User> findByMemberid(Member member);




}
