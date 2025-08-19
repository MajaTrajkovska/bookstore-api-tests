package bookstore.authors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

import com.example.bookstoreapi.api.AuthorsApis;
import com.example.bookstoreapi.data.AuthorDataFactory;
import com.example.bookstoreapi.model.Author;

/**
 * API tests for DELETE author.
 */
public class DeleteAuthorTests 
{
    private AuthorsApis authorsApi;
    int createdAuthorId;
    

    private int nonExistingId = 777777; // Example of a non-existing ID. The valid ID range is between 1 and 590.
    private int idAboveMaxId = 605; // Example of a maximum ID that should not be valid.
    private String validIdWithExtraZeros = "0001";  //Example of a valid ID with few extra zeros upfront.
    private String alphaNumericInput = "abc123"; // Example of an alphanumeric input that should not be valid.
    private String specialSign = "*!"; // Example of a special sign input that should not be valid.
    private int negativeId = -1; // Example of a negative ID that should not be valid.
    private String nullId = null; // Example of a null ID that should not be valid.
    private int maxIntegerId = Integer.MAX_VALUE; // Example of a maximum integer ID that should not be valid.
    private int minIntegerId = Integer.MIN_VALUE; // Example of a minimum integer ID that should not be valid.

    
    @BeforeEach
    public void setup() {
       authorsApi = new AuthorsApis();
    }

    @Test
    @Tag("positive")
    @DisplayName("Delete a book with valid data")
    public void deleteBookValidData() {
        // Create a random author and store its ID for use in tests
        Author author = AuthorDataFactory.createBaseAuthor();
        Response createResponse = authorsApi.createAuthor(author);
        createdAuthorId = createResponse.then()
            .statusCode(200)
            .extract()
            .path("id");
        authorsApi.deleteAuthor(createdAuthorId).then().assertThat()
            .statusCode(200);  
    }

    @Test
    @Tag("positive")
    @DisplayName("Delete a book with ID with extra zeros upfront")
    public void deleteBookWithExtraZeros() {
        Response response = authorsApi.deleteAuthor(validIdWithExtraZeros);
        response.then().assertThat()
            .statusCode(200);
    }

    // There is no logic to be able to delete an author with non-existing ID, but the fake API is returning 200OK.
    // This test case is EXPECTED TO FAIL because the API should not allow deletion of an author that does not exist in the DB.
    @Test
    @Tag("negative")
    @DisplayName("Delete a book with non-existing ID")
    public void deleteBookWithNonExistingId() {
        Response response = authorsApi.deleteAuthor(nonExistingId);
        response.then().assertThat()
            .statusCode(404)
            .body("title", equalTo("One or more validation errors occurred."));
    }

    // There is no logic to be able to delete an author with ith ID above maximum (non-existing ID), but the fake API is returning 200OK.
    // This test case is EXPECTED TO FAIL because the API should not allow deletion of an author that does not exist in the DB.
    @Test
    @Tag("edge")
    @DisplayName("Delete a book with ID above maximum")
    public void deleteBookWithIdAboveMax() {
        Response response = authorsApi.deleteAuthor(idAboveMaxId);
        response.then().assertThat()
            .statusCode(404)
            .body("title", equalTo("Not Found"));
    }

    @Test
    @Tag("negative")
    @DisplayName("Delete a book with alphanumeric ID")
    public void deleteBookWithAlphaNumericId() {
        Response response = authorsApi.deleteAuthor(alphaNumericInput);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }

    @Test
    @Tag("negative")
    @DisplayName("Delete a book with special sign ID")
    public void deleteBookWithSpecialSignId() {
        Response response = authorsApi.deleteAuthor(specialSign);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }

    // There is no logic to be able to delete an author with negative ID, but the fake API is returning 200OK.
    // This test case is EXPECTED TO FAIL because the API should not allow deletion of an author that does not exist in the DB with a negative ID.
    @Test
    @Tag("negative")
    @DisplayName("Delete a book with negative ID")
    public void deleteBookWithNegativeId() {
        Response response = authorsApi.deleteAuthor(negativeId);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }

    @Test
    @Tag("negative")
    @DisplayName("Delete a book with null ID")
    public void deleteBookWithNullId() {
        Response response = authorsApi.deleteAuthor(nullId);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }

    // There is no logic to be able to delete an author with maximum integer ID when it does not exist at all, but the fake API is returning 200OK.
    // This test case is EXPECTED TO FAIL because the API should not allow deletion of an author that does not exist in the DB with that ID.
    @Test
    @Tag("edge")
    @DisplayName("Delete a book with maximum integer ID")
    public void deleteBookWithMaxIntegerId() {
        Response response = authorsApi.deleteAuthor(maxIntegerId);
        response.then().assertThat()
            .statusCode(404)
            .body("title", equalTo("Not Found"));
    }

    // There is no logic to be able to delete an author with minimum integer ID when it does not exist at all, but the fake API is returning 200OK.
    // This test case is EXPECTED TO FAIL because the API should not allow deletion of an author that does not exist in the DB with that ID.
    @Test
    @Tag("edge")
    @DisplayName("Delete a book with minimum integer ID")
    public void deleteBookWithMinIntegerId() {
        Response response = authorsApi.deleteAuthor(minIntegerId);
        response.then().assertThat()
            .statusCode(404)
            .body("title", equalTo("Not Found"));
    }

}