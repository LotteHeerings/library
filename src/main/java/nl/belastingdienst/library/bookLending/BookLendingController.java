package nl.belastingdienst.library.bookLending;

import lombok.RequiredArgsConstructor;
import nl.belastingdienst.library.bookLendingArchive.BookLendingArchive;
import nl.belastingdienst.library.damage.DamageDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookLendingController {

    private final BookLendingService bookLendingService;

    @PostMapping("/employee/lendOutBook")
    public BookLending lendOutBook(@RequestBody BookLendingDto bookLending) throws Exception {
        return bookLendingService.lendBook(bookLending);
    }

    @GetMapping("/employee/allLentBooks")
    public List<BookLending> allLentBooks(){
        return bookLendingService.viewLentBooks();
    }

    @GetMapping("/unavailableBooks")
    public ArrayList<ArrayList<String>> unavailableBooks() {
        return bookLendingService.viewLentBooks_withoutSensitiveData();
    }

    @GetMapping("/myLentBooks")
    public List<BookLending> readMyLentBooks() {
        return bookLendingService.viewUsersLentBooks();
    }

    @GetMapping("/employee/overdueBooks")
    public List<BookLending> readOverdueBooks() {
        return bookLendingService.viewOverDueBooks();
    }

    @PostMapping("/employee/handInBook/{isbn13}")
    public BookLendingArchive handInBookLending(@PathVariable(name = "isbn13") String ISBN13, @RequestBody DamageDto damageDto) {
        return bookLendingService.handInLentBook(ISBN13, damageDto);
    }

    @PutMapping("/employee/extendBookLending/{isbn13}/{extensionInDays}")
    public BookLending extendBookLending(
            @PathVariable(name = "isbn13") String ISBN13,
            @PathVariable(name = "extensionInDays") long extensionInDays)
    throws Exception{
        return bookLendingService.extendLentBook(ISBN13, extensionInDays);
    }
}
