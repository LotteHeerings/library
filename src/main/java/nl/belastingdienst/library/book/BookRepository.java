package nl.belastingdienst.library.book;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {

    Optional<Book> findByAuthors(String authors);
    Optional<Book> findByPublisher(String publisher);
    Book findByTitle(String title);

}
