package com.example.Library.Management.System.repository;

import com.example.Library.Management.System.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    Optional<Book> findByBookId(String bookId);

    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(String title, String category);

    Page<Book> findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(String title, String category, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.active_state = true")
    Page<Book> findByActiveStateTrue(Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.active_state = true AND (LOWER(b.title) LIKE LOWER(CONCAT('%', :searchText, '%')) OR LOWER(b.category) LIKE LOWER(CONCAT('%', :searchText, '%')))")
    Page<Book> findByActiveStateTrueAndSearch(@org.springframework.data.repository.query.Param("searchText") String searchText, Pageable pageable);

    @Query("SELECT b.category, COUNT(b) FROM Book b GROUP BY b.category")
    List<Object[]> countBooksByCategory();
}

