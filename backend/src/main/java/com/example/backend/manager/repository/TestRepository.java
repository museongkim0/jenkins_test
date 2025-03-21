package com.example.backend.manager.repository;

import com.example.backend.manager.model.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {
    @Query("SELECT t FROM Test t JOIN FETCH t.course")
    Page<Test> findAllWithCourse(Pageable pageable);
}
