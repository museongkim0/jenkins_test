package com.example.backend.instructor.model.dto.res;

import com.example.backend.instructor.model.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BootcampListResponseDto {


    private Long idx;
    private String name;
    private int generation;
    public static BootcampListResponseDto from(Course course) {
        return BootcampListResponseDto.builder()
                .idx(course.getIdx())
                .name(course.getName())
                .generation(course.getGeneration())
                .build();
    }
}
