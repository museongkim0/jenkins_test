package com.example.backend.board.model.dto;

import com.example.backend.board.model.Board;
import com.example.backend.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequestDto {
    @Schema(description = "게시글의 제목", example = "자유게시판 제목 입니다.")
    private String title;
    @Schema(description = "게시글의 내용", example = "자유게시판 내용 입니다.")
    private String content;
    @Schema(description = "게시글 첨부파일", example = "")
    private List<String> files;

    public Board toEntity(User loginUser, int boardType) {
        return Board.builder()
                .title(title)
                .content(content)
                .user(loginUser)
                .createdDate(LocalDateTime.now())  // 현재 시간 설정
                .modifiedDate(null)  // 수정 시간은 null
                .boardType(boardType)
                .build();
    }
}
