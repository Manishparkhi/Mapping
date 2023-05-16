package com.Manish.Mapping.service;

import com.Manish.Mapping.model.Book;
import com.Manish.Mapping.model.Student;
import com.Manish.Mapping.repository.BookRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    com.Manish.Mapping.repository.StudentRepository StudentRepository;
    @Autowired
    BookRepository repository;
    public void saveBook(Book book, Integer studentId) {
        if(StudentRepository.existsById(studentId)){
            Student student = StudentRepository.findById(studentId).get();
            book.setStudent(student);
            repository.save(book);
        }
    }

    public String deleteBook(Integer studentId, Integer bookId){
        String response = null;
        if(studentId != null && bookId == null){
            List<Book> bookList = repository.findAll();
            for(Book book : bookList){
                if(book.getStudent().getStudentId().equals(studentId))
                   repository.delete(book);
            }
            response = StudentRepository.findById(studentId).get().getName();
        }else if(bookId != null){
            response = repository.findById(bookId).get().getStudent().getName()
                    + " & bookName " + repository.findById(bookId).get().getTitle();
            repository.deleteById(bookId);
        }
        return response;
    }

    public JSONArray getBook(Integer studentId, Integer bookId) throws JSONException {
        JSONArray books = new JSONArray();
        if(studentId == null && bookId == null){
            List<Book> bookList= repository.findAll();
            for(Book book : bookList){
                JSONObject bookObject = this.bookToJsonObject(book);
                books.put(bookObject);
            }
        } else if (studentId != null && bookId == null) {
            List<Book> bookList= repository.findAll();
            for(Book book : bookList){
                if(book.getStudent().getStudentId().equals(studentId)){
                    JSONObject bookObject = this.bookToJsonObject(book);
                    books.put(bookObject);
                }
            }
        } else {
            Book book = repository.findById(bookId).get();
            JSONObject bookObject = this.bookToJsonObject(book);
            books.put(bookObject);
        }
        return books;
    }

    private JSONObject bookToJsonObject(Book book) throws JSONException {
        JSONObject obj = new JSONObject();

        obj.put("bookId", book.getBookId());
        obj.put("title", book.getTitle());
        obj.put("author", book.getAuthor());
        obj.put("description", book.getDescription());
        obj.put("price", book.getPrice());

        return obj;
    }

    public JSONObject updateBook(Book newBook, Integer bookId) throws JSONException {
        if (repository.existsById(bookId)){
            Book oldbook = repository.findById(bookId).get();
            this.update(newBook, oldbook);
            return this.bookToJsonObject(oldbook);
        }else return null;
    }

    private void update(Book newBook, Book oldbook) {
        if (newBook.getTitle() != null) oldbook.setTitle(newBook.getTitle());
        if (newBook.getAuthor() != null) oldbook.setAuthor(newBook.getAuthor());
        if (newBook.getDescription() != null) oldbook.setDescription(newBook.getDescription());
        if (newBook.getPrice() != null) oldbook.setPrice(newBook.getPrice());

        repository.save(oldbook);
    }

}
