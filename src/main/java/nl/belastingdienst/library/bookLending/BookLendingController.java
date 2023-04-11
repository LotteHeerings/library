package nl.belastingdienst.library.bookLending;

import lombok.RequiredArgsConstructor;
import nl.belastingdienst.library.book.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookLendingController {

    private final BookLendingService bookLendingService;

    @GetMapping("/unavailableBooks")
    public List<Book> unavailableBooks() {
        return null;
    }

    @GetMapping("/myLentBooks")
    public List<BookLending> readMyLentBooks() {
        return null;
    }

    @GetMapping("/employee/overdueBooks")
    public List<BookLending> readOverdueBooks() {
        return bookLendingService.viewOverDueBooks();
    }
}
