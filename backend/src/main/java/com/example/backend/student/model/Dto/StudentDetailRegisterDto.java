package com.example.backend.student.model.Dto;

import com.example.backend.student.model.StudentDetail;
import com.example.backend.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudentDetailRegisterDto {
    @Schema(description = "학생 주소", required = false, example = "student_address")
    private String address;
    @Schema(description = "학생 시험 상태", required = true, example = "false")
    private Boolean testStatus;
    @Schema(description = "학생 지각 일수", required = true, example = "0")
    private Integer perception;
    @Schema(description = "학생 출석 일수", required = true, example = "0")
    private Integer attendance;
    @Schema(description = "학생 조퇴 일수", required = true, example = "0")
    private Integer leaveEarly;
    @Schema(description = "학생 외출 일수", required = true, example = "0")
    private Integer outing;
    @Schema(description = "학생 잔여 휴가 일수", required = true, example = "12")
    private Integer vacationLeft;

    public StudentDetail toEntity(User user) {
        return StudentDetail.builder()
                .address(address)
                .testStatus(testStatus)
                .perception(perception)
                .attendance(attendance)
                .leaveEarly(leaveEarly)
                .outing(outing)
                .vacationLeft(vacationLeft)
                .user(user)
                .build();
    }
}
