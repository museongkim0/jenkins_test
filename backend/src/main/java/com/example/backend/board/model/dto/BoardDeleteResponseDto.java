package com.example.backend.board.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BoardDeleteResponseDto {
    @Schema(description = "삭제된 게시글의 번호", example = "100")
    private Long boardIdx;
    public static BoardDeleteResponseDto from(Long boardIdx) {
        return BoardDeleteResponseDto.builder().boardIdx(boardIdx).build();
    }
}
