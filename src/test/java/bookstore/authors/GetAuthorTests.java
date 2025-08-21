package bookstore.authors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

import com.example.bookstoreapi.api.AuthorsApis;
import com.example.bookstoreapi.data.AuthorDataFactory;
import com.example.bookstoreapi.model.Author;

/**
 * Test class for API GET author endpoint.
 * <p>
 * Contains positive, negative, and edge-case tests for retrieving authors via the API.
 * <ul>
 *   <li>Positive tests verify successful retrieval with valid data and formats.</li>
 *   <li>Negative tests cover invalid, non-existing, null, and malformed IDs and book IDs.</li>
 *   <li>Edge tests cover boundary values such as maximum and minimum integer IDs and book IDs.</li>
 * </ul>
 * <p>
 * Comments above each test method describe the expected behavior and any known issues with the fake API.
 */
public class GetAuthorTests 
{
    private AuthorsApis authorsApi;
    
    private int nonExistingId = 777777; // Example of a non-existing ID. The valid ID range is between 1 and 590.
    private int idAboveMaxId = 605; // Example of a maximum ID that should not be valid.
    private int validIdWithExtraZeros = 0001;  //Example of a valid ID with few extra zeros upfront.
    private String alphaNumericInput = "abc123"; // Example of an alphanumeric input that should not be valid.
    private String specialSign = "*!"; // Example of a special sign input that should not be valid.
    private int negativeId = -1; // Example of a negative ID that should not be valid.
    private String nullId = null; // Example of a null ID that should not be valid.
    private int maxIntegerId = Integer.MAX_VALUE; // Example of a maximum integer ID that should not be valid.
    private int minIntegerId = Integer.MIN_VALUE; // Example of a minimum integer ID that should not be valid.
    private int existingBookId = ThreadLocalRandom.current().nextInt(1, 200); // Example of an existing book ID that should return authors.
    private int bookIdWithExtraZeros = 0001; // Example of a book ID with extra zeros upfront that should return authors.
    private int nonExistingBookId = 999999; // Example of a non-existing book ID that should not return authors.
    private int bookIdAboveMaxId = 201; // Example of a book ID that is above the maximum range and should not return authors.
    private int bookIdWithZero = 0; // Example of a book ID with zero that should not return authors.
    private int bookIdWithNegativeValue = -1; // Example of a book ID with negative value that should not return authors.
    private Integer bookIdWithNullValue = null; // Example of a book ID with null value that should not return authors.
    
    /**
     * Initializes the AuthorsApis instance before each test.
     */
    @BeforeEach
    public void setup() {
       authorsApi = new AuthorsApis();
    }

    /**
     * Positive test: Get all authors.
     * Verifies that the API returns a list of authors with status code 200 and a non-empty response.
     */
    @Test
    @Tag("positive")
    @DisplayName("Test getting all authors")
    public void getAllAuthors() {
        Response response = authorsApi.getAllAuthors();
        response.then().assertThat()
            .statusCode(200)
            .body("size()", greaterThan(0));
    }

    /**
     * Positive test: Get an author by existing ID.
     * Creates a random author, retrieves it by ID, and verifies the response status code and returned ID.
     */
    @Test
    @Tag("positive")
    @DisplayName("Test getting an author by existing ID")
    public void getAuthorByExistingId() {
        // Create a random author and store its ID for use in test
        int createdAuthorId;
        Author author = AuthorDataFactory.createBaseAuthor();
        Response createResponse = authorsApi.createAuthor(author);
        createdAuthorId = createResponse.then()
            .statusCode(200)
            .extract()
            .path("id");

        Response response = authorsApi.getAuthorById(createdAuthorId);
        response.then().assertThat()
            .statusCode(200)
            .body("id", equalTo(createdAuthorId));
    }

    /**
     * Positive test: Get an author by existing ID with extra zeros upfront.
     * Verifies that the API returns status code 200 for a valid ID with leading zeros.
     */
    @Test
    @Tag("positive")
    @DisplayName("Test getting an author using an existing id but written with few more zeros upfront")
    public void getAuthorByExistingIdAndExtraZeros() {
        Response response = authorsApi.getAuthorById(validIdWithExtraZeros);
        response.then().assertThat()
            .statusCode(200);
    }

    /**
     * Negative test: Get an author by non-existing ID.
     * Verifies that the API returns status code 404 and "Not Found" for a non-existing author ID.
     */
    @Test
    @Tag("negative")  
    @DisplayName("Test getting an author by a non-existing ID")
    public void getAuthorByNonExistingId() {   
        Response response = authorsApi.getAuthorById(nonExistingId);
        response.then().assertThat()
            .statusCode(404)
            .body("title", equalTo("Not Found"));   
    }

    /**
     * Edge test: Get an author by ID above the maximum allowed.
     * This test case is EXPECTED TO FAIL because the API should not get back a status code 200.
     * Verifies that the API returns status code 404 and "Not Found" for an ID above the valid range.
     */
    @Test
    @Tag("edge")
    @DisplayName("Test getting an author by id that is above the maxID")
    public void getAuthorByIdAboveMaxId() {
        Response response = authorsApi.getAuthorById(idAboveMaxId);
        response.then().assertThat()
            .statusCode(404)
            .body("title", equalTo("Not Found"));
    }

    /**
     * Negative test: Get an author by alphanumeric ID.
     * Verifies that the API returns status code 400 and a validation error for an alphanumeric ID.
     */
    @Test
    @Tag("negative")
    @DisplayName("Test getting an author by alphanumeric ID")
    public void getAuthorByAlphaNumericId() {
        Response response = authorsApi.getAuthorById(alphaNumericInput);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }

    /**
     * Negative test: Get an author by negative ID.
     * Verifies that the API returns status code 404 and "Not Found" for a negative ID.
     */
    @Test
    @Tag("negative")
    @DisplayName("Test getting an author by negative ID")
    public void getAuthorByNegativeId() {
        Response response = authorsApi.getAuthorById(negativeId);
        response.then().assertThat()
            .statusCode(404)
            .body("title", equalTo("Not Found"));
    }

    /**
     * Negative test: Get an author by null ID.
     * Verifies that the API returns status code 400 and a validation error for a null ID.
     */
    @Test
    @Tag("negative")
    @DisplayName("Test getting an author by null ID")
    public void getAuthorByNullId() {
        Response response = authorsApi.getAuthorById(nullId);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }

    /**
     * Negative test: Get an author by special sign ID.
     * Verifies that the API returns status code 400 and a validation error for a special sign ID.
     */
    @Test
    @Tag("negative")
    @DisplayName("Test getting an author by special sign ID")
    public void getAuthorBySpecialSign() {
        Response response = authorsApi.getAuthorById(specialSign);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }

    /**
     * Edge test: Get an author by maximum integer ID.
     * Verifies that the API returns status code 404 and "Not Found" for the maximum integer ID.
     */
    @Test
    @Tag("edge")
    @DisplayName("Test getting an author by maximum integer ID")
    public void getAuthorByMaxIntegerId() {
        Response response = authorsApi.getAuthorById(maxIntegerId);
        response.then().assertThat()
            .statusCode(404)
            .body("title", equalTo("Not Found"));
    }

    /**
     * Edge test: Get an author by minimum integer ID.
     * Verifies that the API returns status code 404 and "Not Found" for the minimum integer ID.
     */
    @Test
    @Tag("edge")
    @DisplayName("Test getting an author by minimum integer ID")
    public void getAuthorByMinIntegerId() {
        Response response = authorsApi.getAuthorById(minIntegerId);
        response.then().assertThat()
            .statusCode(404)
            .body("title", equalTo("Not Found"));
    }

    /**
     * Positive test: Get authors by existing book ID.
     * Verifies that the API returns status code 200 and all returned authors have the correct book ID.
     */
    @Test
    @Tag("positive")
    @DisplayName("Test getting an author by existing bookID")
    public void getAuthorByExistingBookId() {
        Response response = authorsApi.getAuthorByBookId(existingBookId);
        response.then().assertThat()
            .statusCode(200)
            .body("idBook", everyItem(equalTo(existingBookId))); // because the response is an array
    }

    /**
     * Positive test: Get authors by book ID with extra zeros upfront.
     * Verifies that the API returns status code 200 and all returned authors have the correct book ID.
     */
    @Test
    @Tag("positive")
    @DisplayName("Test getting an author by bookID with extra zeros upfront")
    public void getAuthorByBookIdWithExtraZeros() {
        Response response = authorsApi.getAuthorByBookId(bookIdWithExtraZeros);
        response.then().assertThat()
            .statusCode(200)
            .body("idBook", everyItem(equalTo(bookIdWithExtraZeros)));
    }

    /**
     * Negative test: Get authors by non-existing book ID.
     * This test case is EXPECTED TO FAIL because the API should not get back a status code 200. It is expected to be 404.
     * Verifies that the API returns status code 404 and an empty array for non-existing book ID.
     */
    @Test
    @Tag("negative")
    @DisplayName("Test getting an author by non-existing bookID")
    public void getAuthorByNonExistingBookId() {
        Response response = authorsApi.getAuthorByBookId(nonExistingBookId);
        response.then().assertThat()
            .statusCode(404)
            .body("idBook", everyItem(equalTo("[]")));
    }

    /**
     * Edge test: Get authors by book ID above max ID.
     * This test case is EXPECTED TO FAIL because the API should not get back a status code 200. It is expected to be 404.
     * Verifies that the API returns status code 404 and an empty array for book ID above the valid range.
     */
    @Test
    @Tag("edge")
    @DisplayName("Test getting an author by bookID above max ID")
    public void getAuthorByBookIdAboveMaxId() {
        Response response = authorsApi.getAuthorByBookId(bookIdAboveMaxId);
        response.then().assertThat()
            .statusCode(404)
            .body("idBook", everyItem(equalTo("[]")));
    }

    /**
     * Edge test: Get authors by book ID with zero.
     * This test case is EXPECTED TO FAIL because the API should not get back a status code 200. It is expected to be 404.
     * Verifies that the API returns status code 404 and an empty array for book ID zero.
     */
    @Test
    @Tag("edge")
    @DisplayName("Test getting an author by bookID with zero")
    public void getAuthorByBookIdWithZero() {
        Response response = authorsApi.getAuthorByBookId(bookIdWithZero);
        response.then().assertThat()
            .statusCode(404)
            .body("idBook", everyItem(equalTo("[]")));
    }

    /**
     * Negative test: Get authors by book ID with negative value.
     * This test case is EXPECTED TO FAIL because the API should not get back a status code 200. It is expected to be 404.
     * Verifies that the API returns status code 404 and an empty array for negative book ID.
     */
    @Test
    @Tag("negative")
    @DisplayName("Test getting an author by bookID with negative value")
    public void getAuthorByBookIdWithNegativeValue() {  
        Response response = authorsApi.getAuthorByBookId(bookIdWithNegativeValue);
        response.then().assertThat()
            .statusCode(404)
            .body("idBook", everyItem(equalTo("[]")));
    }

    /**
     * Negative test: Get authors by book ID with null value.
     * Verifies that the API returns status code 400 and a validation error for null book ID.
     */
    @Test
    @Tag("negative")
    @DisplayName("Test getting an author by bookID with null value")
    public void getAuthorByBookIdWithNullValue() {
        Response response = authorsApi.getAuthorByBookId(bookIdWithNullValue);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }

    /**
     * Negative test: Get authors by alphanumeric book ID.
     * Verifies that the API returns status code 400 and a validation error for alphanumeric book ID.
     */
    @Test
    @Tag("negative")
    @DisplayName("Test getting an author by alphanumeric bookID")
    public void getAuthorByAlphaNumericBookId() {
        Response response = authorsApi.getAuthorByBookId(alphaNumericInput);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }

    /**
     * Negative test: Get authors by special sign book ID.
     * Verifies that the API returns status code 400 and a validation error for special sign book ID.
     */
    @Test
    @Tag("negative")  
    @DisplayName("Test getting an author by special sign bookID")
    public void getAuthorBySpecialSignBookId() {
        Response response = authorsApi.getAuthorByBookId(specialSign);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }

}
