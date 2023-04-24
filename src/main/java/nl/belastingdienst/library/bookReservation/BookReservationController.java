package nl.belastingdienst.library.bookReservation;

import lombok.RequiredArgsConstructor;
import nl.belastingdienst.library.bookLending.BookLending;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookReservationController {

    private final BookReservationService bookReservationService;

    @PostMapping("/reserveBook")
    public BookReservation reserveBook(@RequestBody BookReservationDto newBookReservation) throws Exception {
        return bookReservationService.createBookReservation(newBookReservation);
    }

    @GetMapping("/employee/getAllReservations")
    public List<BookReservation> allReservations() {
        return bookReservationService.getAllReservations();
    }

    @DeleteMapping("/reserveBook/{id}")
    public void deleteReservation(@PathVariable(name = "id") Long id) {
        bookReservationService.deleteReservation(id);
    }

    @PostMapping("/employee/lendOutBook/{reservationId}")
    public BookLending lendOutBookReservation(@PathVariable(name = "reservationId") Long reservationId)
            throws Exception{
        return bookReservationService.pickUpReservation(reservationId);
    }

}
