package com.example.backend.manager.service;

import com.example.backend.global.exception.ManagerException;
import com.example.backend.global.response.BaseResponse;
import com.example.backend.global.response.responseStatus.CommonResponseStatus;
import com.example.backend.global.response.responseStatus.ManagerResponseStatus;
import com.example.backend.instructor.model.Instructor;
import com.example.backend.instructor.model.dto.res.InstructorPageResponse;
import com.example.backend.instructor.model.dto.res.InstructorResponseDto;
import com.example.backend.instructor.repository.InstructorRepository;
import com.example.backend.manager.model.Test;
import com.example.backend.manager.model.dto.*;
import com.example.backend.manager.repository.ManagerRepository;
import com.example.backend.manager.repository.TestRepository;
import com.example.backend.student.model.Dto.StudentDetailPageResponse;
import com.example.backend.student.model.Dto.StudentDetailResponseDto;
import com.example.backend.student.model.StudentDetail;
import com.example.backend.student.repository.StudentRepository;
import com.example.backend.user.model.Dto.UserResponseDto;
import com.example.backend.user.model.User;
import com.example.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final ManagerRepository managerRepository;
    private final TestRepository testRepository;

    @Transactional(readOnly = true)
    public BaseResponse<ManagerPageResponse> getManagerList(int page, int size) {
        Page<User> managerPage = managerRepository.findByRole("MANAGER", PageRequest.of(page, size));
        if (managerPage.isEmpty()) {
            throw new ManagerException(ManagerResponseStatus.MANAGER_NOT_FOUND);
        }
        ManagerPageResponse response = ManagerPageResponse.from(managerPage);
        return new BaseResponse<>(true, CommonResponseStatus.SUCCESS.getMessage(),
                CommonResponseStatus.SUCCESS.getCode(), response);
    }

    @Transactional(readOnly = true)
    public BaseResponse<ManagerResponseDto> getManager(Long managerIdx) {
        User manager = managerRepository.findById(managerIdx)
                .orElseThrow(() -> new ManagerException(ManagerResponseStatus.MANAGER_NOT_FOUND));
        return new BaseResponse<>(true, CommonResponseStatus.SUCCESS.getMessage(),
                CommonResponseStatus.SUCCESS.getCode(), ManagerResponseDto.of(manager));
    }

    @Transactional(readOnly = true)
    public BaseResponse<UserResponseDto.SignupResponse> getUser(Long userIdx) {
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new ManagerException(ManagerResponseStatus.MANAGER_NOT_FOUND));
        return new BaseResponse<>(true, CommonResponseStatus.SUCCESS.getMessage(),
                CommonResponseStatus.SUCCESS.getCode(), UserResponseDto.SignupResponse.from(user));
    }

//    @Transactional(readOnly = true)
//    public BaseResponse<InstructorResponseDto> getInstructorByEmail(String instructorEmail) {
//        // Assuming you fixed the repository method to find by userEmail
//        Instructor instructor = instructorRepository.findByUserEmail(instructorEmail)
//                .orElseThrow(() -> new ManagerException(ManagerResponseStatus.MANAGER_NOT_FOUND));
//        return new BaseResponse<>(true, CommonResponseStatus.SUCCESS.getMessage(),
//                CommonResponseStatus.SUCCESS.getCode(), InstructorResponseDto.from(instructor));
//    }

    @Transactional(readOnly = true)
    public BaseResponse<InstructorPageResponse> getInstructorList(int page, int size) {
        Page<Instructor> instructorPage = instructorRepository.findAll(PageRequest.of(page, size));
        if (instructorPage.isEmpty()) {
            throw new ManagerException(ManagerResponseStatus.MANAGER_NOT_FOUND);
        }
        InstructorPageResponse response = InstructorPageResponse.from(instructorPage);
        return new BaseResponse<>(true, CommonResponseStatus.SUCCESS.getMessage(),
                CommonResponseStatus.SUCCESS.getCode(), response);
    }

    @Transactional(readOnly = true)
    public BaseResponse<StudentDetailPageResponse> getStudentList(int page, int size) {
        Page<StudentDetail> studentList = studentRepository.findAllStudents(PageRequest.of(page, size));
        if (studentList.isEmpty()) {
            throw new ManagerException(ManagerResponseStatus.MANAGER_NOT_FOUND);
        }

        return new BaseResponse<>(true, CommonResponseStatus.SUCCESS.getMessage(),
                CommonResponseStatus.SUCCESS.getCode(), StudentDetailPageResponse.from(studentList));
    }

    @Transactional
    public BaseResponse<TestResponseDto> registerTest(TestRequestDto dto) {
        Test test = testRepository.save(dto.toEntity());
        return new BaseResponse<>(true, "테스트 등록 성공",
                CommonResponseStatus.SUCCESS.getCode(), TestResponseDto.of(test));
    }

    @Transactional
    public BaseResponse<TestResponseDto> updateTest(Long testIdx, TestRequestDto dto) {
        Test test = testRepository.findById(testIdx)
                .orElseThrow(() -> new ManagerException(ManagerResponseStatus.MANAGER_NOT_FOUND));
        test.setTitle(dto.getTitle());
        test.setContent(dto.getContent());
        test.setCourse(dto.getCourse());
        testRepository.save(test);
        return new BaseResponse<>(true, "테스트 수정 성공",
                CommonResponseStatus.SUCCESS.getCode(), TestResponseDto.of(test));
    }

    @Transactional
    public BaseResponse<TestResponseDto> deleteTest(Long testIdx) {
        Test test = testRepository.findById(testIdx)
                .orElseThrow(() -> new ManagerException(ManagerResponseStatus.MANAGER_NOT_FOUND));
        testRepository.delete(test);
        return new BaseResponse<>(true, "테스트 삭제 성공",
                CommonResponseStatus.SUCCESS.getCode(), TestResponseDto.of(test));
    }

    @Transactional(readOnly = true)
    public BaseResponse<TestPageResponse> getTestList(int page, int size) {
        Page<Test> testPage = testRepository.findAllWithCourse(PageRequest.of(page, size));
        if (testPage.isEmpty()) {
            throw new ManagerException(ManagerResponseStatus.MANAGER_NOT_FOUND);
        }
        TestPageResponse response = TestPageResponse.from(testPage);
        return new BaseResponse<>(true, CommonResponseStatus.SUCCESS.getMessage(),
                CommonResponseStatus.SUCCESS.getCode(), response);
    }

}
