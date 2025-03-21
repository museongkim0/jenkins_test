package com.example.backend.manager.model.dto;

import com.example.backend.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ManagerResponseDto {
    @Schema(description = "매니저 고유 번호")
    private Long idx;
    @Schema(description = "매니저 이메일")
    private String email;
    @Schema(description = "매니저 이름")
    private String name;
    @Schema(description = "매니저 생년월일")
    private LocalDate birth;

    public static ManagerResponseDto of(User entity) {
        return ManagerResponseDto.builder()
                .idx(entity.getIdx())
                .email(entity.getEmail())
                .name(entity.getName())
                .birth(entity.getBirth())
                .build();
    }
}
