package com.example.backend.instructor.repository;


import com.example.backend.instructor.model.Instructor;
import com.example.backend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstructorRepository  extends JpaRepository<Instructor, Long> {
    Optional<Instructor> findByUser(User user);


    @Query("SELECT i FROM Instructor i JOIN FETCH i.user WHERE i.user.idx = :userIdx")
    Optional<Instructor> findByIdWithUser(@Param("userIdx") Long userIdx);






////    ///
//    @Query("SELECT DISTINCT i FROM Instructor i JOIN FETCH i.user u WHERE i.user.idx = :userIdx")
    Optional<Instructor> findDistinctInstructorByUserIdx(@Param("userIdx") Long userIdx);
////
//


    Optional<Instructor> findByUserIdx(Long userIdx);

 }
