package com.example.backend.manager.controller;

import com.example.backend.global.response.BaseResponse;
import com.example.backend.global.response.BaseResponseServiceImpl;
import com.example.backend.global.response.responseStatus.CommonResponseStatus;
import com.example.backend.instructor.model.dto.res.InstructorPageResponse;
import com.example.backend.instructor.model.dto.res.InstructorResponseDto;
import com.example.backend.manager.model.dto.*;
import com.example.backend.manager.service.ManagerService;
import com.example.backend.user.model.Dto.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manager")
@Tag(name = "매니저 기능", description = "매니저 관리 API")
public class ManagerController {
    private final ManagerService managerService;
    private final BaseResponseServiceImpl baseResponseService;

    @Operation(
            summary = "매니저 목록을 가져온다",
            description = "매니저의 정보와 그 목록들을 가져오는 요청."
    )
    @GetMapping("/list")
    public BaseResponse<ManagerPageResponse> managerList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return managerService.getManagerList(page, size);
    }

    @Operation(
            summary = "매니저 정보를 가져온다",
            description = "매니저의 유저 idx를 기반으로 매니저의 정보를 가져오는 요청."
    )
    @GetMapping("/{managerIdx}")
    public BaseResponse<ManagerResponseDto> findManager(@PathVariable Long managerIdx) {
        return managerService.getManager(managerIdx);
    }

    @Operation(
            summary = "강사 목록을 가져온다",
            description = "강사의 정보와 그 목록들을 가져오는 요청."
    )
    @GetMapping("/instructor/list")
    public BaseResponse<InstructorPageResponse> instructorList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return managerService.getInstructorList(page, size);
    }

    @Operation(
            summary = "시험을 등록한다",
            description = "시험의 제목, 내용, 과목을 받아 시험을 등록한다"
    )
    @PostMapping("/test/register")
    public BaseResponse<TestResponseDto> registerTest(@RequestBody TestRequestDto dto) {
        return managerService.registerTest(dto);
    }

    @Operation(
            summary = "시험을 수정한다",
            description = "시험의 제목, 내용, 과목을 받아 시험을 수정한다"
    )
    @PostMapping("/test/update/{testIdx}")
    public BaseResponse<TestResponseDto> updateTest(@PathVariable Long testIdx, @RequestBody TestRequestDto dto) {
        return managerService.updateTest(testIdx, dto);
    }

    @Operation(
            summary = "시험을 삭제한다",
            description = "시험의 idx를 받아 시험을 삭제한다"
    )
    @PostMapping("/test/delete/{testIdx}")
    public BaseResponse<TestResponseDto> deleteTest(@PathVariable Long testIdx) {
        return managerService.deleteTest(testIdx);
    }

    @Operation(
            summary = "시험 목록을 가져온다",
            description = "시험의 제목, 내용, 과목을 가져온다"
    )
    @GetMapping("/test/list")
    public BaseResponse<TestPageResponse> testList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return managerService.getTestList(page, size);
    }
}
