package com.example.backend.comment.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentReadResponseDto {
    @Schema(description = "댓글의 고유 아이디", example = "100")
    private Long idx;
    @Schema(description = "댓글의 내용", example = "댓글 내용 입니다.")
    private String content;
    @Schema(description = "댓글의 작성 시간", example = "2025-03-03")
    private LocalDateTime createdDate;
    @Schema(description = "댓글의 수정 시간", example = "2025-03-05")
    private LocalDateTime  modifiedDate;
    @Schema(description = "댓글의 작성자", example = "댓글작성자01")
    private String writer;
}
