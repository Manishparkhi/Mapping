package com.Manish.Mapping.service;

import com.Manish.Mapping.model.Laptop;
import com.Manish.Mapping.model.Student;
import com.Manish.Mapping.repository.LaptopRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LaptopService {
    @Autowired
    com.Manish.Mapping.repository.StudentRepository StudentRepository;
    @Autowired
    LaptopRepository repository;
    public String saveLaptop(Laptop laptop, Integer studentId) {
        String response = null;
        if(StudentRepository.existsById(studentId)) {
                List<Laptop> laptopList = repository.findAll();
                for(Laptop laptops: laptopList)
                    if(laptops.getStudent().getStudentId().equals(studentId))
                        return "";

                Student student = StudentRepository.findById(studentId).get();
                laptop.setStudent(student);
                repository.save(laptop);
                response = laptop.getName();
        }
        return response;
    }
    public JSONArray getLaptop(Integer laptopId, Integer studentId) throws JSONException {
        JSONArray laptops = new JSONArray();
        if(studentId == null && laptopId != null && repository.existsById(laptopId)){
            Laptop laptop = repository.findById(laptopId).get();
            JSONObject laptopObj = this.jsonObjectToLaptop(laptop);
            laptops.put(laptopObj);
        } else if(studentId != null) {
            List<Laptop> laptopList = repository.findAll();
            for(Laptop laptop: laptopList) {
                if(laptop.getStudent().getStudentId().equals(studentId)){
                    JSONObject laptopObj = this.jsonObjectToLaptop(laptop);
                    laptops.put(laptopObj);
                }
            }
        } else{
            List<Laptop> laptopList = repository.findAll();
            for(Laptop laptop: laptopList) {
                JSONObject laptopObj = this.jsonObjectToLaptop(laptop);
                laptops.put(laptopObj);
            }
        }
        return laptops;
    }
    private JSONObject jsonObjectToLaptop(Laptop laptop) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("laptopId", laptop.getLaptopId());
        obj.put("name", laptop.getName());
        obj.put("brand", laptop.getBrand());
        obj.put("price", laptop.getPrice());
        return obj;
    }
    public String deleteLaptop(Integer laptopId) {
        String response = null;
        if(repository.existsById(laptopId)){
            response = repository.findById(laptopId).get().getName();
            repository.deleteById(laptopId);
        }
        return response;
    }

    public String updateLaptop(Laptop newLaptop, Integer laptopId) {
        String response = null;
        if(repository.existsById(laptopId)){
            Laptop oldLaptop = repository.findById(laptopId).get();
            this.update(oldLaptop, newLaptop);
            repository.save(oldLaptop);
            response = oldLaptop.getName();
        }
        return response;
    }
    private void update(Laptop oldLaptop, Laptop newLaptop) {
        if(newLaptop.getPrice() != null) oldLaptop.setPrice(newLaptop.getPrice());
        if(newLaptop.getName() != null) oldLaptop.setName(newLaptop.getName());
        if(newLaptop.getBrand() != null) oldLaptop.setBrand(newLaptop.getBrand());
    }
}
