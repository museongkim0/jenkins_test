package com.example.backend.instructor.model.dto.req;

import com.example.backend.instructor.model.Instructor;
import com.example.backend.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateUserInstructorDto {

    @Schema(description = "사용자 비밀번호", example = "newPassword123")
    private String password;  // 비밀번호

    @Schema(description = "강사 경력", example = "5년 이상의 자바 개발 경력")
    private String record;  // 강사 경력

    @Schema(description = "강사 포트폴리오 링크", example = "https://github.com/username")
    private String portfolio;  // 강사 포트폴리오 링크

    public User toInstructorEntity(Long userIdx) {
        // User 객체 생성
        User user = User.builder()
                .idx(userIdx)
                .password(password)
                .build();

        // Instructor 객체 생성
        Instructor instructor = Instructor.builder()
                .record(record)
                .portfolio(portfolio)
                .user(user)  // User와 연결
                .build();

        // User 객체에 Instructor 정보 할당
        user.assignInstructor(instructor);

        return user;  // 업데이트된 User 객체 반환
    }
}
