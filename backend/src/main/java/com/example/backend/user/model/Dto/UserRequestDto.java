package com.example.backend.user.model.Dto;


import com.example.backend.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
@Schema(description = "회원가입 요청")
public class UserRequestDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class SignupRequest {
        @Schema(description = "사용자 이메일", example = "test@test.com")
        private String email;
        @Schema(description = "비밀번호", example = "qwer1234")
        private String password;
        @Schema(description = "사용자 이름", example = "test")
        private String name;
        @Schema(description = "사용자 생일", example = "2024-12-23")
        private LocalDate birth;
        private String role;

        public User toEntity(String encodedPassword, String role) {
            return User.builder()
                    .email(email)
                    .password(encodedPassword)
                    .name(name)
                    .birth(birth)
                    .role(role)
                    .build();
        }


    }
}
