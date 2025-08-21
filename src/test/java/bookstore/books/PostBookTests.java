package bookstore.books;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;
import com.example.bookstoreapi.api.BooksApis;
import com.example.bookstoreapi.data.BookDataFactory;
import com.example.bookstoreapi.model.Book;

/**
 * Test class for API POST book endpoint.
 * <p>
 * Contains positive, negative, and edge-case tests for creating books via the API.
 * <ul>
 *   <li>Positive tests verify successful creation with valid and some missing data.</li>
 *   <li>Negative tests cover invalid, missing, null, and edge-case fields.</li>
 * </ul>
 * <p>
 * Comments above each test method describe the expected behavior and any known issues with the fake API.
 */
public class PostBookTests 
{
    private BooksApis booksApis;
    
    /**
     * Initializes the BooksApis instance before each test.
     */
    @BeforeEach
    public void setup() {
       booksApis = new BooksApis();
    }

    /**
     * Positive test: Create a new book with valid data.
     * Verifies that the API returns status code 200 and correct book data.
     */
    @Test
    @Tag("positive")
    @DisplayName("Create a new book with valid data")
    public void createNewBook() {
        Book book = BookDataFactory.createBaseBook();
        Response response = booksApis.createBook(book);
        response.then().assertThat()
            .statusCode(200)
            .body("title", equalTo(book.getTitle()))
            .body("pageCount", equalTo(book.getPageCount()))
            .body("description", equalTo(book.getDescription()))
            .body("excerpt",  equalTo(book.getExcerpt()));

        //delete the alredy created book
        booksApis.deleteBook(book.getId());  
    }

    /**
     * Positive test: Create a new book without description.
     * Description is not a required field in this fake API, so lets assume that is OK to have a book without description.
     * Verifies that the API returns status code 200 and correct book data.
     */
    @Test
    @Tag("positive")
    @DisplayName("Create a new book without description")
    public void createBookWithoutDescription() {
        Book book = BookDataFactory.createBookWithoutDescription();
        Response response = booksApis.createBook(book);
        response.then().assertThat()
            .statusCode(200)
            .body("title", equalTo(book.getTitle()))
            .body("pageCount", equalTo(book.getPageCount()))
            .body("description", equalTo(book.getDescription()))
            .body("excerpt",  equalTo(book.getExcerpt()));

        //delete the alredy created book
        booksApis.deleteBook(book.getId());  
    }

    /**
     * Positive test: Create a new book without excerpt.
     * Excerpt is not a required field in this fake API, so lets assume that is OK to have a book without excerpt.
     * Verifies that the API returns status code 200 and correct book data.
     */
    @Test
    @Tag("positive")
    @DisplayName("Create a new book without excerpt")
    public void createBookWithoutExcerpt() {
        Book book = BookDataFactory.createBookWithoutExcerpt();
        Response response = booksApis.createBook(book);
        response.then().assertThat()
            .statusCode(200)
            .body("title", equalTo(book.getTitle()))
            .body("pageCount", equalTo(book.getPageCount()))
            .body("description", equalTo(book.getDescription()))
            .body("excerpt",  equalTo(book.getExcerpt()));

        //delete the alredy created book
        booksApis.deleteBook(book.getId());  
    }

    /**
     * Positive test: Create a new book without title.
     * There is no logic to be able to create a book without title, but the fake API is returning 200OK.
     * The title should be a mandatory field as the data and the page number.
     * This test case is EXPECTED TO FAIL because the API should not allow a book without title.
     * Verifies that the API returns status code 400.
     */
    @Test
    @Tag("positive")
    @DisplayName("Create a new book without title")
    public void createBookWithoutTitle() {
        Book book = BookDataFactory.createBookWithoutTitle();
        Response response = booksApis.createBook(book);
        response.then().assertThat()
            .statusCode(400);
    }

    /**
     * Negative test: Create a new book without id.
     * Verifies that the API returns status code 400 and a validation error.
     */
    @Test
    @Tag("negative")
    @DisplayName("Create a new book without id")
    public void createBookWithoutId() {
        Book book = BookDataFactory.createBookWithoutId();
        Response response = booksApis.createBook(book);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }

    /**
     * Negative test: Create a new book with negative page count.
     * Negative test case for creating a book with negative page count.
     * This test case is EXPECTED TO FAIL because the API should not allow negative page counts.
     * Verifies that the API returns status code 400.
     */
    @Test
    @Tag("negative")
    @DisplayName("Create a new book with negative page count")
    public void createBookWithNegativePageCount() {
        Book book = BookDataFactory.createBookWithNegativePageCount();
        Response response = booksApis.createBook(book);
        response.then().assertThat()
            .statusCode(400);
    }

    /**
     * Negative test: Create a new book with null as page number.
     * Verifies that the API returns status code 400 and a validation error.
     */
    @Test
    @Tag("negative")
    @DisplayName("Create a new book with null as page number")
    public void createBookWithNullPageNumber() {
        Book book = BookDataFactory.createBookWithNullPages();
        Response response = booksApis.createBook(book);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }

    /**
     * Negative test: Create a new book with invalid date.
     * Verifies that the API returns status code 400 and a validation error.
     */
    @Test
    @Tag("negative")
    @DisplayName("Create a new book with invalid date")
    public void createBookWithInvalidDate() {
        Book book = BookDataFactory.createBookWithInvalidDate();
        Response response = booksApis.createBook(book);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }

    /**
     * Negative test: Create a new book with null date.
     * Verifies that the API returns status code 400 and a validation error.
     */
    @Test
    @Tag("negative")
    @DisplayName("Create a new book with null date")
    public void createBookWithNullDate() {
        Book book = BookDataFactory.createBookWithNullDate();
        Response response = booksApis.createBook(book);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }
}