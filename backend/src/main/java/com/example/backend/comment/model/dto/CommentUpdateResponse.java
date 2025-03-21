package com.example.backend.comment.model.dto;

import com.example.backend.comment.model.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentUpdateResponse {
    @Schema(description = "수정된 댓글의 번호", example = "100")
    private Long commentIdx;
    @Schema(description = "댓글이 수정된 시간", example = "100")
    private LocalDateTime modifiedDate;
    public static CommentUpdateResponse from(Comment comment) {
        return CommentUpdateResponse.builder().commentIdx(comment.getIdx()).modifiedDate(comment.getModifiedDate()).build();
    }
}
