package com.example.backend.instructor.controller;

import com.example.backend.global.response.BaseResponse;
import com.example.backend.global.response.BaseResponseServiceImpl;
import com.example.backend.global.response.responseStatus.InstructorResponseStatus;
import com.example.backend.instructor.model.dto.req.CourseRegister;
import com.example.backend.instructor.model.dto.res.BootcampListResponseDto;
import com.example.backend.instructor.model.dto.res.CurriculumResponseDto;
import com.example.backend.instructor.model.dto.res.InstructorCourseListResponseDto;
import com.example.backend.instructor.model.dto.res.CourseResponseDto;
import com.example.backend.instructor.service.CourseService;
import com.example.backend.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "코스 기능", description = "코스 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {

    private final BaseResponseServiceImpl baseResponseService;

    private final CourseService courseService;




    //Todo n+1
    @Operation(summary = "코스 정보 등록", description = "강사가 새로운 코스를 등록하는 기능입니다.")
    @PostMapping("/register")
    public BaseResponse register(@AuthenticationPrincipal User user, @RequestBody CourseRegister dto) {
        courseService.register(dto, user);
        return baseResponseService.getSuccessResponse(InstructorResponseStatus.SUCCESS);
    }



    //Todo n+1
    @Operation(summary = "강사가 맡았던 기수 조회", description = "특정 강사가 맡았던 기수들의 코스를 조회하는 기능입니다.")
    @GetMapping("/instructor/{userIdx}")
    public BaseResponse<List<InstructorCourseListResponseDto>> getInstructorCourse(@PathVariable Long userIdx) {
        List<InstructorCourseListResponseDto> response = courseService.findIstructorCourse(userIdx);
        return baseResponseService.getSuccessResponse(response, InstructorResponseStatus.SUCCESS);
    }


    //Todo n+1
    @Operation(summary = "교과목 별 커리큘럼 조회", description = "특정 교과목에 대한 커리큘럼 정보를 조회하는 기능입니다.")
    @GetMapping("/curriculum")
    public BaseResponse<List<CurriculumResponseDto>> getCurriculumBySubject(@AuthenticationPrincipal User user , @RequestParam String subject) {

        List<CurriculumResponseDto> response = courseService.getCurriculumBySubject(subject , user);
        return baseResponseService.getSuccessResponse(response, InstructorResponseStatus.SUCCESS);
    }

    @Operation(summary = "교과목 별 커리큘럼 조회(복구 버전)", description = "특정 교과목에 대한 커리큘럼 정보를 조회하는 기능입니다.")
    @GetMapping("/curriculumRe")
    public  BaseResponse<List<CurriculumResponseDto>>getCurriculumBySubjectRe(@RequestParam String subject) {


        List<CurriculumResponseDto> response = courseService.getCurriculumBySubjectRe(subject);

        return baseResponseService.getSuccessResponse(response, InstructorResponseStatus.SUCCESS);
    }



    //Todo n+1
    @Operation(summary = "기수 별 코스 조회", description = "특정 기수에 대한 코스 정보를 조회하는 기능입니다.")
    @GetMapping("/bootcampinfo")
    public BaseResponse<CourseResponseDto> read(@AuthenticationPrincipal User user) {
        CourseResponseDto response = courseService.read(user);
        return baseResponseService.getSuccessResponse(response, InstructorResponseStatus.SUCCESS);
    }

    @Operation(summary = "기수 입력 받아 코스 조회", description = "특정 기수 아이디를 받아 코스 정보를 조회하는 기능입니다.")
    @GetMapping("/{generation}")
    public BaseResponse<CourseResponseDto> readGenCourse(@PathVariable int generation) {
        CourseResponseDto response = courseService.readGenCourse(generation);
        return baseResponseService.getSuccessResponse(response, InstructorResponseStatus.SUCCESS);
    }

    @GetMapping("/bootcamp")
    public BaseResponse<List<BootcampListResponseDto>> getAllCourses() {

        List<BootcampListResponseDto> rs =  courseService.getBootcampCourseAll();


        // Todo 나중에 baseRes 확인
        return baseResponseService.getSuccessResponse(rs , InstructorResponseStatus.SUCCESS);

    }


    @GetMapping("/applyBootcamp/{courseIdx}")
    public String applyBootcamp(@PathVariable Long courseIdx , @AuthenticationPrincipal User user) {
        courseService.applyBootcamp(courseIdx,user);

        return "가입됌";

    }




}
