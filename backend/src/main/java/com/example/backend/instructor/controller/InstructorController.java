package com.example.backend.instructor.controller;


import com.example.backend.global.response.BaseResponse;
import com.example.backend.global.response.BaseResponseServiceImpl;
import com.example.backend.global.response.responseStatus.InstructorResponseStatus;
import com.example.backend.instructor.model.dto.req.InstructorRequestDto;
import com.example.backend.instructor.model.dto.req.UpdateUserInstructorDto;
import com.example.backend.instructor.model.dto.res.InstructorResponseDto;
import com.example.backend.instructor.repository.InstructorRepository;
import com.example.backend.instructor.service.InstructorService;
import com.example.backend.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@Tag(name = "강사 기능", description = "강사 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/instructor")
public class InstructorController {

    private final BaseResponseServiceImpl baseResponseService;


    private final InstructorService instructorService;



    // Todo n+1 처리해야함
//    @Operation(summary = "강사 정보 조회", description = "자기 정보를 조회하는 기능입니다.")
//    @GetMapping("/edit")
//    public BaseResponse<InstructorResponseDto> getInstructor(@AuthenticationPrincipal User user) {
//        InstructorResponseDto response = instructorService.getInstructor(user.getIdx());
//        return baseResponseService.getSuccessResponse(response, InstructorResponseStatus.SUCCESS);
//    }

    //Todo n+1 처리해야함 그리고 강사 정보 업데이트 기능 이상
    @Operation(summary = "강사 정보 수정", description = "강사의 개인 정보를 수정하는 기능입니다.")
    @PostMapping("/edit")
    public BaseResponse setInfo(@RequestBody UpdateUserInstructorDto dto, @AuthenticationPrincipal User user) {
        instructorService.setInfo(dto, user);
        return baseResponseService.getSuccessResponse(InstructorResponseStatus.SUCCESS);
    }

//    // 모든 강사 정보 조회
//    @Operation(summary = "모든 강사 정보 조회", description = "모든 강사의 정보를 조회하는 기능입니다.")
//    @GetMapping("/instructors")
//    public BaseResponse<List<InstructorResponseDto>> getAllInstructorInfo() {
//        List<InstructorResponseDto> responseDtoList = instructorService.instructor_list2();
//        return baseResponseService.getSuccessResponse(responseDtoList, InstructorResponseStatus.SUCCESS);
//    }
//


}
