package com.example.backend.board.model.dto;

import com.example.backend.board.model.Board;
import com.example.backend.comment.model.Comment;
import com.example.backend.comment.model.dto.CommentReadResponseDto;
import com.example.backend.comment.model.dto.CommentResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardReadResponseDto {
    @Schema(description = "게시글의 제목", example = "게시글 제목 입니다.")
    private String title;
    @Schema(description = "게시글의 내용", example = "게시글 내용 입니다.")
    private String content;
    @Schema(description = "게시글의 작성자", example = "작성자01")
    private String writer;
    @Schema(description = "게시글이 작성된 시간", example = "2025-03-05")
    private LocalDateTime createdDate;
    @Schema(description = "게시글이 수정된 시간", example = "2025-03-06")
    private LocalDateTime modifiedDate;
    @Schema(description = "게시글에 첨부된 파일의 주소", example = "")
    private List<String> imageUrls;
    @Schema(description = "게시글에 작성된 댓글들", example = "")
    private List<CommentReadResponseDto> comments;

    public static BoardReadResponseDto from(Board board) {
        return BoardReadResponseDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getUser().getName()).comments(board.getComments().stream()
                        .map(comment -> CommentReadResponseDto.builder()
                                .idx(comment.getIdx())
                                .content(comment.getContent())
                                .createdDate(comment.getCreatedDate())
                                .modifiedDate(comment.getModifiedDate())
                                .writer(comment.getUser().getName())
                                .build())
                        .collect(Collectors.toList()))
                .imageUrls(
                        board.getImageList() == null ? List.of() : board.getImageList().stream()
                                .map(image -> image.getUrl()).toList()
                ).createdDate(board.getCreatedDate()).modifiedDate(board.getModifiedDate()).build();
    }

}
