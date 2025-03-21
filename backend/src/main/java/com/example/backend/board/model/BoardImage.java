package com.example.backend.board.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class BoardImage {
    @Schema(description = "게시글 첨부 사진의 고유 아이디", example = "100")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Schema(description = "게시글의 사진이 저장된 preSignedUrl")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_idx")
    @Schema(description = "사진이 첨부된 게시글의 정보", example = "100")
    private Board board;
}
