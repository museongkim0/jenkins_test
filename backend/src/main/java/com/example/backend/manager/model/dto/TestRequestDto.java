package com.example.backend.manager.model.dto;

import com.example.backend.instructor.model.Course;
import com.example.backend.manager.model.Test;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class TestRequestDto {
    @Schema(description = "시험 제목", required = true, example = "test_title1")
    private String title;
    @Schema(description = "시험 내용", required = true, example = "test_content1")
    private String content;
    private Course course;

    public Test toEntity(){
        return Test.builder()
                .title(title)
                .content(content)
                .course(course)
                .build();
    }
}
