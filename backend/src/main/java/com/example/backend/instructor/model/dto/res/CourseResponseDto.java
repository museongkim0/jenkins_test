package com.example.backend.instructor.model.dto.res;

import com.example.backend.instructor.model.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseResponseDto {

    private Long idx;
    private String name;
    private int generation;

    @Builder.Default
    List<CurriculumResponseDto> curriculumList = new ArrayList<>();

    public static CourseResponseDto from(Course course) {
        return CourseResponseDto.builder()
                .idx(course.getIdx())
                .name(course.getName())
                .generation(course.getGeneration())
                .curriculumList(course.getCurriculumList().stream().map(CurriculumResponseDto::from).collect(Collectors.toList()))
                .build();
    }

}
