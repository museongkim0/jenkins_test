package com.example.backend.comment.model.dto;

import com.example.backend.comment.model.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponseDto {
    @Schema(description = "댓글 작성자", example = "댓글작성자01")
    private String writer;


    public static CommentResponseDto from(Comment comment){
        return CommentResponseDto.builder().writer(comment.getUser().getName()).build();
    }
}
