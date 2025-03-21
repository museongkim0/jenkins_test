package com.example.backend.instructor.model;


import com.example.backend.manager.model.Test;
import com.example.backend.student.model.StudentDetail;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String name;
    private int generation;


    @OneToMany(mappedBy = "course")
    private List<StudentDetail> studentDetail;


    @OneToMany(mappedBy = "course")
    @JsonIgnore
    private List<Curriculum> curriculumList = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    @JsonIgnore
    private List<Test> testList = new ArrayList<>();

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "instructor_idx")
    private Instructor instructor;


    @OneToMany(mappedBy = "course")
    private List<Homework> homeworkList = new ArrayList<>();
}
