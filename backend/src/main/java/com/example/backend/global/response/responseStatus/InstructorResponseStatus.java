package com.example.backend.global.response.responseStatus;

import lombok.Getter;

@Getter
public enum InstructorResponseStatus implements BaseResponseStatus {


    SUCCESS(true, 1800, "요청에 성공했습니다."),
    CREATED(true, 1801, "데이터가 성공적으로 생성되었습니다."),
    UPDATED(true, 1802, "데이터가 성공적으로 수정되었습니다."),
    DELETED(true, 1803, "데이터가 성공적으로 삭제되었습니다."),

    // 4000번대 - Instructor 관련 에러
    INSTRUCTOR_NOT_FOUND(false, 4001, "강사 정보를 찾을 수 없습니다."),
    DUPLICATE_INSTRUCTOR(false, 4002, "이미 존재하는 강사입니다."),
    INVALID_INSTRUCTOR_ID(false, 4003, "잘못된 강사 ID입니다."),
    INSTRUCTOR_ASSIGNMENT_FAILED(false, 4004, "강사 배정에 실패했습니다."),
    INSTRUCTOR_ALREADY_ASSIGNED(false, 4005, "강사가 이미 해당 강의에 배정되었습니다."),
    COURSE_NOT_FOUND(false, 4006, "강의 정보를 찾을 수 없습니다."),
    INSTRUCTOR_ACCESS_DENIED(false, 4007, "강사가 접근할 수 없는 리소스입니다."),
    INSTRUCTOR_CANNOT_GRADE(false, 4008, "강사가 해당 학생의 성적을 수정할 권한이 없습니다."),
    COURSE_CREATION_FAILED(false, 4009, "강의 개설에 실패했습니다."),
    CURRICULUM_NOT_FOUND(false, 4010, "해당 교과목 정보를 찾을 수 없습니다."),
    INSTRUCTOR_NOT_ASSIGNED(false, 4011, "강사가 해당 강의에 배정되지 않았습니다."),
    INVALID_COURSE_GENERATION(false, 4012, "잘못된 기수 정보입니다."),
    CURRICULUM_REGISTRATION_FAILED(false, 4013, "교과목 등록에 실패했습니다."),
    COURSE_UPDATE_FAILED(false, 4014, "강의 수정에 실패했습니다."),
    COURSE_DELETION_FAILED(false, 4015, "강의 삭제에 실패했습니다."),
    UNKNOWN_ERROR(false , 4016 ,"강사 조회 중 오류가 발생했습니다.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    InstructorResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean isSuccess() {
        return isSuccess;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
