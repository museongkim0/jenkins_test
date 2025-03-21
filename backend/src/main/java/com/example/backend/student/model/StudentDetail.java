package com.example.backend.student.model;

import com.example.backend.instructor.model.Course;
import com.example.backend.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class StudentDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO INCREMENT
    private Long idx;
    private String address;
    private Boolean testStatus;
    private Integer perception;
    private Integer attendance;
    private Integer leaveEarly;
    private Integer outing;
    private Integer vacationLeft;

    private Integer generation;



    @OneToOne
    @JoinColumn(name="user_idx")
    @JsonIgnore
    private User user;



    @ManyToOne
    @JoinColumn(name = "course_idx")
    private Course course;

    @OneToMany(mappedBy="studentdetail")
    private List<Attendance_Logs> attendance_logs;

    public void updateTestStatus() {
        this.testStatus = true;
    }
    public void updatePerception() {
        this.perception += 1;
    }
    public void updateAttendance() {
        this.attendance += 1;
    }
    public void updateLeaveEarly() {
        this.leaveEarly += 1;
    }
    public void updateOuting() {
        this.outing += 1;
    }
    public void updateVacationLeft() {
        this.vacationLeft -= 1;
    }
    public void updataVacationLeft(int vacationLeft) {
        this.vacationLeft -= vacationLeft;
    }


}
