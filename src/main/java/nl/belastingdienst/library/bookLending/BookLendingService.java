package nl.belastingdienst.library.bookLending;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import nl.belastingdienst.library.book.Book;
import nl.belastingdienst.library.book.BookDto;
import nl.belastingdienst.library.book.BookRepository;
import nl.belastingdienst.library.config.JwtAuthenticationFilter;
import nl.belastingdienst.library.config.JwtService;
import nl.belastingdienst.library.user.UserRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookLendingService {

    private final BookLendingRepository bookLendingRepository;

    private final BookRepository bookRepository;

    private final JwtService jwtService;

    @NonNull
    HttpServletRequest request;

    public BookLending lendBook(BookLendingDto lendBook) throws Exception { //CREATE
        String ISBN13 = lendBook.getIsbn();
        if (ISBN13 == null) {
            throw new Exception("ISBN13 is null");
        } else if (lendBook.getEmail() == null) {
            throw new Exception("Email is null");
        } else if (!bookRepository.existsById(ISBN13)) {
            throw new Exception("Book is not registered");
        }
        LocalDate now = LocalDate.now();

        var lend = BookLending.builder()
                .ISBN13(ISBN13)
                .email(lendBook.getEmail())
                .handOutDate(now)
                .returnDate(now.plusDays(Long.parseLong(lendBook.getWeeksOfLending())))
                .build();

        return bookLendingRepository.save(lend);
    }

    public List<BookLending> viewLentBooks() { //READ
        return bookLendingRepository.findAll();
    }

    public ArrayList<ArrayList<String>> viewLentBooks_withoutSensitiveData() {
        List<BookLending> allLentBooks = viewLentBooks();

        ArrayList<ArrayList<String>> allLentBooks_withOutSensitiveData = new ArrayList<>();
        for (BookLending bookLending: allLentBooks) {
            ArrayList<String> innerList = new ArrayList<>();
            innerList.add(bookLending.getISBN13());
            innerList.add(String.valueOf(bookLending.getHandOutDate()));
            innerList.add(String.valueOf(bookLending.getReturnDate()));
            allLentBooks_withOutSensitiveData.add(innerList);
        }
        return allLentBooks_withOutSensitiveData;
    }

    public List<BookLending> viewUsersLentBooks() { //READ
        String authHeader = request.getHeader("Authorization"); // Header with JWT
        String jwt = authHeader.substring(7);
        String email = jwtService.extractEmailUser(jwt);

        return viewLentBooks().stream()
                .filter(bookLending -> bookLending.getEmail().equals(email))
                .collect(Collectors.toList());
    }

    public List<BookLending> viewOverDueBooks() { //READ
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
