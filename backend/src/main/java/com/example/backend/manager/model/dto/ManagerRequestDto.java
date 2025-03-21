package com.example.backend.manager.model.dto;

import com.example.backend.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ManagerRequestDto {
    @Schema(description = "매니저 이메일", required = true, example = "manager@test.com")
    private String email;
    @Schema(description = "매니저 패스워드", required = true, example = "qwer1234")
    private String password;
    @Schema(description = "매니저 별명", required = true, example = "Eric")
    private String nickname;

    public User toEntity(String encodedPassword) {
        return User.builder()
                .email(email)
                .password(encodedPassword)
                .build();
    }
}
