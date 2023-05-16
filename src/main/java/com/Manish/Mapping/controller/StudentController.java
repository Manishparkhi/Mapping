package com.Manish.Mapping.controller;

import com.Manish.Mapping.model.Student;
import com.Manish.Mapping.service.StudentService;
import jakarta.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/student")
public class StudentController {
    @Autowired
    StudentService service;
    @PostMapping("saveStudent")
    public ResponseEntity<String> saveStudent(@RequestBody Student student){
        String response = service.saveStudent(student); // response = student name
        return new ResponseEntity<>("Student with name " + response + " saved!", HttpStatus.CREATED);
    }

    @GetMapping("getStudent")
   public ResponseEntity<String> getStudent(@Nullable @RequestParam Integer studentId) throws JSONException {
        JSONArray response = service.getStudent(studentId);
        if(response != null)
            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        else return new ResponseEntity<>("Student not found!", HttpStatus.NOT_FOUND);
    }

    @PutMapping("updateStudent")
    public ResponseEntity<String> updateStudent(@RequestBody Student student, @RequestParam Integer studentId) throws JSONException {
        JSONObject response = service.updateStudent(student,studentId);
        if(response != null)
            return new ResponseEntity<>("Student with name " + response.get("name") + " updated!", HttpStatus.CREATED);
        else return new ResponseEntity<>("Student not found!", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("deleteStudent")
    public ResponseEntity<String> deleteStudent(@RequestParam Integer studentId) throws JSONException {
        String response = service.deleteStudent(studentId); // response = student name
        if(response != null)
            return new ResponseEntity<>("Student with name " + response + " deleted!", HttpStatus.OK);
        else return new ResponseEntity<>("Student not found!", HttpStatus.NOT_FOUND);
    }

}
