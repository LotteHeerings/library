package nl.belastingdienst.library.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    Optional<Book> findByISBN13(String ISBN13);
    Optional<Book> findByAuthors(String authors);
    Optional<Book> findByPublisher(String publisher);
    Book findByTitle(String title);

}
