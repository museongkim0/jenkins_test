package com.example.backend.comment.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDeleteResponse {
    @Schema(description = "삭제된 댓글의 고유 아이디", example = "100")
    private Long commentIdx;
    public static CommentDeleteResponse from(Long commentIdx) {
        return CommentDeleteResponse.builder().commentIdx(commentIdx).build();
    }
}
