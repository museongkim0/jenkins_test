package com.example.backend.instructor.model;

import com.example.backend.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String record;
    private String portfolio;
    @OneToOne
    @JoinColumn(name = "user_idx")
    private User user;


    @OneToMany(mappedBy = "instructor", fetch = FetchType.LAZY)
    private List<Course> course;

    public Instructor(User user, String record, String portfolio) {
        this.user = user;
        this.record = record;
        this.portfolio = portfolio;
    }
}
