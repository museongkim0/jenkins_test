package com.example.backend.instructor.repository;


import com.example.backend.instructor.model.Curriculum;
import com.example.backend.instructor.model.dto.res.CurriculumResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Integer> {

    List<Curriculum> findByCurriculumSubjectIgnoreCase(String subject);

}
