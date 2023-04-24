package nl.belastingdienst.library.bookReservation;

import lombok.RequiredArgsConstructor;
import nl.belastingdienst.library.book.BookRepository;
import nl.belastingdienst.library.bookLending.BookLending;
import nl.belastingdienst.library.bookLending.BookLendingRepository;
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

    public BookReservation createBookReservation(BookReservationDto newBookReservation) throws Exception{
        String ISBN13 = newBookReservation.getIsbn();
        if (ISBN13 == null) {
            throw new Exception("ISBN13 is null");
        }else if (bookRepository.findByISBN13(ISBN13).isEmpty()) {
            throw new Exception("Book does not exist");
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
    public void deleteReservation(Long id) {
        bookReservationRepository.deleteById(id);
    }

    //(Update) Delete reservation and transform it into a lending
    public BookLending pickUpReservation(Long id) throws Exception {
        Optional<BookReservation> optionalBookReservation = bookReservationRepository.findById(id);
        if (optionalBookReservation.isEmpty()) {
            throw new Exception("This reservation does not exist or has already been picked up");
        }

        BookReservation bookReservation = optionalBookReservation.get();

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
