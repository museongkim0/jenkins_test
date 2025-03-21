package com.example.backend.comment.model;

import com.example.backend.board.model.Board;
import com.example.backend.comment.model.dto.CommentUpdateRequest;
import com.example.backend.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "댓글 정보를 담는 객체")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Schema(description = "댓글의 고유 아이디", example = "100")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @Schema(description = "댓글의 내용", example = "안녕하세요. 반갑습니다.")
    private String content;
    @Schema(description = "댓글 작성일자", example = "2025-03-02")
    private LocalDateTime createdDate;
    @Schema(description = "댓글 수정일자", example = "2025-03-03")
    private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(name = "board_idx")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User user;

    public void updateContent(String content) {
        this.content = content;
        this.modifiedDate = LocalDateTime.now();
    }
}
