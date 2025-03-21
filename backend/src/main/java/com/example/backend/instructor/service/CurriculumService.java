package com.example.backend.instructor.service;


import com.example.backend.instructor.model.Course;
import com.example.backend.instructor.model.Curriculum;
import com.example.backend.instructor.model.Instructor;
import com.example.backend.instructor.model.dto.req.CurriculumRegisterDto;
import com.example.backend.instructor.model.dto.res.CurriculumResponseDto;
import com.example.backend.instructor.repository.CourseRepository;
import com.example.backend.instructor.repository.CurriculumRepository;
import com.example.backend.instructor.repository.InstructorRepository;
import com.example.backend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class CurriculumService {
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final CurriculumRepository curriculumRepository;


    public List<Curriculum> getCurriculumBySubject(String subject) {
        return  curriculumRepository.findByCurriculumSubjectIgnoreCase(subject);
    }


    public void registerCurriculum(List<CurriculumRegisterDto> curriculumList, Course course) {
        curriculumList.forEach(curriculumDto -> {
            // curriculumRepository.save() 호출을 CurriculumService에서 처리
            curriculumRepository.save(curriculumDto.toEntity(course));
        });

    }

    // 커리큘럼만 따로 등록 (강사 대시보드 기능)
    public void register(User user, CurriculumRegisterDto dto) {
        Instructor instructor = instructorRepository.findByUserIdx(user.getIdx()).orElseThrow();
        Course course = courseRepository.findByInstructorIdx(instructor.getIdx()).orElseThrow();
        curriculumRepository.save(dto.toEntity(course));
    }
}
