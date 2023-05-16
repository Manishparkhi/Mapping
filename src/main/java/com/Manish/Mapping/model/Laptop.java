package com.Manish.Mapping.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_laptop")
public class Laptop {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "laptop_id")
    private Integer laptopId;
    private String name;
    private String brand;
    private Integer price;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

}
