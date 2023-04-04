package nl.belastingdienst.library.controller;

import nl.belastingdienst.library.model.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
public class BookController {

    private List<Book> books = new ArrayList<>();
    @GetMapping("/books")
    public List<Book> books(){
        Book testBook1 = new Book();
        testBook1.setISBN(1);
        testBook1.setAuthors("Me");
        testBook1.setLanguage("NL");
        testBook1.setTitle("Testing is fun");
        testBook1.setPublication_date(new Date());
        testBook1.setPublisher("Your mom");
        testBook1.setCover_path("/user/user/user");

        books.add(testBook1);
        return books;
    }

    @PostMapping("/students")
    public Book createBook(@RequestBody Book book){
        books.add(book);
        return book;
    }
}
