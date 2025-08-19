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
 * API tests for GET books.
 */
public class GetBookTests 
{
    private BooksApis booksApis;
    

    private int nonExistingId = 777777; // Example of a non-existing ID. The valid ID range is between 1 and 200.
    private int validIdWithExtraZeros = 0001;  //Example of a valid ID with few extra zeros upfront.
    private int idAboveMaxId = 201; // Example of a current maximum ID that should not be valid.
    private String alphaNumericInput = "abc123"; // Example of an alphanumeric input that should not be valid.
    private String specialSign = "*!"; // Example of a special sign input that should not be valid.
    private int negativeId = -1; // Example of a negative ID that should not be valid.
    private String nullId = null; // Example of a null ID that should not be valid.
    private int maxIntegerId = Integer.MAX_VALUE; // Example of a maximum integer ID that should not be valid.
    private int minIntegerId = Integer.MIN_VALUE; // Example of a minimum integer ID that should not be valid.
    
    @BeforeEach
    public void setup() {
       booksApis = new BooksApis();
    }

    @Test
    @Tag("positive")
    @DisplayName("Test getting all books")
    public void getAllBooks() {
        Response response = booksApis.getAllBooks();
        response.then().assertThat()
            .statusCode(200)
            .body("size()", greaterThan(0));
    }

    @Test
    @Tag("positive")
    @DisplayName("Test getting a book by existing ID")
    public void getBookByExistingId() {
        // Create a random book and store its ID for use in test
        int createdBookId;
        Book book = BookDataFactory.createBaseBook();
        Response createResponse = booksApis.createBook(book);
        createdBookId = createResponse.then()
            .statusCode(200)
            .extract()
            .path("id");

        Response response = booksApis.getBookById(createdBookId);
        response.then().assertThat()
            .statusCode(200)
            .body("id", equalTo(createdBookId));
    }

    @Test
    @Tag("positive")
    @DisplayName("Test getting a book using an existing id but written with few more zeros upfront")
    public void getBookByExistingIdAndExtraZeros() {
        Response response = booksApis.getBookById(validIdWithExtraZeros);
        response.then().assertThat()
            .statusCode(200);
    }

    @Test
    @Tag("negative")
    @DisplayName("Test getting a book by id that is above the maxID")
    public void getBookByIdAboveMaxId() {
        Response response = booksApis.getBookById(idAboveMaxId);
        response.then().assertThat()
            .statusCode(404)
            .body("title", equalTo("Not Found"));
    }

    @Test
    @Tag("negative")
    @DisplayName("Test getting a book by a non-existing ID")
    public void getBookByNonExistingId() {
        Response response = booksApis.getBookById(nonExistingId);
        response.then().assertThat()
            .statusCode(404)
            .body("title", equalTo("Not Found"));
    }

    // If we imagine that the ID must be higher than 0, then this test case is negative.
    @Test
    @Tag("negative")
    @DisplayName("Test getting a book using a negative ID")
    public void getBookByNegativeId() {
        Response response = booksApis.getBookById(negativeId);
        response.then().assertThat()
            .statusCode(404)
            .body("title", equalTo("Not Found"));
    }


    @Test
    @Tag("negative")
    @DisplayName("Test getting a book by alphanumeric id")
    public void getBookByAlphaNumericId() {
        Response response = booksApis.getBookById(alphaNumericInput);
        response.then().assertThat()
            .statusCode(400);
    }     

    @Test
    @Tag("negative")
    @DisplayName("Test getting a book using a special sign")
    public void getBookBySpecialSign() {
        Response response = booksApis.getBookById(specialSign);
        response.then().assertThat()
            .statusCode(400);
    }

    @Test
    @Tag("negative")
    @DisplayName("Test getting a book using a null id")
    public void getBookByNullId() {
        Response response = booksApis.getBookById(nullId);
        response.then().assertThat()
            .statusCode(400);
    }

    @Test
    @Tag("edge")
    @DisplayName("Test getting a book using a maximum integer id")
    public void getBookByMaxIntegerId() {
        Response response = booksApis.getBookById(maxIntegerId);
        response.then().assertThat()
            .statusCode(404)
            .body("title", equalTo("Not Found"));
    }

    @Test
    @Tag("edge")
    @DisplayName("Test getting a book using a minimum integer id")
    public void getBookByMinIntegerId() {
        Response response = booksApis.getBookById(minIntegerId);
        response.then().assertThat()
            .statusCode(404)
            .body("title", equalTo("Not Found"));
    }

}
