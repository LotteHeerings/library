package nl.belastingdienst.library.bookReservation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_reservation_book")
public class BookReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ISBN13")
    private String ISBN13;

    @Column(name = "email")
    private String email;

    @Column(name = "date_for_reservation")
    private LocalDate reservationDate;

    @Column(name = "weeks_reserved")
    private Integer weeksReserved;
}
