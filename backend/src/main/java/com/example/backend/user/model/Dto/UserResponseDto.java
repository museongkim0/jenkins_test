package com.example.backend.user.model.Dto;

import com.example.backend.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class UserResponseDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class SignupResponse {
        private Long idx;
        private String email;

        public static SignupResponse from(User entity) {
            return new SignupResponse(entity.getIdx(), entity.getEmail());
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BasicUserResponseDto {
        @Schema(description = "유저 고유 번호")
        private Long idx;
        @Schema(description = "유저 이메일")
        private String email;
        @Schema(description = "유저 이름")
        private String name;
        @Schema(description = "유저 프로필 이미지")
        private String profileUrl;
        @Schema(description = "유저 생일")
        private LocalDate birth;
        @Schema(description = "유저 역할")
        private String role;

        public static BasicUserResponseDto from(User entity) {
            return BasicUserResponseDto.builder()
                    .idx(entity.getIdx())
                    .email(entity.getEmail())
                    .name(entity.getName())
                    .profileUrl(entity.getProfileUrl())
                    .birth(entity.getBirth())
                    .role(entity.getRole())
                    .build();
        }
    }
}
