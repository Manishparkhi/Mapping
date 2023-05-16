package com.Manish.Mapping.controller;

import com.Manish.Mapping.model.Laptop;
import com.Manish.Mapping.service.LaptopService;
import jakarta.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/laptop")
public class LaptopController {
    @Autowired
    LaptopService service;
    @PostMapping("saveLaptop")
    private ResponseEntity<String> saveLaptop(@RequestBody Laptop laptop, @RequestParam Integer studentId){
        String response = service.saveLaptop(laptop, studentId);
        if(response != null){
            if(response.equals("")) return new ResponseEntity<>("Student doesn't exist", HttpStatus.BAD_REQUEST);
            else return new ResponseEntity<>(response + "Laptop saved!", HttpStatus.CREATED);
        }
        else
            return new ResponseEntity<>("Laptop already exist ", HttpStatus.BAD_REQUEST);
    }
    @GetMapping("getLaptop")
    private ResponseEntity<String> getLaptop(@Nullable @RequestParam Integer laptopId,
                                             @Nullable @RequestParam Integer studentId) throws JSONException {
        JSONArray response = service.getLaptop(laptopId, studentId);
        if(response.length() != 0)
            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        else return new ResponseEntity<>("Laptop not found!", HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("deleteLaptop")
    private ResponseEntity<String> deleteLaptop(@RequestParam Integer laptopId){
        String response = service.deleteLaptop(laptopId);
        if(response != null)
            return new ResponseEntity<>(response + " Laptop deleted!", HttpStatus.OK);
        else
            return new ResponseEntity<>("Laptop already exist!", HttpStatus.BAD_REQUEST);
    }
    @PutMapping("updateLaptop")
    private ResponseEntity<String> updateLaptop(@RequestBody Laptop laptop,
                                                @RequestParam Integer laptopId){
        String response = service.updateLaptop(laptop,laptopId);
        if(response != null)
            return new ResponseEntity<>(response + " Laptop updated!", HttpStatus.OK);
        else
            return new ResponseEntity<>("Laptop not found!", HttpStatus.NOT_FOUND);
    }

}
