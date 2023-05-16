package com.Manish.Mapping.service;

import com.Manish.Mapping.model.Course;
import com.Manish.Mapping.model.Student;
import com.Manish.Mapping.repository.CourseRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    CourseRepository repository;

    public String saveCourse(Course course) {
        repository.save(course);
        StringBuilder names = new StringBuilder();
        List<Student> studentList = course.getStudentList();
        for (Student student : studentList) names.append(student.getName()).append(", ");
        return course.getTitle() + " having students " + names;
    }

    public JSONArray getCourse(Integer studentId, Integer courseId) throws JSONException {
        JSONArray courses = new JSONArray();
        if(studentId == null && courseId == null){
            List<Course> studentList = repository.findAll();
            for(Course course : studentList){
                JSONObject courseObj = courseToJsonObj(course);
                courses.put(courseObj);
            }
        } else if(studentId != null && courseId == null) {
            List<Course> courseList = repository.findAll();
            for (Course course : courseList) {
                List<Student> studentList = course.getStudentList();
                for (Student student : studentList) {
                    if (student.getStudentId().equals(studentId)) {
                        JSONObject courseObj = courseToJsonObj(course);
                        courses.put(courseObj);
                    }
                }
            }
        }
        else {
            if (repository.existsById(courseId)){
                Course course = repository.findById(courseId).get();
                JSONObject courseObj = courseToJsonObj(course);
                courses.put(courseObj);
            }else return null;
        }
        return courses;
    }

    private JSONObject courseToJsonObj(Course course) throws JSONException {
        JSONObject courseObj = new JSONObject();

        courseObj.put("courseId", course.getCourseId());
        courseObj.put("title", course.getTitle());
        courseObj.put("description", course.getDescription());
        courseObj.put("duration", course.getDuration());

        return courseObj;
    }

    public String deleteCourse(Integer courseId) {
        String response = null;
        if (repository.existsById(courseId)){
            response = repository.findById(courseId).get().getTitle();
            repository.deleteById(courseId);
        }
        return response;
    }

    public JSONObject updateCourse(Course newCourse, Integer courseId) throws JSONException {
        if (repository.existsById(courseId)){
            Course oldCourse = repository.findById(courseId).get();
            this.update(newCourse, oldCourse);
            return this.courseToJsonObj(oldCourse);
        }else return null;
    }

    private void update(Course newCourse, Course oldCourse) {
        if(newCourse.getTitle() != null) oldCourse.setTitle(newCourse.getTitle());
        if(newCourse.getDescription() != null) oldCourse.setDescription(newCourse.getDescription());
        if(newCourse.getDuration() != null) oldCourse.setDuration(newCourse.getDuration());

        repository.save(oldCourse);
    }
}
