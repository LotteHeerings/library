package nl.belastingdienst.library;

import nl.belastingdienst.library.book.BookRepository;
import nl.belastingdienst.library.book.BookService;
import nl.belastingdienst.library.book.BookDto;
import nl.belastingdienst.library.book.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
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
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBook() throws Exception {
        // Arrange
        BookDto newBookDto = new BookDto("1234567890123", "Book Title", "Author", "Publisher",
                "path/to/cover", "Language", "2023-04-14", "100");
        when(bookRepository.findByISBN13(newBookDto.getIsbn())).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenReturn(new Book("1234567890123", "Book Title", "Author", "Publisher",
                "path/to/cover", "Language", "2023-04-14", 100));

        // Act
        Book createdBook = bookService.createBook(newBookDto);

        // Assert
        assertNotNull(createdBook);
        assertEquals(newBookDto.getIsbn(), createdBook.getISBN13());
        assertEquals(newBookDto.getTitle(), createdBook.getTitle());
        assertEquals(newBookDto.getAuthors(), createdBook.getAuthors());
        assertEquals(newBookDto.getPublisher(), createdBook.getPublisher());
        assertEquals(newBookDto.getCover_path(), createdBook.getCover_path());
        assertEquals(newBookDto.getLanguage(), createdBook.getLanguage());
        assertEquals(newBookDto.getPublication_date(), createdBook.getPublication_date());
        assertEquals(Integer.parseInt(newBookDto.getPages()), createdBook.getPages());
        verify(bookRepository, times(1)).findByISBN13(newBookDto.getIsbn());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void testGetAllBooks() {
        // Mocking the bookRepository's findAll method
        List<Book> books = new ArrayList<>();
        books.add(new Book("1234567890123", "Book 1", "Author 1", "Publisher 1", "path/to/cover1.jpg", "English", "2021-01-01", 300));
        books.add(new Book("2345678901234", "Book 2", "Author 2", "Publisher 2", "path/to/cover2.jpg", "Spanish", "2021-02-02", 200));
        when(bookRepository.findAll()).thenReturn(books);

        // Test the getAllBooks() method of BookService
        List<Book> result = bookService.getAllBooks();

        // Assertions
        assertEquals(2, result.size());
        assertEquals("1234567890123", result.get(0).getISBN13());
        assertEquals("Book 1", result.get(0).getTitle());
        assertEquals("Author 1", result.get(0).getAuthors());
        assertEquals("Publisher 1", result.get(0).getPublisher());
        assertEquals("path/to/cover1.jpg", result.get(0).getCover_path());
        assertEquals("English", result.get(0).getLanguage());
        assertEquals("2021-01-01", result.get(0).getPublication_date());
        assertEquals(300, result.get(0).getPages());

        assertEquals("2345678901234", result.get(1).getISBN13());
        assertEquals("Book 2", result.get(1).getTitle());
        assertEquals("Author 2", result.get(1).getAuthors());
        assertEquals("Publisher 2", result.get(1).getPublisher());
        assertEquals("path/to/cover2.jpg", result.get(1).getCover_path());
        assertEquals("Spanish", result.get(1).getLanguage());
        assertEquals("2021-02-02", result.get(1).getPublication_date());
        assertEquals(200, result.get(1).getPages());
    }

    @Test
    public void testGetAllBooksByAuthor() {
        // Arrange
        List<Book> allBooks = Arrays.asList(
                new Book("1234567890123", "Book 1", "Author 1", null, null, null, "2002-12-12", 0),
                new Book("2345678901234", "Book 2", "Author 1, Author 2", null, null, null, "2002-12-12", 0),
                new Book("3456789012345", "Book 3", "Author 2", null, null, null, "2002-12-12", 0));

        when(bookRepository.findAll()).thenReturn(allBooks);

        // Act
        List<Book> result = bookService.getAllBooksByAuthor("Author 1");

        // Assert
        assertEquals(2, result.size());
        assertEquals("Book 1", result.get(0).getTitle());
        assertEquals("Author 1", result.get(0).getAuthors());
        assertEquals("Book 2", result.get(1).getTitle());
        assertEquals("Author 1, Author 2", result.get(1).getAuthors());
    }

    @Test
    public void testGetAllBooksByTitle() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("1234567890123", "Clean Code", "Robert C. Martin", "Prentice Hall", "path/to/cover", "EN", "2008-08-01", 464));
        books.add(new Book("2345678901234", "The Design of Everyday Things", "Don Norman", "Basic Books", "path/to/cover", "EN", "2013-11-05", 368));
        books.add(new Book("3456789012345", "Thinking, Fast and Slow", "Daniel Kahneman", "Farrar, Straus and Giroux", "path/to/cover", "EN", "2002-11-03", 512));
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> booksWithTitle = bookService.getAllBooksByTitle("Clean");

        assertEquals(1, booksWithTitle.size());
        assertEquals("Clean Code", booksWithTitle.get(0).getTitle());
        assertEquals("1234567890123", booksWithTitle.get(0).getISBN13());
    }

    @Test
    public void testGetAllBooksByPublisher() {
        Book book1 = new Book("Book 1", "JavaUwU", null, "Publisher A", null, null, "2008-08-01", 0);
        Book book2 = new Book("Book 2", "PythonOwO", null, "Publisher A", null, null, "2008-08-01", 0);
        Book book3 = new Book("Book 3", "PHP", null, "Publisher B", null, null, "2008-08-01", 0);
        List<Book> allBooks = new ArrayList<>(Arrays.asList(book1, book2, book3));

        when(bookService.getAllBooks()).thenReturn(allBooks);

        BookService bookService = new BookService(bookRepository);
        List<Book> booksByPublisher = bookService.getAllBooksByPublisher("Publisher A");

        assertEquals(2, booksByPublisher.size());
        assertEquals("JavaUwU", booksByPublisher.get(0).getTitle());
        assertEquals("PythonOwO", booksByPublisher.get(1).getTitle());
    }

    @Test
    public void testDeleteBook() {
        String ISBN13 = "1234567890123";

        BookService bookService = new BookService(bookRepository);
        bookService.deleteBook(ISBN13);

        verify(bookRepository, times(1)).deleteById(ISBN13);
    }

    @Test
    void testUpdateBook() throws Exception {
        // Create a new book
        BookDto bookDto = new BookDto("1234567890123", "Book Title", "Author", "Publisher",
                "path/to/cover", "EN", "2022-01-01", "200");
        bookService.createBook(bookDto);

        Optional<Book> optionalBook = bookRepository.findByISBN13("1234567890123");
        if (optionalBook.isEmpty()){
            System.out.println("sadge");
        }

        System.out.println("createdBook: " + bookDto); // Debugging info
        System.out.println("repo: " + optionalBook.get());

        // Update the book
        BookDto updatedBook = new BookDto("1234567890123", "New Title", "New Author", "New Publisher",
                "path/to/new/cover", "FR", "2023-01-01", "250");
        Book updated = bookService.updateBook("1234567890123", updatedBook);

        // Check that the book was updated correctly
        assertEquals(updatedBook.getTitle(), updated.getTitle());
        assertEquals(updatedBook.getAuthors(), updated.getAuthors());
        assertEquals(updatedBook.getLanguage(), updated.getLanguage());
        assertEquals(updatedBook.getPublisher(), updated.getPublisher());
        assertEquals(updatedBook.getPublication_date(), updated.getPublication_date());
        assertEquals(updatedBook.getCover_path(), updated.getCover_path());
        assertEquals(Integer.valueOf(updatedBook.getPages()), updated.getPages());
    }

    @Test
    void testUpdateNonExistentBook() {
        // Try to update a book with an invalid ISBN13
        assertThrows(Exception.class, () -> bookService.updateBook("invalid-isbn13", new BookDto()));
    }

    @Test
    void testUpdateBookWithNullValues() throws Exception {
        // Create a new book
        BookDto newBook = new BookDto("1234567890123", "Book Title", "Author", "Publisher",
                "path/to/cover", "EN", "2022-01-01", "200");
        Book createdBook = bookService.createBook(newBook);
        System.out.println(createdBook);
        // Try to update the book with null values
        BookDto updatedBook = new BookDto("1234567890123", "null", "null", "null",
                "null", null, "null", "null");
        assertThrows(Exception.class, () -> bookService.updateBook(createdBook.getISBN13(), updatedBook));
    }


}
