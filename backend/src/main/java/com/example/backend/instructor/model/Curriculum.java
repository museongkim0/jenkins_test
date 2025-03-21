package com.example.backend.instructor.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Curriculum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private LocalDate curriculumDate;
    private int curriculumDay;
    private String content;
    private String curriculumSubject;
    private int curriculumHours;

    @ManyToOne
    @JoinColumn(name="course_idx")
    private Course course;


}
