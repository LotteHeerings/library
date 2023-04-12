package nl.belastingdienst.library.bookLending;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.belastingdienst.library.book.Book;
import nl.belastingdienst.library.user.User;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_book_lending")
public class BookLending {
    @Id
    @Column(name = "ISBN13")
    private String ISBN13;

    @Column(name = "email")
    private String email;

    @Column(name = "return_before")
    private LocalDate returnDate;

    @Column(name = "date_of_hand_out")
    private LocalDate handOutDate;

}
