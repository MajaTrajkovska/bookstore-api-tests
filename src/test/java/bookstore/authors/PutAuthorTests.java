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
 * Test class for API PUT author endpoint.
 * <p>
 * Contains positive, negative, and edge-case tests for updating authors via the API.
 * <ul>
 *   <li>Positive tests verify successful update with valid and some missing data.</li>
 *   <li>Negative tests cover invalid, missing, null, and edge-case IDs.</li>
 * </ul>
 * <p>
 * Comments above each test method describe the expected behavior and any known issues with the fake API.
 */
public class PutAuthorTests 
{
    private AuthorsApis authorsApi;
    int createdAuthorId;

    /**
     * Initializes the AuthorsApis instance before each test and creates a random author for update tests.
     */
    @BeforeEach
    public void setup() {
       authorsApi = new AuthorsApis();
     // Create a random author and store its ID for use in tests
        Author author = AuthorDataFactory.createBaseAuthor();
        Response createResponse = authorsApi.createAuthor(author);
        createdAuthorId = createResponse.then()
            .statusCode(200)
            .extract()
            .path("id");
    }

    /**
     * Positive test: Update an author with valid data.
     * Verifies that the API returns status code 200 and correct author data.
     */
    @Test
    @Tag("positive")
    @DisplayName("Update an author with valid data")
    public void updateAuthorWithValidData() {
        Author author = AuthorDataFactory.updateAuthorWithValidData();
        Response response = authorsApi.updateAuthor(createdAuthorId, author);
        response.then().assertThat()
            .statusCode(200)
            .body("id", equalTo(author.getId()))
            .body("firstName", equalTo(author.getFirstName()))
            .body("lastName",  equalTo(author.getLastName()));

        //delete the alredy created book
        authorsApi.deleteAuthor(createdAuthorId);  
    }

    /**
     * Positive test: Update author with empty first name.
     * Let's assume that the first name is not mandatory field. In this case, the API is returning 200OK.
     * Verifies that the API returns status code 200 and correct author data.
     */
    @Test
    @Tag("positive")
    @DisplayName("Update author with empty first name")
    public void updateAuthorWithEmptyFirstName() {
        Author author = AuthorDataFactory.updateAuthorWithEmptyFirstName();
        Response response = authorsApi.updateAuthor(createdAuthorId, author);
        response.then().assertThat()
            .statusCode(200)
            .body("id", equalTo(author.getId()))
            .body("firstName", equalTo(author.getFirstName()))
            .body("lastName",  equalTo(author.getLastName()));
        
        //delete the alredy created book
        authorsApi.deleteAuthor(createdAuthorId);               
    }

    /**
     * Positive test: Update author with empty last name.
     * Let's assume that the last name is not mandatory field. In this case, the API is returning 200OK.
     * Verifies that the API returns status code 200 and correct author data.
     */
    @Test
    @Tag("positive")
    @DisplayName("Update author with empty last name")
    public void updateAuthorWithEmptyLastName() {
        Author author = AuthorDataFactory.updateAuthorWithEmptyLastName();
        Response response = authorsApi.updateAuthor(createdAuthorId, author);
        response.then().assertThat()
            .statusCode(200)
            .body("id", equalTo(author.getId()))
            .body("firstName", equalTo(author.getFirstName()))
            .body("lastName",  equalTo(author.getLastName()));

        //delete the alredy created book
        authorsApi.deleteAuthor(createdAuthorId);
    }

    /**
     * Negative test: Update an author with null id.
     * Verifies that the API returns status code 400 and a validation error.
     */
    @Test
    @Tag("negative")
    @DisplayName("Update an author with null id")
    public void updateAuthorWithEmptyId() {
        Author author = AuthorDataFactory.updateAuthorWithNullId();
        Response response = authorsApi.updateAuthor(createdAuthorId, author);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }

    /**
     * Negative test: Update an author with empty book id.
     * We assume that the book ID is a mandatory field, so this test case is EXPECTED TO FAIL.
     * Verifies that the API returns status code 400 and a validation error.
     */
    @Test
    @Tag("negative")
    @DisplayName("Update an author with empty book id")
    public void updateAuthorWithEmptyBookId() {
        Author author = AuthorDataFactory.updateAuthorWithEmptyBookId();
        Response response = authorsApi.updateAuthor(createdAuthorId, author);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }

    /**
     * Negative test: Update an author with negative ID.
     * If we imagine that the ID must be higher than 0, then this test case is negative.
     * Leaded by this fact, this test case is EXPECTED TO FAIL!
     * Verifies that the API returns status code 400 and a validation error.
     */
    @Test
    @Tag("negative")
    @DisplayName("Update an author with negative ID")
    public void updateAuthorWithNegativeId() {
        Author author = AuthorDataFactory.updateAuthorWithNegativeId();
        Response response = authorsApi.updateAuthor(createdAuthorId, author);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }

}