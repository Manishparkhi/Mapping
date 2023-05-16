package com.Manish.Mapping.controller;

import com.Manish.Mapping.model.Book;
import com.Manish.Mapping.service.BookService;
import jakarta.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/book")
public class BookController {

    @Autowired
    BookService service;
    @PostMapping("saveBook") // ✅
    private ResponseEntity<String> saveBook(@RequestBody Book book, @RequestParam Integer studentId){
        service.saveBook(book, studentId);
        return new ResponseEntity<>("Book saved!", HttpStatus.CREATED);
    }

    @GetMapping("getBook") // ✅
    private ResponseEntity<String> getBook(@Nullable @RequestParam Integer studentId,
                                           @Nullable @RequestParam Integer bookId) throws JSONException {
        JSONArray response = service.getBook(studentId, bookId);
        if(response.length() != 0)
            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        else return new ResponseEntity<>("Book not found!", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("deleteBook") // ✅
    private ResponseEntity<String> deleteBook(@Nullable @RequestParam Integer studentId, @Nullable Integer bookId){
        if (studentId == null && bookId == null) return new ResponseEntity<>("Enter studentId or bookId", HttpStatus.BAD_REQUEST);

        String response = service.deleteBook(studentId,bookId); // response = student name + Book name
        if(response != null)
            return new ResponseEntity<>("Book of student with name " + response + " deleted!", HttpStatus.OK);
        else return new ResponseEntity<>("Book not found!", HttpStatus.NOT_FOUND);
    }

    @PutMapping("updateBook") // ✅
    private ResponseEntity<String> updateBook(@RequestBody Book book, @RequestParam Integer bookId) throws JSONException {
        JSONObject response = service.updateBook(book,bookId);
        if(response != null)
            return new ResponseEntity<>("Book with name " + response.get("title") + " updated!", HttpStatus.CREATED);
        else return new ResponseEntity<>("Book not found!", HttpStatus.NOT_FOUND);
    }
}
