package com.example.backend.instructor.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Homework {





    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;



    private String title;
    private String content;




    @ManyToOne
    @JoinColumn(name = "course_idx")
    private Course course;





}
