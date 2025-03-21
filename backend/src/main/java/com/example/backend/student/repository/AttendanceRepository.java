package com.example.backend.student.repository;

import com.example.backend.student.model.Attendance_Logs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance_Logs,Long> {
}
