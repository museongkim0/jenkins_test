package com.example.backend.manager.repository;

import com.example.backend.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManagerRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = {"studentDetail", "boards"})
    Page<User> findByRole(String role, Pageable pageable);
}
