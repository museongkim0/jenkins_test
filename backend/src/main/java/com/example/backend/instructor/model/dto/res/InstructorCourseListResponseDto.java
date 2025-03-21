package com.example.backend.instructor.model.dto.res;

import com.example.backend.instructor.model.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstructorCourseListResponseDto {

    private String name;
    private int generation;

    public static InstructorCourseListResponseDto from(Course course) {
        return InstructorCourseListResponseDto.builder()
                .name(course.getName())
                .generation(course.getGeneration())
                .build();
    }




}
