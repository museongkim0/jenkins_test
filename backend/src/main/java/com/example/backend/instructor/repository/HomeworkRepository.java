package com.example.backend.instructor.repository;


import com.example.backend.instructor.model.Homework;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HomeworkRepository  extends JpaRepository<Homework, Long> {

    Optional<Long> findIdxByIdx(Long idx);




}
