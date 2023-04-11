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
    @OneToOne
    @Column(name = "ISBN13")
    private Book book;

    @ManyToOne
    @Column(name = "email")
    private User user;

    @Column(name = "return_before")
    private LocalDate returnDate;

    @Column(name = "date_of_handout")
    private LocalDate handOutDate;

}
