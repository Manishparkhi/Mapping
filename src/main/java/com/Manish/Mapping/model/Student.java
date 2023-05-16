package com.Manish.Mapping.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tbl_student")
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "student_id")
    private Integer studentId;
    private String name;
    private Integer age;
    private String phoneNumber;
    private String branch;
    private String department;
    @Embedded
    @Column(name = "address")
    private Address address;

}
