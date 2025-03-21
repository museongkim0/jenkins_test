package com.example.backend.student.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Builder
public class Attendance_Logs {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String name;  // 사유 요약
    private LocalDate startDate;  // 시작 날짜
    private LocalDate endDate;  // 종료 날짜

    private String logType;  // String 타입으로 변경 (휴가, 병가, 기타)

    private String description;  // 사유
    private LocalDate createdAt;





    @ManyToOne
    @JoinColumn(name = "studentdetail_idx")
    private StudentDetail studentdetail;





}
