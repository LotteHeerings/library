package nl.belastingdienst.library.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book createBook(BookDto newBook) throws Exception{
        String ISBN13 = newBook.getIsbn();
        if (ISBN13 == null) {
            throw new Exception("Id/ISBN13 is null");
        }
        if (bookRepository.findByISBN13(ISBN13).isPresent()) {
            throw new Exception("Book is already registered");
        }

        var book = Book.builder()
                .ISBN13(ISBN13)
                .title(newBook.getTitle())
                .authors(newBook.getAuthors())
                .publisher(newBook.getPublisher())
                .cover_path(newBook.getCover_path())
                .language(newBook.getLanguage())
                .publication_date(newBook.getPublication_date())
                .pages(Integer.valueOf(newBook.getPages()))
                .build();

        return bookRepository.save(book);
    }

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public List<Book> getAllBooksByAuthor(String authors){
        return getAllBooks().stream()
                .filter(book -> book.getAuthors() != null && book.getAuthors().contains(authors))
                .collect(Collectors.toList());
    }

    public List<Book> getAllBooksByTitle(String title){
        return getAllBooks().stream()
                .filter(book -> book.getTitle().contains(title))
                .collect(Collectors.toList());
    }

    public List<Book> getAllBooksByPublisher(String publisher){
        return getAllBooks().stream()
                .filter(book -> book.getPublisher() != null && book.getPublisher().contains(publisher))
                .collect(Collectors.toList());
    }

    public void deleteBook(String ISBN13){
        bookRepository.deleteById(ISBN13);
    }

    public Book updateBook (String ISBN13, BookDto bookDetails) throws Exception{
        Optional<Book> bookOptional = bookRepository.findByISBN13(ISBN13);
        if (bookOptional.isEmpty()) {
            throw new Exception("ISBN13 of: " + ISBN13 + " not found");
        }

        Book book = bookOptional.get();

        book.setAuthors(bookDetails.getAuthors());
        book.setLanguage(bookDetails.getLanguage());
        book.setPublisher(bookDetails.getPublisher());
        book.setTitle(bookDetails.getTitle());
        book.setPublication_date(bookDetails.getPublication_date());
        book.setCover_path(bookDetails.getCover_path());
        book.setPages(Integer.parseInt(bookDetails.getPages()));

        return bookRepository.save(book);
    }
}
