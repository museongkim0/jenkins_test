package com.example.backend.comment.model.dto;

import com.example.backend.comment.model.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentUpdateRequest {
    @Schema(description = "수정된 댓글의 내용", example = "수정된 댓글 내용입니다.")
    private String content;
    public Comment toEntity() {
        return Comment.builder().content(content).build();
    }
}
