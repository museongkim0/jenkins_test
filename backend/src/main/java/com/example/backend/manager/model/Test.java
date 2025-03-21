package com.example.backend.manager.model;

import com.example.backend.instructor.model.Course;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name="course_idx")
    private Course course;
}
