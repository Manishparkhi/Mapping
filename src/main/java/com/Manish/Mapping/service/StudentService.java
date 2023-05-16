package com.Manish.Mapping.service;

import com.Manish.Mapping.model.Address;
import com.Manish.Mapping.model.Book;
import com.Manish.Mapping.model.Laptop;
import com.Manish.Mapping.model.Student;
import com.Manish.Mapping.repository.BookRepository;
import com.Manish.Mapping.repository.CourseRepository;
import com.Manish.Mapping.repository.LaptopRepository;
import com.Manish.Mapping.repository.StudentRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    LaptopRepository laptopRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    StudentRepository repository;

    public String saveStudent(Student student) {
        repository.save(student);
        return student.getName();
    }
    public JSONArray getStudent(Integer studentId) throws JSONException {
        JSONArray students = new JSONArray();
        if(studentId != null){
            if (repository.existsById(studentId)){
                JSONObject studentJsonObject = this.StudentToJsonObject(repository.findById(studentId).get());
                students.put(studentJsonObject);
            }else return null;
        }else{
            List<Student> studentList = repository.findAll();
            for (Student student : studentList) {
                JSONObject studentJsonObject = StudentToJsonObject(student);
                students.put(studentJsonObject);
            }
        }
        return students;
    }
    private JSONObject StudentToJsonObject(Student student) throws JSONException {
        JSONObject objStudent = new JSONObject();

        objStudent.put("studentId", student.getStudentId());
        objStudent.put("name", student.getName());
        objStudent.put("age", student.getAge());
        objStudent.put("phoneNumber", student.getPhoneNumber());
        objStudent.put("branch", student.getBranch());
        objStudent.put("branch", student.getBranch());
        objStudent.put("department", student.getDepartment());

        JSONObject objStudentAddress = new JSONObject();

        objStudentAddress.put("landmark", student.getAddress().getLandmark());
        objStudentAddress.put("zipcode", student.getAddress().getZipcode());
        objStudentAddress.put("district", student.getAddress().getDistrict());
        objStudentAddress.put("state", student.getAddress().getState());
        objStudentAddress.put("country", student.getAddress().getCountry());

        objStudent.put("address",objStudentAddress);

        return objStudent;
    }
    public JSONObject updateStudent(Student newStudent, Integer studentId) throws JSONException {
        if (repository.existsById(studentId)){
            Student oldStudent = repository.findById(studentId).get();
            this.update(newStudent, oldStudent);
            return this.StudentToJsonObject(oldStudent);
        }else return null;
    }
    private void update(Student newStudent, Student oldStudent) {
        if(newStudent.getName() != null) oldStudent.setName(newStudent.getName());
        if(newStudent.getAge() != null) oldStudent.setAge(newStudent.getAge());
        if(newStudent.getBranch() != null) oldStudent.setBranch(newStudent.getBranch());
        if(newStudent.getPhoneNumber() != null) oldStudent.setPhoneNumber(newStudent.getPhoneNumber());
        if(newStudent.getDepartment() != null) oldStudent.setDepartment(newStudent.getDepartment());
        if (newStudent.getAddress() != null){
            Address address = new Address();

            if(newStudent.getAddress().getCountry() != null) address.setCountry(newStudent.getAddress().getCountry());
            else address.setCountry(oldStudent.getAddress().getCountry());
            if(newStudent.getAddress().getZipcode() != null) address.setCountry(newStudent.getAddress().getZipcode());
            else address.setCountry(oldStudent.getAddress().getZipcode());
            if(newStudent.getAddress().getState() != null) address.setCountry(newStudent.getAddress().getState());
            else address.setCountry(oldStudent.getAddress().getState());
            if(newStudent.getAddress().getDistrict() != null) address.setCountry(newStudent.getAddress().getDistrict());
            else address.setCountry(oldStudent.getAddress().getDistrict());
            if(newStudent.getAddress().getLandmark() != null) address.setCountry(newStudent.getAddress().getLandmark());
            else address.setCountry(oldStudent.getAddress().getLandmark());

            oldStudent.setAddress(address);
        }
        repository.save(oldStudent);
    }
    public String deleteStudent(Integer studentId) {
        String response = null;
        if (repository.existsById(studentId)){
            Student student = repository.findById(studentId).get();
            response = student.getName();
            this.deleteBook(studentId);
            this.deleteLaptop(studentId);
//            this.deleteCourse(studentId);
            repository.deleteById(studentId);
        }
        return response;
    }
    private void deleteLaptop(Integer studentId) {
        List<Laptop> laptopList =  laptopRepository.findAll();
        for(Laptop laptop: laptopList)
            if(laptop.getStudent().getStudentId().equals(studentId))
                laptopRepository.delete(laptop);
    }
    private void deleteBook(Integer studentId){
        List<Book> bookList =  bookRepository.findAll();
        for(Book book: bookList)
            if(book.getStudent().getStudentId().equals(studentId))
                bookRepository.delete(book);
    }
}

