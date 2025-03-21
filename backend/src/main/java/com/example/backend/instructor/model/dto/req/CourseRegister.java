package com.example.backend.instructor.model.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import com.example.backend.instructor.model.Course;
import com.example.backend.instructor.model.Instructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseRegister {

    @Schema(description = "코스 이름", example = "Java Bootcamp")
    private String name; // 코스 이름

    @Schema(description = "기수", example = "1")
    private int generation; // 기수

//    @Schema(description = "커리큘럼 목록", example = "[{\"subject\": \"Java\", \"hours\": 40}, {\"subject\": \"Spring Framework\", \"hours\": 50}]")
    List<CurriculumRegisterDto> curriculumList = new ArrayList<>(); // 커리큘럼 목록

    public Course toEntity(Instructor instructorIdx) {
        return Course.builder()
                .name(name)
                .generation(generation)
                .instructor(instructorIdx)
                .build();
    }

}
