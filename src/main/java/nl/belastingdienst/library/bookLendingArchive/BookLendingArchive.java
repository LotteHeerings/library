package nl.belastingdienst.library.bookLendingArchive;

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
@Table(name = "_archives_book_lending")
public class BookLendingArchive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ISBN13")
    private String ISBN13;

    @Column(name = "email")
    private String email;

    @Column(name = "date_of_hand_out")
    private LocalDate handOutDate;

    @Column(name = "date_of_hand_in")
    private LocalDate handInDate;
}
