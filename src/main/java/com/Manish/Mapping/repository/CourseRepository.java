package com.Manish.Mapping.repository;

import com.Manish.Mapping.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}
