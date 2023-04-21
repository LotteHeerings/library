package nl.belastingdienst.library.bookLending;

import nl.belastingdienst.library.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookLendingRepository extends JpaRepository<BookLending, String> {
    Book findByISBN13(String ISBN13);
}
