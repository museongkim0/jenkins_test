package com.example.backend.instructor.model.dto.res;

import com.example.backend.instructor.model.Curriculum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurriculumResponseDto {

    private Long idx;
    private String content;
    private LocalDate curriculumDate;
    private int curriculumDay;
    private String curriculumSubject;
    private int curriculumHours;




    public static CurriculumResponseDto from(Curriculum curriculum) {
        return CurriculumResponseDto.builder()
                .idx(curriculum.getIdx())
                .content(curriculum.getContent())
                .curriculumDate(curriculum.getCurriculumDate())
                .curriculumDay(curriculum.getCurriculumDay())
                .curriculumSubject(curriculum.getCurriculumSubject())
                .curriculumHours(curriculum.getCurriculumHours())
                .build();
    }
}
