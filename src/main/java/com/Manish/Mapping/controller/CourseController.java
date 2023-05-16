package com.Manish.Mapping.controller;

import com.Manish.Mapping.model.Course;
import com.Manish.Mapping.service.CourseService;
import com.Manish.Mapping.util.Util;
import jakarta.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/course")
public class CourseController {
    @Autowired
    CourseService service;
    @Autowired
    Util util;
    @PostMapping("saveCourse")
    private ResponseEntity<String> saveCourse(@RequestBody String courseStr) throws JSONException {
        Course courseObj = util.StringToJsonToCourse(courseStr);
        String response = service.saveCourse(courseObj);
        return new ResponseEntity<>("Course with name " + response + "saved!", HttpStatus.CREATED);
    }

    @GetMapping("getCourse")
    private ResponseEntity<String> getCourse(@Nullable @RequestParam Integer studentId,
                                             @Nullable @RequestParam Integer courseId) throws JSONException {
        JSONArray response = service.getCourse(studentId, courseId);
        if(response != null && response.length() != 0)
            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        else return new ResponseEntity<>("Course not found!", HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("deleteCourse")
    private ResponseEntity<String> deleteCourse(@RequestParam Integer courseId){
        String response = service.deleteCourse(courseId);
        if(response != null)
            return new ResponseEntity<>("Course with name " + response + " deleted!", HttpStatus.OK);
        else return new ResponseEntity<>("Course not found!", HttpStatus.NOT_FOUND);
    }

    @PutMapping("updateCourse")
    private ResponseEntity<String> updateCourse(@RequestBody Course course, @RequestParam Integer courseId) throws JSONException {
        JSONObject response = service.updateCourse(course, courseId);
        if(response != null)
            return new ResponseEntity<>("Course with name " + response.get("title") + " updated!", HttpStatus.OK);
        else return new ResponseEntity<>("Course not found!", HttpStatus.NOT_FOUND);
    }
}
