package nl.belastingdienst.library.bookLending;

import lombok.RequiredArgsConstructor;
import nl.belastingdienst.library.book.Book;
import nl.belastingdienst.library.book.BookRepository;
import nl.belastingdienst.library.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookLendingService {

    private final BookLendingRepository bookLendingRepository;

    private final BookRepository bookRepository;

    public BookLending lendBook(BookLendingDto lendBook) throws Exception { //CREATE
        if (lendBook.getISBN13() == null) {
            throw new Exception("ISBN is null");
        } else if (lendBook.getEmail() == null) {
            throw new Exception("Email is null");
        } else if (!bookRepository.existsById(lendBook.getISBN13())) {
            throw new Exception("Book is not registered");
        }
        LocalDate now = LocalDate.now();

        var lend = BookLending.builder()
                .ISBN13(lendBook.getISBN13())
                .email(lendBook.getEmail())
                .handOutDate(now)
                .returnDate(now.plusDays(Long.parseLong(lendBook.getWeeksOfLending())))
                .build();

        return bookLendingRepository.save(lend);
    }

    public List<BookLending> viewLentBooks() { //READ
        return bookLendingRepository.findAll();
    }

    public List<BookLending> viewOverDueBooks() {
        LocalDate now = LocalDate.now();
        return viewLentBooks().stream()
                .filter(bookLending -> bookLending.getReturnDate().isBefore(now))
                .collect(Collectors.toList());
    }

    public void handInLentBook(String ISBN13) { //DELETE
        bookLendingRepository.deleteById(ISBN13);
    }

    public BookLending extendLentBook(String ISBN13, Long extensionInDays) throws Exception { //UPDATE
        BookLending bookLending = bookLendingRepository.findById(ISBN13).orElseThrow(() -> new Exception("BookLending not found"));
        bookLending.setReturnDate(LocalDate.now().plusDays(extensionInDays));

        return bookLendingRepository.save(bookLending);
    }

}
