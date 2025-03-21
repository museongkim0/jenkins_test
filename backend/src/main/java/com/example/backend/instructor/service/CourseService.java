package com.example.backend.instructor.service;


import com.example.backend.global.exception.InstructorException;
import com.example.backend.global.exception.StudentException;
import com.example.backend.global.response.responseStatus.InstructorResponseStatus;
import com.example.backend.global.response.responseStatus.StudentResponseStatus;
import com.example.backend.instructor.model.Course;
import com.example.backend.instructor.model.Curriculum;
import com.example.backend.instructor.model.Instructor;
import com.example.backend.instructor.model.dto.req.CourseRegister;
import com.example.backend.instructor.model.dto.res.BootcampListResponseDto;
import com.example.backend.instructor.model.dto.res.CourseResponseDto;
import com.example.backend.instructor.model.dto.res.CurriculumResponseDto;
import com.example.backend.instructor.model.dto.res.InstructorCourseListResponseDto;
import com.example.backend.instructor.repository.CourseRepository;
import com.example.backend.instructor.repository.CurriculumRepository;
import com.example.backend.student.model.Dto.ApplyBootcampRequestDto;
import com.example.backend.student.model.Dto.StudentDetailResponseDto;
import com.example.backend.student.model.StudentDetail;
import com.example.backend.student.repository.StudentRepository;
import com.example.backend.student.service.StudentService;
import com.example.backend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final InstructorService instructorService;

    @Lazy
    private final StudentService studentService;


    private final CurriculumService curriculumService;

    // 임시로 레포 추가 나중에 고쳐야함
    private final StudentRepository studentRepository;


    // 기수등록
    @Transactional
    public void register(CourseRegister dto, User user) {
        Instructor instructor = instructorService.getInstructorByUserIdx(user.getIdx());
        Course course = dto.toEntity(instructor);

        try {
            course = courseRepository.save(course);
        } catch (Exception e) {
            throw new InstructorException(InstructorResponseStatus.COURSE_CREATION_FAILED);
        }

        curriculumService.registerCurriculum(dto.getCurriculumList(), course);
    }





    // 강사의 진행 코스 조회
    public List<InstructorCourseListResponseDto> findIstructorCourse(Long userIdx) {
        List<Course> courses = courseRepository.findByInstructorUserIdx(userIdx);
        if (courses.isEmpty()) {
            throw new InstructorException(InstructorResponseStatus.INSTRUCTOR_NOT_ASSIGNED);
        }

        return courses.stream()
                .map(InstructorCourseListResponseDto::from)
                .collect(Collectors.toList());
    }

    // 학원에서 list 조회
    public List<InstructorCourseListResponseDto> list() {
        List<Course> result = courseRepository.findAllCourses();
        if (result.isEmpty()) {
            throw new InstructorException(InstructorResponseStatus.COURSE_NOT_FOUND);
        }

        return result.stream()
                .map(InstructorCourseListResponseDto::from)
                .collect(Collectors.toList());
    }



    // 코스 상세 조회
    @Transactional(readOnly = true)
    public CourseResponseDto read(User user) {
        StudentDetail studentDetail = studentService.getStudent(user.getIdx());

        Course course = courseRepository.findAllWithCurriculumListByGeneration(studentDetail.getGeneration());
        if (course == null) {
            throw new InstructorException(InstructorResponseStatus.COURSE_NOT_FOUND);
        }

        return CourseResponseDto.from(course);
    }

    @Transactional(readOnly = true)
    public CourseResponseDto readGenCourse(int generation) {
        Course course = courseRepository.findAllWithCurriculumListByGeneration(generation);
        if (course == null) {
            throw new InstructorException(InstructorResponseStatus.COURSE_NOT_FOUND);
        }

        return CourseResponseDto.from(course);
    }

    public Course getCourse(Long courseIdx) {
        Course course = courseRepository.findById(courseIdx).orElseThrow();
        if (course == null) {
            throw new InstructorException(InstructorResponseStatus.COURSE_NOT_FOUND);
        }
        return course;
    }


    public List<BootcampListResponseDto> getBootcampCourseAll() {

        List<Course> rs = courseRepository.findAllCourses();
        return rs.stream().map(BootcampListResponseDto::from).collect(Collectors.toList());
    }


    //교과목 별 조회
    public List<CurriculumResponseDto> getCurriculumBySubject(String subject , User user ) {
        // 유저 idx 로 학생 엔티티에서 조회해서 만약 기수가 없으면 커리큘럼 조회  x
        // 조회해서 있으면 커리큘럼 반환

        StudentDetail studentDetail = studentService.getStudent(user.getIdx());

        if(studentDetail.getGeneration() !=null) {
            List<Curriculum> result = curriculumService.getCurriculumBySubject(subject);
            if (result.isEmpty()) {
                throw new InstructorException(InstructorResponseStatus.CURRICULUM_NOT_FOUND);
            }

            return result.stream().map(CurriculumResponseDto::from).collect(Collectors.toList());
        }
        throw new InstructorException(InstructorResponseStatus.CURRICULUM_NOT_FOUND);
    }

    public List<CurriculumResponseDto> getCurriculumBySubjectRe(String subject) {
        List<Curriculum> result = curriculumService.getCurriculumBySubject(subject);
        if (result.isEmpty()) {
            throw new InstructorException(InstructorResponseStatus.CURRICULUM_NOT_FOUND);
        }

        return result.stream().map(CurriculumResponseDto::from).collect(Collectors.toList());
    }


    public void applyBootcamp(Long courseIdx, User user) {
        Optional<StudentDetail> result = studentRepository.findByUserIdx(user.getIdx());


        result.filter(studentDetail -> studentDetail.getGeneration() != null)
                .ifPresent(studentDetail -> {
                    throw new StudentException(StudentResponseStatus.STUDENT_ALREADY_ENROLLED);
                });



        // 가입 신청하면 enabled 1 처리와 기수 등록되야함
        Course course = courseRepository.findById(courseIdx).orElseThrow();

        StudentDetail studentDetail = result.get();

        studentRepository.save(ApplyBootcampRequestDto.toEntity(studentDetail ,course));


    }
}
