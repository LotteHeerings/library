package nl.belastingdienst.library.bookLendingArchive;

import lombok.RequiredArgsConstructor;
import nl.belastingdienst.library.bookLending.BookLending;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookLendingArchiveService {

    private final BookLendingArchiveRepository bookLendingArchiveRepository;

    public BookLendingArchive archiveBookLending(BookLending bookLending, boolean damage) {
        LocalDate now = LocalDate.now();

        if (now.isAfter(bookLending.getReturnDate())){
            //Add damage late hand-in
        }

        if (damage){
            //Add damage service calls for other things
        }

        var bookLendingArchive = BookLendingArchive.builder()
                .ISBN13(bookLending.getISBN13())
                .email(bookLending.getEmail())
                .handOutDate(bookLending.getHandOutDate())
                .handInDate(now)
                .build();

        return bookLendingArchiveRepository.save(bookLendingArchive);
    }

    public List<BookLendingArchive> viewAllArchivedBookLendings() {
        return bookLendingArchiveRepository.findAll();
    }

    public void deleteArchivedBookLending(Long id){
        bookLendingArchiveRepository.deleteById(id);
    }
    
}
