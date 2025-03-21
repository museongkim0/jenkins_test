package com.example.backend.board.controller;

import com.example.backend.board.model.dto.*;
import com.example.backend.board.service.BoardService;
import com.example.backend.global.response.BaseResponse;
import com.example.backend.global.response.BaseResponseServiceImpl;
import com.example.backend.global.response.responseStatus.CommonResponseStatus;
import com.example.backend.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@Tag(name = "게시판 기능", description = "게시판 관리 API")
public class BoardController {
    private final BoardService boardService;
    private final BaseResponseServiceImpl baseResponseService;

    @Operation(
            summary = "게시글 등록",
            description = "boardType을 통해 어떤 게시판에 글을 작성할지 지정한 뒤, 제목, 내용, 첨부파일과 함께 글을 작성합니다."
    )
    @PostMapping("/register/{boardType}")
    public BaseResponse<Object> register(@AuthenticationPrincipal User loginUser, @RequestBody BoardRequestDto boardRequestDto, @PathVariable("boardType") int boardType) {
        BoardResponseDto response = boardService.register(loginUser, boardRequestDto, boardType);
        return baseResponseService.getSuccessResponse(response, CommonResponseStatus.CREATED);
    }

    @Operation(
            summary = "게시글 상세보기",
            description = "boardIdx를 전달받아 게시글 하나의 정보를 확인합니다."
    )
    @GetMapping("/read/{boardIdx}")
    public BaseResponse<Object> read(@PathVariable @Param("boardIdx") Long boardIdx) {
        BoardReadResponseDto response = boardService.read(boardIdx);
        return baseResponseService.getSuccessResponse(response, CommonResponseStatus.SUCCESS);
    }

    @Operation(
            summary = "게시글 리스트보기",
            description = "boardIdx를 전달받아 게시글 하나의 정보를 확인합니다."
    )
    @GetMapping("/list/{boardType}")
    public BaseResponse<Object> getBoardList(@PathVariable int boardType,int page, int size) {
        BoardPageResponse response = boardService.getBoardList(boardType, page, size);
        return baseResponseService.getSuccessResponse(response, CommonResponseStatus.SUCCESS);
    }

    @Operation(
            summary = "로그인한 사용자의 게시판 리스트 보기",
            description = "로그인 사용자 정보와 boardType 전달받아 로그인한 사용자의 게시물을 확인합니다."
    )
    @GetMapping("/listByUser/{boardType}")
    public BaseResponse<Object> getByLoginUserBoardList(@AuthenticationPrincipal User loginUser, @PathVariable int boardType, int page, int size) {
        BoardPageResponse response = boardService.getByLoginUserBoardList(loginUser, boardType, page, size);
        return baseResponseService.getSuccessResponse(response, CommonResponseStatus.SUCCESS);
    }

    @Operation(
            summary = "사용자별 게시판 리스트보기",
            description = "특정 사용자 idx와 boardType 전달받아 해당 사용자의 게시물을 확인합니다."
    )
    @GetMapping("/listByUserIdx/{boardType}")
    public BaseResponse<Object> getByUserBoardList(@PathVariable int boardType, Long userIdx, int page, int size) {
        BoardPageResponse response = boardService.getByUserBoardList(boardType, userIdx, page, size);
        return baseResponseService.getSuccessResponse(response, CommonResponseStatus.SUCCESS);
    }

    @Operation(
            summary = "게시글 삭제하기",
            description = "boardIdx를 전달받아 본인이 작성한 글인지 확인 후, 게시글 하나를 삭제합니다."
    )
    @DeleteMapping("/delete/{boardIdx}")
    public BaseResponse<Object> delete(@AuthenticationPrincipal User loginUser, @PathVariable Long boardIdx) {
        BoardDeleteResponseDto response = boardService.deleteBoard(loginUser, boardIdx);
        return baseResponseService.getSuccessResponse(response, CommonResponseStatus.DELETED);
    }

    @Operation(
            summary = "게시글 수정하기",
            description = "boardIdx를 전달받아 본인이 작성한글인지 확인 후, 게시글의 제목과 내용, 첨부파일을 수정합니다."
    )
    @PatchMapping("/update/{boardIdx}")
    public BaseResponse<Object> update(@AuthenticationPrincipal User loginUser, @PathVariable Long boardIdx, @RequestBody BoardUpdateRequestDto boardUpdateRequestDto) {
        BoardUpdateResponseDto response = boardService.updateBoard(loginUser, boardIdx, boardUpdateRequestDto);
        return baseResponseService.getSuccessResponse(response, CommonResponseStatus.UPDATED);
    }
}
