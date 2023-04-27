package nl.belastingdienst.library;

import nl.belastingdienst.library.book.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTests {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateBook() throws Exception {
        // Arrange
        BookDto newBook = new BookDto("123", "testTitle", "me", "also me",
                "never implemented this part", "EN", LocalDate.now().toString(), "69");

        Book testBook = Book.builder()
                .ISBN13(newBook.getIsbn())
                .title(newBook.getTitle())
                .authors(newBook.getAuthors())
                .publisher(newBook.getPublisher())
                .cover_path(newBook.getCover_path())
                .language(newBook.getLanguage())
                .publication_date(newBook.getPublication_date())
                .pages(Integer.parseInt(newBook.getPages()))
                .build();

        when(bookRepository.findByISBN13(newBook.getIsbn())).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenReturn(testBook);

        //Act
        Book testResult = bookService.createBook(newBook);

        //Assert
        assertNotNull(testResult);
        assertEquals(newBook.getIsbn(), testResult.getISBN13());
        assertEquals(newBook.getTitle(), testResult.getTitle());
        assertEquals(newBook.getAuthors(), testResult.getAuthors());
        assertEquals(newBook.getPublisher(), testResult.getPublisher());
        assertEquals(newBook.getCover_path(), testResult.getCover_path());
        assertEquals(newBook.getLanguage(), testResult.getLanguage());
        assertEquals(newBook.getPublication_date(), testResult.getPublication_date());
        assertEquals(newBook.getPages(), testResult.getPages().toString());
        verify(bookRepository, times(1)).findByISBN13(newBook.getIsbn());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void testCreateExistingBook() {
        // Arrange
        BookDto newBook = new BookDto("123", "testTitle", "me", "also me",
                "never implemented this part", "EN", LocalDate.now().toString(), "69");
        Book existingBook = Book.builder()
                .ISBN13(newBook.getIsbn())
                .title(newBook.getTitle())
                .publication_date(newBook.getPublication_date())
                .pages(Integer.parseInt(newBook.getPages()))
                .build();
        when(bookRepository.findByISBN13(newBook.getIsbn())).thenReturn(Optional.of(existingBook));

        // Act and Assert
        assertThrows(Exception.class, () -> bookService.createBook(newBook));
        verify(bookRepository, times(1)).findByISBN13(newBook.getIsbn());
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    public void getAllBooks() {
        // Arrange
        var book1 = Book.builder()
                .ISBN13("1234")
                .title("ahem 1234")
                .publication_date(LocalDate.now().minusDays(1).toString())
                .pages(420)
                .build();

        var book2 = Book.builder()
                .ISBN13("12345")
                .title("ahem 12345")
                .publication_date(LocalDate.now().toString())
                .pages(69)
                .build();

        List<Book> expectedBooks = Arrays.asList(book1, book2);

        when(bookRepository.findAll()).thenReturn(expectedBooks);

        // Act
        List<Book> actualBooks = bookService.getAllBooks();

        // Assert
        assertEquals(expectedBooks, actualBooks);
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllBooksByAuthor() {
        // Arrange
        var book1 = Book.builder()
                .ISBN13("1234")
                .title("ahem 1234")
                .authors("hurb")
                .publication_date(LocalDate.now().minusDays(1).toString())
                .pages(420)
                .build();

        var book2 = Book.builder()
                .ISBN13("12345")
                .title("ahem 12345")
                .authors("bruh")
                .publication_date(LocalDate.now().toString())
                .pages(69)
                .build();

        List<Book> books = Arrays.asList(book1, book2);

        when(bookService.getAllBooks()).thenReturn(books);

        // Act
        List<Book> result1 = bookService.getAllBooksByAuthor("hurb");
        List<Book> result2 = bookService.getAllBooksByAuthor("bruh");

        // Assert
        assertEquals(Arrays.asList(book1), result1);
        assertEquals(Arrays.asList(book2), result2);
    }

    @Test
    public void testGetAllBooksByTitle() {
        // Arrange
        var book1 = Book.builder()
                .ISBN13("1234")
                .title("ahem 1234")
                .publication_date(LocalDate.now().minusDays(1).toString())
                .pages(420)
                .build();

        var book2 = Book.builder()
                .ISBN13("12345")
                .title("ahem 1235")
                .publication_date(LocalDate.now().toString())
                .pages(69)
                .build();

        List<Book> books = Arrays.asList(book1, book2);

        when(bookService.getAllBooks()).thenReturn(books);

        // Act
        List<Book> result1 = bookService.getAllBooksByTitle("ahem 1234");
        List<Book> result2 = bookService.getAllBooksByTitle("ahem 1235");

        // Assert
        assertEquals(Arrays.asList(book1), result1);
        assertEquals(Arrays.asList(book2), result2);
    }

    @Test
    public void testGetAllBooksByPublisher() {
        // Arrange
        var book1 = Book.builder()
                .ISBN13("1234")
                .title("ahem 1234")
                .publisher("kobo")
                .publication_date(LocalDate.now().minusDays(1).toString())
                .pages(420)
                .build();

        var book2 = Book.builder()
                .ISBN13("12345")
                .title("ahem 1235")
                .publisher("brook")
                .publication_date(LocalDate.now().toString())
                .pages(69)
                .build();

        List<Book> books = Arrays.asList(book1, book2);

        when(bookService.getAllBooks()).thenReturn(books);

        // Act
        List<Book> result1 = bookService.getAllBooksByPublisher("kobo");
        List<Book> result2 = bookService.getAllBooksByPublisher("brook");

        // Assert
        assertEquals(Arrays.asList(book1), result1);
        assertEquals(Arrays.asList(book2), result2);
    }

    @Test
    public void testDeleteBookSuccessful() throws Exception {
        // Arrange
        Book book = Book.builder()
                .ISBN13("1")
                .title("2")
                .publication_date(LocalDate.now().toString())
                .pages(3)
                .build();

        when(bookRepository.findById("1")).thenReturn(Optional.of(book));

        // Act
        bookService.deleteBook("1");

        // Assert
        verify(bookRepository).deleteById("1");
    }

    @Test
    public void testDeleteBookNotFound() throws Exception {
        // Arrange
        when(bookRepository.findById("1234567890123")).thenReturn(Optional.empty());

        // Call the deleteBook method and expect an exception to be thrown
        assertThrows(Exception.class, () -> bookService.deleteBook("1234567890123"));

        // Verify that the deleteById method was not called
        verify(bookRepository).findById("1234567890123");
        verify(bookRepository, never()).deleteById("1234567890123");
    }

    @Test
    public void testUpdateBookSuccessful() throws Exception {
        // Arrange
        Book book = Book.builder()
                .ISBN13("123")
                .title("cool")
                .publication_date("2019-02-13")
                .pages(666)
                .build();

        Optional<Book> bookOptional = Optional.of(book);
        when(bookRepository.findByISBN13("123")).thenReturn(bookOptional);

        BookDto bookDetails = new BookDto("123", "hurb", "bruh", "hurb",
                "path/to/cover", "EN", "2002-28-12", "20");

        // Act
        bookService.updateBook("123", bookDetails);
        Book updatedBook = bookRepository.findByISBN13("123").get();

        // Assert
        assertEquals(bookDetails.getTitle(), updatedBook.getTitle());
        assertEquals(bookDetails.getAuthors(), updatedBook.getAuthors());
        assertEquals(bookDetails.getLanguage(), updatedBook.getLanguage());
        assertEquals(bookDetails.getPublisher(), updatedBook.getPublisher());
        assertEquals(bookDetails.getCover_path(), updatedBook.getCover_path());
        assertEquals(Integer.parseInt(bookDetails.getPages()), updatedBook.getPages());
        assertEquals(bookDetails.getPublication_date(), updatedBook.getPublication_date());
        verify(bookRepository).save(updatedBook);
    }

    @Test
    public void testUpdateBookNotFound() throws Exception {
        // Arrange
        when(bookRepository.findByISBN13("123")).thenReturn(Optional.empty());

        // Act
        BookDto bookDetails = new BookDto("Book 1", "Author 1", "English", "Publisher 1",
                "path/to/cover", "EN", "2002-28-12", "20");
        assertThrows(Exception.class, () -> bookService.updateBook("123", bookDetails));

        // Assert
        verify(bookRepository, never()).save(any(Book.class));
    }

}
