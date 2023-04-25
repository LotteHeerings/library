package nl.belastingdienst.library.book;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/employee/books")
    public Book createBook(@RequestBody BookDto book) throws Exception{
        return bookService.createBook(book);
    }

    @GetMapping("/books")
    public List<Book> readBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/books/author/{author}")
    public List<Book> readBooksByAuthor(@PathVariable(name = "author") String author) {
        return bookService.getAllBooksByAuthor(author);
    }

    @GetMapping("/books/publisher/{publisher}")
    public List<Book> readBooksByPublisher(@PathVariable(name = "publisher") String publisher) {
        return bookService.getAllBooksByPublisher(publisher);
    }

    @GetMapping("/books/title/{title}")
    public List<Book> readBooksByTitle(@PathVariable(name = "title") String title) {
        return bookService.getAllBooksByTitle(title);
    }

    @DeleteMapping("/employee/books/{isbn13}")
    public void deleteBooks(@PathVariable(name = "isbn13") String ISBN13) throws Exception {
        bookService.deleteBook(ISBN13);
    }

    @PutMapping("/employee/books/{isbn13}")
    public Book updateBook(@PathVariable(name = "isbn13") String ISBN13, @RequestBody BookDto bookDetails) throws Exception {
        System.out.println(bookDetails); //debug
        return bookService.updateBook(ISBN13, bookDetails);
    }
}
