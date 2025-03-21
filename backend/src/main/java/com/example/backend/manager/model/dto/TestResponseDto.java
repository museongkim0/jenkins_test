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
public class TestResponseDto {
    @Schema(description = "시험 고유 번호")
    private Long idx;
    @Schema(description = "시험 제목")
    private String title;
    @Schema(description = "시험 내용")
    private String content;

    public static TestResponseDto of(Test entity) {
        return TestResponseDto.builder()
                .idx(entity.getIdx())
                .title(entity.getTitle())
                .content(entity.getContent())
                .build();
    }
}
