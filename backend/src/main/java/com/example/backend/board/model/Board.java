package com.example.backend.board.model;

import com.example.backend.comment.model.Comment;
import com.example.backend.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "게시글 정보를 담는 객체")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {
    @Schema(description = "게시글의 고유 아이디", example = "100")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @Schema(description = "게시글의 제목", example = "Spring 관련 질문")
    private String title;
    @Schema(description = "게시글의 내용", example = "Spring에서 Bean과 Component의 차이가 뭔가요?")
    private String content;
    @Schema(description = "게시글의 작성 시간", example = "2025-03-05")
    private LocalDateTime createdDate;
    @Schema(description = "게시글의 수정 시간", example = "2025-03-06")
    private LocalDateTime modifiedDate;
    // 0 = 공지 게시판 / 1 = 일반 게시판 / 2 = 시험 / 3 = 자료실 / 4 = 과제 / 5 = 질의 응답
    @Schema(description = "게시글의 카테고리", example = "1")
    private int boardType;

    @Schema(description = "댓글 리스트")
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @BatchSize(size = 10)  // 한 번에 최대 10개의 댓글 조회
    private List<Comment> comments = new ArrayList<>();

    @Schema(description = "게시글 작성자의 정보")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @BatchSize(size = 10)  // 한 번에 최대 10개의 이미지 조회
    private List<BoardImage> imageList = new ArrayList<>();

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.modifiedDate = LocalDateTime.now();
    }
}



