package com.example.backend.student.service;

import com.example.backend.global.exception.StudentException;
import com.example.backend.global.response.responseStatus.StudentResponseStatus;
import com.example.backend.instructor.model.Course;
import com.example.backend.instructor.service.CourseService;
import com.example.backend.student.model.Dto.*;
import com.example.backend.student.model.StudentDetail;
import com.example.backend.student.repository.AttendanceRepository;
import com.example.backend.student.repository.StudentRepository;
import com.example.backend.user.model.Dto.UserResponseDto;
import com.example.backend.user.model.User;
import com.example.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;


    public StudentDetailResponseDto register(StudentDetailRegisterDto dto, User user) {
        if (dto == null) {
            throw new StudentException(StudentResponseStatus.STUDENT_NOT_FOUND);
        }

        try {
            StudentDetail studentDetail = studentRepository.save(dto.toEntity(user));
            return StudentDetailResponseDto.from(studentDetail);
        } catch (Exception e) {
            throw new StudentException(StudentResponseStatus.STUDENT_ENROLLMENT_FAILED);
        }
    }


    public StudentDetailResponseDto getStudentDetail(Long userIdx) {
        Optional<StudentDetail> rs = studentRepository.findByUserIdx(userIdx);
        if (rs.isPresent()) {
            return StudentDetailResponseDto.from(rs.get());
        }
        return null;

    }
    public StudentDetail  getStudent(Long userIdx) {
        Optional<StudentDetail> rs = studentRepository.findByUserIdx(userIdx);
        if (rs.isPresent()) {
            return rs.get();
        }
        return null;

    }

    public StudentResponseDto read(Long idx) {
        if (idx == null || idx < 0) {
            throw new StudentException(StudentResponseStatus.INVALID_STUDENT_ID);
        }

        StudentDetail student = studentRepository.findByStudent(idx).orElseThrow();
        if (student == null) {
            throw new StudentException(StudentResponseStatus.STUDENT_NOT_FOUND);
        }

        return StudentResponseDto.from(student);
    }

    public Object readAllInfo(Long userIdx) {
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new StudentException(StudentResponseStatus.STUDENT_NOT_FOUND));
        StudentDetail studentDetail = user.getStudentDetail();
        if (studentDetail == null) {
            return UserResponseDto.BasicUserResponseDto.from(user);
        }
        return StudentResponseDto.from(studentDetail);
    }



    public StudentDetailPageResponse list(int page, int size) {
        if (page < 0 || page+1 > studentRepository.count() || size < 0 || size > studentRepository.count()) {
            throw new StudentException(StudentResponseStatus.INVALID_PAGE);
        }
        Page<StudentDetail> studentList = studentRepository.findAllStudents(PageRequest.of(page, size));
        if (studentList.isEmpty()) {
            throw new StudentException(StudentResponseStatus.STUDENT_NOT_FOUND);
        }
        return StudentDetailPageResponse.from(studentList);
    }
    public StudentDetailResponseDto update(User user, String action) {
        if (user.getIdx() == null || user.getIdx() < 0) {
            throw new StudentException(StudentResponseStatus.INVALID_STUDENT_ID);
        }
        User findUser = userRepository.findById(user.getIdx()).orElseThrow();
        if (findUser.getStudentDetail().getIdx() == null || findUser.getStudentDetail().getIdx() < 0) {
            throw new StudentException(StudentResponseStatus.INVALID_STUDENT_ID);
        }
        StudentDetail studentDetail = studentRepository.findById(findUser.getStudentDetail().getIdx()).orElseThrow();

        if (action.equals("testStatus")) {
            studentDetail.updateTestStatus();
            StudentDetail studentDetailR = studentRepository.save(studentDetail);
            return StudentDetailResponseDto.from(studentDetailR);
        } else if (action.equals("perception")) {
            studentDetail.updatePerception();
            StudentDetail studentDetailR = studentRepository.save(studentDetail);
            return StudentDetailResponseDto.from(studentDetailR);
        } else if (action.equals("attendance")) {
            studentDetail.updateAttendance();
            StudentDetail studentDetailR = studentRepository.save(studentDetail);
            return StudentDetailResponseDto.from(studentDetailR);
        } else if (action.equals("leaveEarly")) {
            studentDetail.updateLeaveEarly();
            StudentDetail studentDetailR = studentRepository.save(studentDetail);
            return StudentDetailResponseDto.from(studentDetailR);
        } else if (action.equals("outing")) {
            studentDetail.updateOuting();
            StudentDetail studentDetailR = studentRepository.save(studentDetail);
            return StudentDetailResponseDto.from(studentDetailR);
        } else if (action.equals("vacationLeft")) {
            studentDetail.updateVacationLeft();
            StudentDetail studentDetailR = studentRepository.save(studentDetail);
            return StudentDetailResponseDto.from(studentDetailR);
        } else {
            throw new StudentException(StudentResponseStatus.INVALID_UPDATE_ACTION);
        }
    }

    public List<StudentDetailResponseDto> batchUpdate(List<Long> userIds, String action) {
        if (userIds == null || userIds.isEmpty()) {
            throw new StudentException(StudentResponseStatus.STUDENT_NOT_FOUND);
        }

        List<User> users = userRepository.findAllById(userIds);
        if (users.isEmpty()) {
            throw new StudentException(StudentResponseStatus.STUDENT_NOT_FOUND);
        }

        List<Long> studentDetailIds = users.stream()
                .filter(u -> u.getStudentDetail() != null)
                .map(u -> u.getStudentDetail().getIdx())
                .collect(Collectors.toList());

        List<StudentDetail> studentDetails = studentRepository.findAllById(studentDetailIds);
        if (studentDetails.isEmpty()) {
            throw new StudentException(StudentResponseStatus.STUDENT_NOT_FOUND);
        }

        Map<String, Consumer<StudentDetail>> updateActions = new HashMap<>();
        updateActions.put("testStatus", StudentDetail::updateTestStatus);
        updateActions.put("perception", StudentDetail::updatePerception);
        updateActions.put("attendance", StudentDetail::updateAttendance);
        updateActions.put("leaveEarly", StudentDetail::updateLeaveEarly);
        updateActions.put("outing", StudentDetail::updateOuting);
        updateActions.put("vacationLeft", StudentDetail::updateVacationLeft);

        Consumer<StudentDetail> updateFunction = updateActions.get(action);
        if (updateFunction == null) {
            throw new StudentException(StudentResponseStatus.INVALID_UPDATE_ACTION);
        }

        studentDetails.forEach(updateFunction);

        List<StudentDetail> updatedStudents = studentRepository.saveAll(studentDetails);

        return updatedStudents.stream()
                .map(StudentDetailResponseDto::from)
                .collect(Collectors.toList());
    }


    public void applyForLeave(AttendanceRequestDto dto , Long userIdx) {
        int vacationDays = (int) ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate()) + 1;

        Optional<StudentDetail> studentIdx = studentRepository.findByStudent(userIdx);

        if (studentIdx.isEmpty() || !isVacationValid(studentIdx.get(), vacationDays)) {
            throw new StudentException(StudentResponseStatus.huga_noting);
        }

        studentIdx.get().updataVacationLeft(vacationDays);

        attendanceRepository.save(dto.from(studentIdx.get()));
    }

    // 학생의 휴가 일수를 확인하는 메서드
    private boolean isVacationValid(StudentDetail studentDetail, int vacationDays) {
        return studentDetail.getVacationLeft() > vacationDays;
    }

    public StudentDetailResponseDto read1(Long idx) {
        if (idx == null || idx < 0) {
            throw new StudentException(StudentResponseStatus.INVALID_STUDENT_ID);
        }

        StudentDetail student = studentRepository.findByStudent(idx).orElseThrow();
        if (student == null) {
            throw new StudentException(StudentResponseStatus.STUDENT_NOT_FOUND);
        }

        return StudentDetailResponseDto.from(student);
    }


}
