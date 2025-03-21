package com.example.backend.board.model.dto;

import com.example.backend.board.model.Board;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardUpdateResponseDto {
    @Schema(description = "수정된 게시글의 고유 아이디", example = "100")
    private Long idx;
    @Schema(description = "수정된 게시글의 제목", example = "수정된 게시글 제목입니다.")
    private String title;
    @Schema(description = "게시글이 수정된 날짜", example = "2025-03-05")
    private LocalDateTime modifiedDate;
    @Schema(description = "게시글 수정 중 추가된 이미지 url", example = "")
    private List<String> newFileUrls;

    public static BoardUpdateResponseDto from(Board board, List<String> newFileUrls) {
        return BoardUpdateResponseDto.builder().idx(board.getIdx()).title(board.getTitle()).modifiedDate(board.getModifiedDate()).newFileUrls(newFileUrls).build();
    }

}
