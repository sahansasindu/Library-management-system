package com.example.Library.Management.System.repository;

import com.example.Library.Management.System.entity.ReturnBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnBookRepository extends JpaRepository<ReturnBook,Long> {
}
