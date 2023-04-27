package nl.belastingdienst.library.bookReservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookReservationRepository extends JpaRepository<BookReservation, Long> {
    Optional<BookReservation> findByISBN13(String ISBN13);
}
