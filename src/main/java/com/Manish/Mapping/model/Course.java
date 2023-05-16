package com.Manish.Mapping.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_course")
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "course_id")
    private Integer courseId;
    private String title;
    private String description;
    private String duration;

    @ManyToMany
    @JoinColumn(name = "student_id")
    List<Student> studentList = new ArrayList<>();
}
