package nl.belastingdienst.library.bookLendingArchive;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookLendingArchiveRepository extends JpaRepository<BookLendingArchive, Long> {

}
