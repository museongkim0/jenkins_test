package com.example.backend.instructor.model.dto.req;

import com.example.backend.instructor.model.Course;
import com.example.backend.instructor.model.Curriculum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurriculumRegisterDto {

    @Schema(description = "커리큘럼 날짜", example = "2023-08-01")
    private LocalDate curriculumDate; //일자

    @Schema(description = "커리큘럼 일수", example = "1")
    private int curriculumDay; //일수

    @Schema(description = "커리큘럼 과목", example = "Java Programming")
    private String curriculumSubject; // 교과목

    @Schema(description = "커리큘럼 내용", example = "자바 프로그래밍 기초 수업")
    private String content; // 내용

    @Schema(description = "커리큘럼 편성 시간", example = "4")
    private int curriculumHours; // 편성시간

    public Curriculum toEntity(Course course) {
        return Curriculum.builder()
                .curriculumDate(curriculumDate)
                .curriculumDay(curriculumDay)
                .curriculumSubject(curriculumSubject)
                .curriculumHours(curriculumHours)
                .content(content)
                .course(course)
                .build();
    }
}
