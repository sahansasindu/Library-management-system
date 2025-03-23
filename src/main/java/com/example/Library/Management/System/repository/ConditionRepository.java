package com.example.Library.Management.System.repository;

import com.example.Library.Management.System.entity.Condition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConditionRepository extends JpaRepository<Condition,Integer> {
}
