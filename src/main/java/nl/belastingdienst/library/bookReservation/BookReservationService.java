package nl.belastingdienst.library.bookReservation;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import nl.belastingdienst.library.book.Book;
import nl.belastingdienst.library.book.BookRepository;
import nl.belastingdienst.library.bookLending.BookLending;
import nl.belastingdienst.library.bookLending.BookLendingRepository;
import nl.belastingdienst.library.config.JwtService;
import nl.belastingdienst.library.user.Role;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookReservationService {

    private final BookRepository bookRepository;

    private final BookLendingRepository bookLendingRepository;
    private final BookReservationRepository bookReservationRepository;

    private final JwtService jwtService;

    @NonNull
    HttpServletRequest request;

    public BookReservation createBookReservation(BookReservationDto newBookReservation) throws Exception{
        String ISBN13 = newBookReservation.getIsbn();
        if (ISBN13 == null) {
            throw new Exception("ISBN13 is null");
        }else if (bookRepository.findByISBN13(ISBN13).isEmpty()) {
            throw new Exception("Book does not exist");
        } else if (bookReservationRepository.findByISBN13(ISBN13).isPresent()) {
            throw new Exception("Book is already reserved");
        }

        var bookReservation = BookReservation.builder()
                .ISBN13(ISBN13)
                .email(newBookReservation.getEmail())
                .reservationDate(LocalDate.parse(newBookReservation.getReservationDate()))
                .weeksReserved(Integer.parseInt(newBookReservation.getWeeksOfLending()))
                .build();

        return bookReservationRepository.save(bookReservation);
    }

    public List<BookReservation> getAllReservations() {
        return bookReservationRepository.findAll();
    }

    //Delete reservation
    public void deleteReservation(Long id) throws Exception {
        String authHeader = request.getHeader("Authorization"); // Header with JWT
        String jwt = authHeader.substring(7);
        String email = jwtService.extractEmailUser(jwt);

        BookReservation bookReservation = bookReservationRepository.findById(id).get();

        if (bookReservation.getEmail() != email) {
            throw new Exception("This is not your reservation");
        }

        bookReservationRepository.deleteById(id);
    }

    //(Update) Delete reservation and transform it into a lending
    public BookLending pickUpReservation(Long id) throws Exception {
        Optional<BookReservation> optionalBookReservation = bookReservationRepository.findById(id);
        if (optionalBookReservation.isEmpty()) {
            throw new Exception("This reservation does not exist or has already been picked up");
        }

        BookReservation bookReservation = optionalBookReservation.get();

        Optional<BookLending> optionalBookLending = bookLendingRepository.findById(bookReservation.getISBN13());

        if (optionalBookLending.isPresent()) {
            throw new Exception("This book is still registered as handed out");
        }

        LocalDate now = LocalDate.now();

        var bookLending = BookLending.builder()
                .ISBN13(bookReservation.getISBN13())
                .email(bookReservation.getEmail())
                .handOutDate(now)
                .returnDate(now.plusWeeks(bookReservation.getWeeksReserved()))
                .build();

        bookReservationRepository.deleteById(id);
        return bookLendingRepository.save(bookLending);
    }
}
