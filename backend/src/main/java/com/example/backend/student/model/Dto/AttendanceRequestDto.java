package com.example.backend.student.model.Dto;

import com.example.backend.student.model.Attendance_Logs;
import com.example.backend.student.model.StudentDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


@Getter
@NoArgsConstructor
@AllArgsConstructor

public class AttendanceRequestDto {


    private String name;  // 사유 요약
    private LocalDate startDate;  // 시작 날짜
    private LocalDate endDate;  // 종료 날짜
    private String logType;  // String 타입으로 그대로 사용
    private String description;
    private StudentDetail studentdetail; // 사유
    private LocalDate createdAt;

    public Attendance_Logs from(StudentDetail studentDetail) {
        return Attendance_Logs.builder()
                .name(name)
                .startDate(startDate)
                .endDate(endDate)
                .logType(logType)
                .description(description)
                .studentdetail(studentDetail)
                .createdAt(createdAt)
                .build();
    }





}
