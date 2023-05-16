package com.Manish.Mapping.util;

import com.Manish.Mapping.model.Course;
import com.Manish.Mapping.model.Student;
import com.Manish.Mapping.repository.StudentRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Util {
    @Autowired
    StudentRepository studentRepository;

    public Course StringToJsonToCourse(String courseStr) throws JSONException {
        JSONObject jsonObject = new JSONObject(courseStr);
        Course course = new Course();
        course.setTitle(jsonObject.getString("title"));
        course.setDescription(jsonObject.getString("description"));
        course.setDuration(jsonObject.getString("duration"));

        String studentList = jsonObject.getString("studentList");
        String[] studentListArray = studentList.split(",");
        List<Student> studentListList = new ArrayList<>();
        for(String studentId : studentListArray){
            if(studentRepository.existsById(Integer.valueOf(studentId))){
                studentListList.add(studentRepository.findById(Integer.valueOf(studentId)).get());
            }
        }
        course.setStudentList(studentListList);

        return course;
    }

}
