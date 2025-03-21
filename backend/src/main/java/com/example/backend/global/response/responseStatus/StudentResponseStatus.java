package com.example.backend.global.response.responseStatus;

import lombok.Getter;

@Getter
public enum StudentResponseStatus implements BaseResponseStatus {

    // 3000번대 - Student 관련 에러
    STUDENT_NOT_FOUND(false, 3001, "학생 정보를 찾을 수 없습니다."),
    DUPLICATE_STUDENT(false, 3002, "이미 존재하는 학생입니다."),
    INVALID_STUDENT_ID(false, 3003, "잘못된 학생 ID입니다."),
    STUDENT_ENROLLMENT_FAILED(false, 3004, "학생 등록에 실패했습니다."),
    STUDENT_ALREADY_ENROLLED(false, 3005, "이미 해당 강의에 등록된 학생입니다."),
    COURSE_LIMIT_EXCEEDED(false, 3006, "학생이 신청할 수 있는 최대 강의 수를 초과했습니다."),
    STUDENT_ACCESS_DENIED(false, 3007, "학생이 접근할 수 없는 리소스입니다."),
    STUDENT_PROGRESS_NOT_FOUND(false, 3008, "학생의 학습 진행 정보를 찾을 수 없습니다."),
    STUDENT_GRADE_NOT_FOUND(false, 3009, "학생의 성적 정보를 찾을 수 없습니다."),
    INVALID_PAGE(false, 3010, "잘못된 페이지 요청입니다. page, size를 확인해주세요."),
    huga_noting(false, 3011, "남은 휴가가 없습니다."),
    INVALID_UPDATE_ACTION(false, 3012, "잘못된 업데이트 요청입니다. action 값을 확인해주세요.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    StudentResponseStatus(boolean isSuccess, int code, String message) {
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
