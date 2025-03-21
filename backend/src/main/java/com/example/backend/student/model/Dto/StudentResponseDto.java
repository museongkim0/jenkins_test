package com.example.backend.student.model.Dto;

import com.example.backend.student.model.StudentDetail;
import com.example.backend.user.model.User;
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
public class StudentResponseDto {
    @Schema(description = "유저 고유 번호")
    private Long idx;
    @Schema(description = "유저 이메일")
    private String email;
    @Schema(description = "유저 비밀번호")
    private String password;
    @Schema(description = "유저 이름")
    private String name;
    @Schema(description = "유저 역할")
    private String role;
    @Schema(description = "유저 역할")
    private LocalDate birth;



    @Schema(description = "학생 상세 정보")
    StudentDetailResponseDto studentDetail;

    public static StudentResponseDto from(StudentDetail s) {
        User user = s.getUser();
        return StudentResponseDto.builder()
                .idx(user.getIdx())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .role(user.getRole())
                .birth(user.getBirth())
                .studentDetail(StudentDetailResponseDto.from(user.getStudentDetail()))
                .build();
    }
}
