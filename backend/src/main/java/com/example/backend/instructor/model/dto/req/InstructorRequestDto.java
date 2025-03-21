package com.example.backend.instructor.model.dto.req;

import com.example.backend.instructor.model.Instructor;
import com.example.backend.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstructorRequestDto {

    @Schema(description = "강사 경력", example = "5년 이상의 자바 개발 경력")
    private String record;

    @Schema(description = "강사 포트폴리오 링크", example = "https://github.com/username")
    private String portfolio;

    public Instructor toEntity(User user) {
        return Instructor.builder()
                .record(record)
                .portfolio(portfolio)
                .user(user)
                .build();
    }
}
