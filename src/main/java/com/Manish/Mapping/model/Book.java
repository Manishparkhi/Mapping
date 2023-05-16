package com.Manish.Mapping.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tbl_book")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "book_id")
    private Integer bookId;
    private String title;
    private String author;
    private String description;
    private String price;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
