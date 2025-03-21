package com.example.backend.board.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardUpdateRequestDto {
    @Schema(description = "게시글 수정 제목", example = "수정된 게시글의 제목입니다.")
    private String title;
    @Schema(description = "게시글의 수정 내용", example = "수정된 게시글의 내용입니다.")
    private String content;
    @Schema(description = "게시글의 추가 사진", example = "게시글에 수정중 추가되는 사진 이름")
    private List<String> addFiles;   // 새로 추가할 파일 리스트
    @Schema(description = "게시글의 삭제 사진", example = "게시글에 수정중 삭제되는 사진 이름")
    private List<String> deleteFiles; // 삭제할 파일 리스트
}
