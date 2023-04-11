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
    public Book createBook(@RequestBody Book book) throws Exception{
        return bookService.createBook(book);
    }

    @GetMapping("/books")
    public List<Book> readBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/books/author/{authors}")
    public List<Book> readBooksByAuthor(@PathVariable(name = "authors") String authors) {
        return bookService.getAllBooksByAuthor(authors);
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
    public void deleteBooks(@PathVariable(name = "isbn13") Integer ISBN13) {
        bookService.deleteBook(ISBN13);
    }

    @PutMapping("/employee/books/{isbn13}")
    public Book updateBook(@PathVariable(name = "isbn13") Integer ISBN13, @RequestBody Book bookDetails) {
        return bookService.updateBook(ISBN13, bookDetails);
    }
}
