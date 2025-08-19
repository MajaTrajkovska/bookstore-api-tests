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
 * API tests for GET authors.
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
    
    @BeforeEach
    public void setup() {
       authorsApi = new AuthorsApis();
    }

    @Test
    @Tag("positive")
    @DisplayName("Test getting all authors")
    public void getAllAuthors() {
        Response response = authorsApi.getAllAuthors();
        response.then().assertThat()
            .statusCode(200)
            .body("size()", greaterThan(0));
    }

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

    @Test
    @Tag("positive")
    @DisplayName("Test getting an author using an existing id but written with few more zeros upfront")
    public void getAuthorByExistingIdAndExtraZeros() {
        Response response = authorsApi.getAuthorById(validIdWithExtraZeros);
        response.then().assertThat()
            .statusCode(200);
    }

    @Test
    @Tag("negative")  
    @DisplayName("Test getting an author by a non-existing ID")
    public void getAuthorByNonExistingId() {   
        Response response = authorsApi.getAuthorById(nonExistingId);
        response.then().assertThat()
            .statusCode(404)
            .body("title", equalTo("Not Found"));   
    }

    @Test
    @Tag("edge")
    @DisplayName("Test getting an author by id that is above the maxID")
    public void getAuthorByIdAboveMaxId() {
        Response response = authorsApi.getAuthorById(idAboveMaxId);
        response.then().assertThat()
            .statusCode(404)
            .body("title", equalTo("Not Found"));
    }

    @Test
    @Tag("negative")
    @DisplayName("Test getting an author by alphanumeric ID")
    public void getAuthorByAlphaNumericId() {
        Response response = authorsApi.getAuthorById(alphaNumericInput);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }

    // If we imagine that the ID must be higher than 0, then this test case is negative.
    @Test
    @Tag("negative")
    @DisplayName("Test getting an author by negative ID")
    public void getAuthorByNegativeId() {
        Response response = authorsApi.getAuthorById(negativeId);
        response.then().assertThat()
            .statusCode(404)
            .body("title", equalTo("Not Found"));
    }

    @Test
    @Tag("negative")
    @DisplayName("Test getting an author by null ID")
    public void getAuthorByNullId() {
        Response response = authorsApi.getAuthorById(nullId);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }

    @Test
    @Tag("negative")
    @DisplayName("Test getting an author by special sign ID")
    public void getAuthorBySpecialSign() {
        Response response = authorsApi.getAuthorById(specialSign);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }

    @Test
    @Tag("edge")
    @DisplayName("Test getting an author by maximum integer ID")
    public void getAuthorByMaxIntegerId() {
        Response response = authorsApi.getAuthorById(maxIntegerId);
        response.then().assertThat()
            .statusCode(404)
            .body("title", equalTo("Not Found"));
    }

    @Test
    @Tag("edge")
    @DisplayName("Test getting an author by minimum integer ID")
    public void getAuthorByMinIntegerId() {
        Response response = authorsApi.getAuthorById(minIntegerId);
        response.then().assertThat()
            .statusCode(404)
            .body("title", equalTo("Not Found"));
    }

    @Test
    @Tag("positive")
    @DisplayName("Test getting an author by existing bookID")
    public void getAuthorByExistingBookId() {
        Response response = authorsApi.getAuthorByBookId(existingBookId);
        response.then().assertThat()
            .statusCode(200)
            .body("idBook", everyItem(equalTo(existingBookId))); // because the response is an array
    }

    @Test
    @Tag("positive")
    @DisplayName("Test getting an author by bookID with extra zeros upfront")
    public void getAuthorByBookIdWithExtraZeros() {
        Response response = authorsApi.getAuthorByBookId(bookIdWithExtraZeros);
        response.then().assertThat()
            .statusCode(200)
            .body("idBook", everyItem(equalTo(bookIdWithExtraZeros)));
    }

    // Negative test case for getting a book with non-existing book ID
    // This test case is EXPECTED TO FAIL because the API should not get back a sttaus code 200. It is exepected to be 404.
    @Test
    @Tag("negative")
    @DisplayName("Test getting an author by non-existing bookID")
    public void getAuthorByNonExistingBookId() {
        Response response = authorsApi.getAuthorByBookId(nonExistingBookId);
        response.then().assertThat()
            .statusCode(404)
            .body("idBook", everyItem(equalTo("[]")));
    }

    // This test case is EXPECTED TO FAIL because the API should not get back a sttaus code 200. It is exepected to be 404.
    @Test
    @Tag("edge")
    @DisplayName("Test getting an author by bookID above max ID")
    public void getAuthorByBookIdAboveMaxId() {
        Response response = authorsApi.getAuthorByBookId(bookIdAboveMaxId);
        response.then().assertThat()
            .statusCode(404)
            .body("idBook", everyItem(equalTo("[]")));
    }

     // This test case is EXPECTED TO FAIL because the API should not get back a sttaus code 200. It is exepected to be 404.
    @Test
    @Tag("edge")
    @DisplayName("Test getting an author by bookID with zero")
    public void getAuthorByBookIdWithZero() {
        Response response = authorsApi.getAuthorByBookId(bookIdWithZero);
        response.then().assertThat()
            .statusCode(404)
            .body("idBook", everyItem(equalTo("[]")));
    }

    // This test case is EXPECTED TO FAIL because the API should not get back a sttaus code 200. It is exepected to be 404.
    @Test
    @Tag("negative")
    @DisplayName("Test getting an author by bookID with negative value")
    public void getAuthorByBookIdWithNegativeValue() {  
        Response response = authorsApi.getAuthorByBookId(bookIdWithNegativeValue);
        response.then().assertThat()
            .statusCode(404)
            .body("idBook", everyItem(equalTo("[]")));
    }

    
    @Test
    @Tag("negative")
    @DisplayName("Test getting an author by bookID with null value")
    public void getAuthorByBookIdWithNullValue() {
        Response response = authorsApi.getAuthorByBookId(bookIdWithNullValue);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }


    @Test
    @Tag("negative")
    @DisplayName("Test getting an author by alphanumeric bookID")
    public void getAuthorByAlphaNumericBookId() {
        Response response = authorsApi.getAuthorByBookId(alphaNumericInput);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }

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