package com.example.backend.board.repository;

import com.example.backend.board.model.Board;
import com.example.backend.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Long> {
    /*
    @Query("SELECT b FROM Board b " +
            "JOIN FETCH b.user u1 " +
            "LEFT JOIN FETCH b.comments c " +
            "LEFT JOIN FETCH c.user u2 " +
            "LEFT JOIN FETCH u1.studentDetail " +
            "LEFT JOIN FETCH u2.studentDetail " +
            "WHERE b.idx = :boardIdx")


    @Query("SELECT DISTINCT b FROM Board b " +
            "JOIN FETCH b.imageList " +
            "JOIN FETCH b.user u1 " +
            "LEFT JOIN FETCH u1.studentDetail sd1 " +  // Board 작성자의 studentDetail
            "LEFT JOIN FETCH b.comments c " +
            "LEFT JOIN FETCH c.user u2 " +
            "LEFT JOIN FETCH u2.studentDetail sd2 " +  // Comment 작성자의 studentDetail
            "WHERE b.idx = :boardIdx")

     */
    @Query("SELECT DISTINCT b FROM Board b " +
            "JOIN FETCH b.user u1 " +  // Board 작성자의 user 정보만 FETCH
            "LEFT JOIN FETCH u1.studentDetail sd1 " +  // 작성자의 studentDetail 정보
            "WHERE b.idx = :boardIdx")
    Board getBoardByIdx(Long boardIdx);
    // 만약 studentDetail이 있는 유저와 없는 유저, 둘다 있는 유저, 둘다 없는 유저 이 경우들 일때 쿼리가 어떻게 바뀌어야 하는지..?

    Page<Board> findAllByBoardType(PageRequest of, int boardType);

    Page<Board> findAllByUserAndBoardType(User user, int boardType, PageRequest of);

    Page<Board> findAllByUserIdxAndBoardType(Long userIdx, int boardType, PageRequest of);
}
