package com.example.Library.Management.System.repository;

import com.example.Library.Management.System.entity.Transection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransectionRepository extends JpaRepository<Transection, Long> {
}
