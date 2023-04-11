package nl.belastingdienst.library.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book createBook(Book book) throws Exception{
        if (bookRepository.existsById(book.getISBN13())) {
            throw new Exception("Book is already registered");
        }
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public List<Book> getAllBooksByAuthor(String authors){
        return getAllBooks().stream()
                .filter(book -> book.getAuthors().contains(authors))
                .collect(Collectors.toList());
    }

    public List<Book> getAllBooksByTitle(String title){
        return getAllBooks().stream()
                .filter(book -> book.getTitle().contains(title))
                .collect(Collectors.toList());
    }

    public List<Book> getAllBooksByPublisher(String publisher){
        return getAllBooks().stream()
                .filter(book -> book.getPublisher().contains(publisher))
                .collect(Collectors.toList());
    }

    public void deleteBook(Long ISBN13){
        bookRepository.deleteById(ISBN13);
    }

    public Book updateBook (Long ISBN13, Book bookDetails) throws Exception{
        if (bookRepository.findById(bookDetails.getISBN13()).isPresent()) {
            throw new Exception("ISBN13 address is already taken.");
        }
        Book book = bookRepository.findById(ISBN13).get();
        book.setAuthors(book.getAuthors());
        book.setLanguage(book.getLanguage());
        book.setPublisher(book.getPublisher());
        book.setTitle(book.getTitle());
        book.setPublication_date(book.getPublication_date());
        book.setCover_path(bookDetails.getCover_path());

        bookRepository.deleteById(ISBN13);
        return bookRepository.save(book);
    }
}
