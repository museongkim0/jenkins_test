package com.example.backend.instructor.service;


import com.example.backend.global.exception.InstructorException;
import com.example.backend.global.response.responseStatus.InstructorResponseStatus;
import com.example.backend.instructor.model.Instructor;
import com.example.backend.instructor.model.dto.req.InstructorRequestDto;
import com.example.backend.instructor.model.dto.req.UpdateUserInstructorDto;
import com.example.backend.instructor.model.dto.res.InstructorResponseDto;
import com.example.backend.instructor.repository.InstructorRepository;
import com.example.backend.user.model.User;
import com.example.backend.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstructorService {

    private final InstructorRepository instructorRepository;
    private final UserService userService;


    //    //Todo 강사 조회
    public InstructorResponseDto getInstructor(Long userIdx) {
        try {
             Instructor instructor = instructorRepository.findByUserIdx(userIdx) // Instructor 조회
                    .orElseThrow(() -> new InstructorException(InstructorResponseStatus.INSTRUCTOR_NOT_FOUND));
            return InstructorResponseDto.from(instructor);
        } catch (InstructorException e) {
            // 예외 처리 로직: 강사를 찾을 수 없는 경우
            throw new InstructorException(InstructorResponseStatus.INSTRUCTOR_NOT_FOUND);
        } catch (Exception e) {
            // 그 외 예외 처리
            throw new InstructorException(InstructorResponseStatus.UNKNOWN_ERROR);
        }
    }

    // InstructorService.java
    public Instructor getInstructorByUserIdx(Long userIdx) {
        return instructorRepository.findDistinctInstructorByUserIdx(userIdx)
                .orElseThrow(() -> new InstructorException(InstructorResponseStatus.INSTRUCTOR_NOT_FOUND));
    }





    //Todo
    // 쿠키 idx 값이랑 강사 idx 값이랑 같아야함
     // 강사 정보 수정
    @Transactional
    public void setInfo(UpdateUserInstructorDto dto, User user) {

        Instructor instructor = instructorRepository.findByUserIdx(user.getIdx()) // Instructor 조회
                .orElseThrow(() -> new InstructorException(InstructorResponseStatus.INSTRUCTOR_NOT_FOUND));

        if (!instructor.getUser().getIdx().equals(user.getIdx())) {
            throw new InstructorException(InstructorResponseStatus.INVALID_INSTRUCTOR_ID);
        }
        userService.setInstructorInfo(instructor.getIdx(), dto);

    }


    @Transactional
    public void setInfo2(Long instructorIdx, UpdateUserInstructorDto dto) {
        try {
            userService.setInstructorInfo(instructorIdx, dto);
        } catch (Exception e) {
            throw new InstructorException(InstructorResponseStatus.UNKNOWN_ERROR);
        }
    }





    public List<InstructorResponseDto> instructor_list2() {
        try {
            // 'INSTRUCTOR' 역할을 가진 User를 가져오며, 그와 관련된 Instructor도 함께 로딩
            List<User> users = userService.findUsersByRoleWithInstructor();

            return users.stream()
                    .map(user -> {
                        // 'User' 객체에 이미 'Instructor'가 로딩되었으므로, 바로 접근
                        Instructor instructor = user.getInstructor();
                        return InstructorResponseDto.from(instructor != null ? instructor : new Instructor(user, "기본 기록", "기본 포트폴리오"));
                    })
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new InstructorException(InstructorResponseStatus.UNKNOWN_ERROR);
        }
    }


}







