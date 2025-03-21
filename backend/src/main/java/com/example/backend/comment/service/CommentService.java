package com.example.backend.comment.service;

import com.example.backend.board.model.Board;
import com.example.backend.board.repository.BoardRepository;
import com.example.backend.comment.model.Comment;
import com.example.backend.comment.model.dto.*;
import com.example.backend.comment.repository.CommentRepository;
import com.example.backend.global.exception.CommentException;
import com.example.backend.global.response.BaseResponseService;
import com.example.backend.global.response.responseStatus.CommentResponseStatus;
import com.example.backend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final BaseResponseService baseResponseService;

    public CommentResponseDto register(User loginUser, Long boardIdx, CommentRequestDto dto) {
        Board board = boardRepository.findById(boardIdx)
                .orElseThrow(() -> new CommentException(CommentResponseStatus.INVALID_BOARD_ID)); // 예외 처리 추가
        try {
            Comment comment = commentRepository.save(dto.toEntity(loginUser, board));
            return CommentResponseDto.from(comment);
        } catch (Exception e) {
            throw new CommentException(CommentResponseStatus.COMMENT_CREATION_FAILED);
        }

    }

    public CommentDeleteResponse delete(User loginUser, Long commentIdx) {
        Comment comment = commentRepository.findById(commentIdx)
                .orElseThrow(() -> new CommentException(CommentResponseStatus.INVALID_COMMENT_ID));
        if(!comment.getUser().getIdx().equals(loginUser.getIdx())){
            throw new CommentException(CommentResponseStatus.COMMENT_ACCESS_DENIED);
        }
        commentRepository.delete(comment);

        return CommentDeleteResponse.from(comment.getIdx());
    }

    public CommentUpdateResponse update(User loginUser, Long commentIdx, CommentUpdateRequest dto) {
        Comment comment = commentRepository.findById(commentIdx)
                .orElseThrow(() -> new CommentException(CommentResponseStatus.INVALID_COMMENT_ID));


        if (!comment.getUser().getIdx().equals(loginUser.getIdx())) {
            throw new CommentException(CommentResponseStatus.COMMENT_ACCESS_DENIED);
        }

        comment.updateContent(dto.getContent());

        Comment updatedComment = commentRepository.save(comment);

        return CommentUpdateResponse.from(updatedComment);
    }
}
