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
 * Test class for API DELETE book endpoint.
 * <p>
 * Contains positive, negative, and edge-case tests for deleting books via the API.
 * <ul>
 *   <li>Positive tests verify successful deletion with valid data and formats.</li>
 *   <li>Negative tests cover invalid, non-existing, null, and malformed IDs.</li>
 *   <li>Edge tests cover boundary values such as maximum and minimum integer IDs.</li>
 * </ul>
 * <p>
 * Comments above each test method describe the expected behavior and any known issues with the fake API.
 */
public class DeleteBookTests 
{

    private int nonExistingId = 88888888; // Example of a non-existing ID. The valid ID range is between 1 and 200.
    private int validIdWithExtraZeros = 0001;  //Example of a valid ID with few extra zeros upfront.
    private String alphaNumericInput = "abc123"; // Example of an alphanumeric input that should not be valid.
    private String specialSign = "*!"; // Example of a special sign input that should not be valid.
    private int negativeId = -1; // Example of a negative ID that should not be valid.
    private String nullId = null; // Example of a null ID that should not be valid.
    private int maxIntegerId = Integer.MAX_VALUE; // Example of a maximum integer ID that should not be valid.
    private int minIntegerId = Integer.MIN_VALUE; // Example of a minimum integer ID that should not be valid.

    private BooksApis booksApis;
    int createdBookId;
    
    /**
     * Initializes the BooksApis instance before each test.
     */
    @BeforeEach
    public void setup() {
       booksApis = new BooksApis();
    }

    /**
     * Positive test: Delete a book with valid data.
     * Creates a random book, deletes it, and expects a 200 status code.
     */
    @Test
    @Tag("positive")
    @DisplayName("Delete a book with valid data")
    public void deleteBookValidData() {
        // Create a random book and store its ID for use in tests
        Book book = BookDataFactory.createBaseBook();
        Response createResponse = booksApis.createBook(book);
        createdBookId = createResponse.then()
            .statusCode(200)
            .extract()
            .path("id");
        booksApis.deleteBook(createdBookId).then().assertThat()
            .statusCode(200);  
    }

    /**
     * Positive test: Delete a book with ID with extra zeros upfront.
     * Expects a 200 status code.
     */
    @Test
    @Tag("positive")
    @DisplayName("Delete a book with ID with extra zeros upfront")
    public void deleteBookWithExtraZeros() {
        Response response = booksApis.deleteBook(validIdWithExtraZeros);
        response.then().assertThat()
            .statusCode(200);
    }

    /**
     * Negative test: Delete a book with non-existing ID.
     * There is no logic to be able to delete a book with non-existing ID, but the fake API is returning 200OK.
     * This test case is EXPECTED TO FAIL because the API should not allow deletion of a book that does not exist in the DB.
     */
    @Test
    @Tag("negative")
    @DisplayName("Delete a book with non-existing ID")
    public void deleteBookWithNonExistingId() {
        Response response = booksApis.deleteBook(nonExistingId);
        response.then().assertThat()
            .statusCode(404)
            .body("title", equalTo("One or more validation errors occurred."));
    }

    /**
     * Negative test: Delete a book with alphanumeric ID.
     * Expects a 400 status code and validation error.
     */
    @Test
    @Tag("negative")
    @DisplayName("Delete a book with alphanumeric ID")
    public void deleteBookWithAlphaNumericId() {
        Response response = booksApis.deleteBook(alphaNumericInput);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }

    /**
     * Negative test: Delete a book with special sign ID.
     * Expects a 400 status code and validation error.
     */
    @Test
    @Tag("negative")
    @DisplayName("Delete a book with special sign ID")
    public void deleteBookWithSpecialSignId() {
        Response response = booksApis.deleteBook(specialSign);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }

    /**
     * Negative test: Delete a book with negative ID.
     * There is no logic to be able to delete a book with negative ID, but the fake API is returning 200OK.
     * This test case is EXPECTED TO FAIL because the API should not allow deletion of a book that does not exist in the DB with a negative ID.
     */
    @Test
    @Tag("negative")
    @DisplayName("Delete a book with negative ID")
    public void deleteBookWithNegativeId() {
        Response response = booksApis.deleteBook(negativeId);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }

    /**
     * Negative test: Delete a book with null ID.
     * Expects a 400 status code and validation error.
     */
    @Test
    @Tag("negative")
    @DisplayName("Delete a book with null ID")
    public void deleteBookWithNullId() {
        Response response = booksApis.deleteBook(nullId);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }

    /**
     * Edge test: Delete a book with maximum integer ID.
     * There is no logic to be able to delete a book with maxInteger ID when it does not exist at all, but the fake API is returning 200OK.
     * This test case is EXPECTED TO FAIL because the API should not allow deletion of a book that does not exist in the DB with that ID.
     */
    @Test
    @Tag("edge")
    @DisplayName("Delete a book with maximum integer ID")
    public void deleteBookWithMaxIntegerId() {
        Response response = booksApis.deleteBook(maxIntegerId);
        response.then().assertThat()
            .statusCode(404)
            .body("title", equalTo("Not Found"));
    }

    /**
     * Edge test: Delete a book with minimum integer ID.
     * There is no logic to be able to delete a book with minInteger ID when it does not exist at all, but the fake API is returning 200OK.
     * This test case is EXPECTED TO FAIL because the API should not allow deletion of a book that does not exist in the DB with that ID.
     */
    @Test
    @Tag("edge")
    @DisplayName("Delete a book with minimum integer ID")
    public void deleteBookWithMinIntegerId() {
        Response response = booksApis.deleteBook(minIntegerId);
        response.then().assertThat()
            .statusCode(404)
            .body("title", equalTo("Not Found"));
    }

}