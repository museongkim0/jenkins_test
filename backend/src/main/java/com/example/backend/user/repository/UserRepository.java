package com.example.backend.user.repository;

import com.example.backend.student.model.Dto.StudentResponseDto;
import com.example.backend.user.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String name);

    List<User> findByRoleEquals(String roleUser);

    List<User> findByRole(String role);



    @Query("SELECT u FROM User u JOIN FETCH u.instructor WHERE u.role = :role")
    List<User> findUsersWithInstructorByRole(@Param("role") String role);

//    List<User> findUsersByRoleWithInstructor(String instructor);
}
