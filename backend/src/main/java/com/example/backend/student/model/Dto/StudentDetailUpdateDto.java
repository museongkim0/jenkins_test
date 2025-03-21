package com.example.backend.student.model.Dto;

import com.example.backend.student.model.StudentDetail;
import com.example.backend.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDetailUpdateDto {
    @Schema(description = "학생 고유 번호")
    private Long idx;
    @Schema(description = "학생 주소")
    private String address;
    @Schema(description = "학생 시험 상태")
    private Boolean testStatus;
    @Schema(description = "학생 지각 일수")
    private Integer perception;
    @Schema(description = "학생 출석 일수")
    private Integer attendance;
    @Schema(description = "학생 조퇴 일수")
    private Integer leaveEarly;
    @Schema(description = "학생 외출 일수")
    private Integer outing;
    @Schema(description = "학생 잔여 휴가 일수")
    private Integer vacationLeft;

    public static StudentDetail toEntity(StudentDetail studentDetail, String action) {
        if (action.equals("testStatus")) {
            return StudentDetail.builder()
                    .idx(studentDetail.getIdx())
                    .address(studentDetail.getAddress())
                    .testStatus(studentDetail.getTestStatus())
                    .perception(studentDetail.getPerception())
                    .attendance(studentDetail.getAttendance())
                    .leaveEarly(studentDetail.getLeaveEarly())
                    .outing(studentDetail.getOuting())
                    .vacationLeft(studentDetail.getVacationLeft())
                    .user(studentDetail.getUser())
                    .build();
        } else if (action.equals("perception")) {
            return StudentDetail.builder()
                    .idx(studentDetail.getIdx())
                    .address(studentDetail.getAddress())
                    .testStatus(studentDetail.getTestStatus())
                    .perception(studentDetail.getPerception()+1)
                    .attendance(studentDetail.getAttendance())
                    .leaveEarly(studentDetail.getLeaveEarly())
                    .outing(studentDetail.getOuting())
                    .vacationLeft(studentDetail.getVacationLeft())
                    .user(studentDetail.getUser())
                    .build();
        } else if (action.equals("attendance")) {
            return StudentDetail.builder()
                    .idx(studentDetail.getIdx())
                    .address(studentDetail.getAddress())
                    .testStatus(studentDetail.getTestStatus())
                    .perception(studentDetail.getPerception())
                    .attendance(studentDetail.getAttendance()+1)
                    .leaveEarly(studentDetail.getLeaveEarly())
                    .outing(studentDetail.getOuting())
                    .vacationLeft(studentDetail.getVacationLeft())
                    .user(studentDetail.getUser())
                    .build();
        } else if (action.equals("leaveEarly")) {
            return StudentDetail.builder()
                    .idx(studentDetail.getIdx())
                    .address(studentDetail.getAddress())
                    .testStatus(studentDetail.getTestStatus())
                    .perception(studentDetail.getPerception())
                    .attendance(studentDetail.getAttendance())
                    .leaveEarly(studentDetail.getLeaveEarly()+1)
                    .outing(studentDetail.getOuting())
                    .vacationLeft(studentDetail.getVacationLeft())
                    .user(studentDetail.getUser())
                    .build();
        } else if (action.equals("outing")) {
            return StudentDetail.builder()
                    .idx(studentDetail.getIdx())
                    .address(studentDetail.getAddress())
                    .testStatus(studentDetail.getTestStatus())
                    .perception(studentDetail.getPerception())
                    .attendance(studentDetail.getAttendance())
                    .leaveEarly(studentDetail.getLeaveEarly())
                    .outing(studentDetail.getOuting()+1)
                    .vacationLeft(studentDetail.getVacationLeft())
                    .user(studentDetail.getUser())
                    .build();
        } else if (action.equals("vacationLeft")) {
            return StudentDetail.builder()
                    .idx(studentDetail.getIdx())
                    .address(studentDetail.getAddress())
                    .testStatus(studentDetail.getTestStatus())
                    .perception(studentDetail.getPerception())
                    .attendance(studentDetail.getAttendance())
                    .leaveEarly(studentDetail.getLeaveEarly())
                    .outing(studentDetail.getOuting())
                    .vacationLeft(studentDetail.getVacationLeft()-1)
                    .user(studentDetail.getUser())
                    .build();
        }
        return studentDetail;
    }
}
