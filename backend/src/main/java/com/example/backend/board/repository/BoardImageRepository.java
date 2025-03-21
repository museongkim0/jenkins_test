package com.example.backend.board.repository;

import com.example.backend.board.model.Board;
import com.example.backend.board.model.BoardImage;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {
    default void saveAllImages(List<String> uploadFilePaths, Board board) {
        List<BoardImage> boardImages = uploadFilePaths.stream()
                .map(path -> BoardImage.builder()
                        .url(path)
                        .board(board)
                        .build())
                .toList();
        saveAll(boardImages);
    }

    @Query("SELECT bi.url FROM BoardImage bi WHERE bi.board = :board")
    List<String> findUrlsByBoard(@Param("board") Board board);

    void deleteByBoard(Board board);

    @Modifying
    @Transactional
    @Query("DELETE FROM BoardImage bi WHERE bi.board = :board AND bi.url IN :urls")
    void deleteByBoardAndUrls(@Param("board") Board board, @Param("urls") List<String> urls);

}
