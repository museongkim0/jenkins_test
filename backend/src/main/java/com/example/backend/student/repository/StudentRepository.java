package com.example.backend.student.repository;

import com.example.backend.student.model.StudentDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentDetail, Long> {

    @Query("SELECT sd FROM StudentDetail sd JOIN FETCH sd.user u")
    Page<StudentDetail> findAllStudents(PageRequest of);

    @Query("SELECT sd FROM StudentDetail sd JOIN FETCH sd.user u WHERE u.idx = :idx")
    Optional<StudentDetail> findByStudent(Long idx);

    Optional<StudentDetail> findByUserIdx(Long idx);
}