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
 * Test class for API POST author endpoint.
 * <p>
 * Contains positive, negative, and edge-case tests for creating authors via the API.
 * <ul>
 *   <li>Positive tests verify successful creation with valid and some missing data.</li>
 *   <li>Negative tests cover invalid, missing, null, and edge-case IDs.</li>
 * </ul>
 * <p>
 * Comments above each test method describe the expected behavior and any known issues with the fake API.
 */
public class PostAuthorTests 
{
    private AuthorsApis authorsApi;
    
    /**
     * Initializes the AuthorsApis instance before each test.
     */
    @BeforeEach
    public void setup() {
       authorsApi = new AuthorsApis();
    }

    /**
     * Positive test: Create a new author with valid data.
     * The validation of the bookID is skipped because the fake API always returns 0 for its id.
     * Because we do not want all the tests to be failed, we will not validate the bookId in these tests.
     * Verifies that the API returns status code 200 and correct author data.
     */
    @Test
    @Tag("positive")
    @DisplayName("Create a new author with valid data")
    public void createNewAuthor() {
        Author author = AuthorDataFactory.createAuthorWithFixedIds();
        Response response = authorsApi.createAuthor(author);
        response.then().assertThat()
            .statusCode(200)
            .body("id", equalTo(author.getId()))
            .body("firstName", equalTo(author.getFirstName()))
            .body("lastName",  equalTo(author.getLastName()));
        
        //delete the alredy created author
        authorsApi.deleteAuthor(author.getId()); 
    }

    /**
     * Negative test: Create a new author without ID.
     * Verifies that the API returns status code 400 and a validation error.
     */
    @Test
    @Tag("negative")
    @DisplayName("Create a new author without ID")
    public void createAuthorWithoutID() {
        Author author = AuthorDataFactory.createAuthorWithEmptyId();
        Response response = authorsApi.createAuthor(author);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }

    /**
     * Negative test: Create a new author without book ID.
     * This test case is EXPECTED TO FAIL because the API should not allow creating an author with bookId null, but it returns 200OK.
     * Verifies that the API returns status code 400 and a validation error.
     */
    @Test
    @Tag("negative")
    @DisplayName("Create a new author without book ID")
    public void createAuthorWithoutBookID() {
        Author author = AuthorDataFactory.createAuthorWithEmptyBookId();
        Response response = authorsApi.createAuthor(author);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }

    /**
     * Positive test: Create a new author without first name.
     * Let's assume that the first name is not mandatory field. In this case, the API is returning 200OK.
     * Verifies that the API returns status code 200 and correct author data.
     */
    @Test
    @Tag("positive")
    @DisplayName("Create a new author without first name")
    public void createAuthorWithEmptyFirstName() {
        Author author = AuthorDataFactory.createAuthorWithEmptyFirstName();
        Response response = authorsApi.createAuthor(author);
        response.then().assertThat()
            .statusCode(200)
            .body("id", equalTo(author.getId()))
            .body("firstName", equalTo(author.getFirstName()))
            .body("lastName",  equalTo(author.getLastName()));

             //delete the alredy created author
        authorsApi.deleteAuthor(author.getId());
            
    }

    /**
     * Positive test: Create a new author without last name.
     * Let's assume that the last name is not mandatory field. In this case, the API is returning 200OK.
     * Verifies that the API returns status code 200 and correct author data.
     */
    @Test
    @Tag("positive")
    @DisplayName("Create a new author without last name")
    public void createAuthorWithEmptyLastName() {
        Author author = AuthorDataFactory.createAuthorWithEmptyLastName();
        Response response = authorsApi.createAuthor(author);
        response.then().assertThat()
            .statusCode(200)
            .body("id", equalTo(author.getId()))
            .body("firstName", equalTo(author.getFirstName()))
            .body("lastName",  equalTo(author.getLastName()));

        //delete the alredy created author
        authorsApi.deleteAuthor(author.getId());
    }

    /**
     * Negative test: Create a new author with negative ID.
     * If we imagine that the ID must be higher than 0, then this test case is negative.
     * Leaded by this fact, this test case is EXPECTED TO FAIL!
     * Verifies that the API returns status code 400 and a validation error.
     */
    @Test
    @Tag("negative")
    @DisplayName("Create a new author with negative ID")
    public void createAuthorWithNegativeId() {
        Author author = AuthorDataFactory.createAuthorWithNegativeId();
        Response response = authorsApi.createAuthor(author);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }

    /**
     * Edge test: Create a new author with maximum integer ID.
     * Verifies that the API returns status code 200 and correct author data.
     */
    @Test
    @Tag("edge")
    @DisplayName("Create a new author with maximum integer ID")
    public void createAuthorWithAlphaNumericId() {
        Author author = AuthorDataFactory.createAuthorWithMaxIntegerId();
        Response response = authorsApi.createAuthor(author);
        response.then().assertThat()
            .statusCode(200)
            .body("id", equalTo(author.getId()))
            .body("firstName", equalTo(author.getFirstName()))
            .body("lastName",  equalTo(author.getLastName()));

        //delete the alredy created author
        authorsApi.deleteAuthor(author.getId());
    }

    /**
     * Edge test: Create a new author with minimum integer ID.
     * Verifies that the API returns status code 200 and correct author data.
     */
    @Test
    @Tag("edge")
    @DisplayName("Create a new author with minimum integer ID")
    public void createAuthorWithMinIntegerId() {
        Author author = AuthorDataFactory.createAuthorWithMinIntegerId();
        Response response = authorsApi.createAuthor(author);
        response.then().assertThat()
            .statusCode(200)
            .body("id", equalTo(author.getId()))
            .body("firstName", equalTo(author.getFirstName()))      
            .body("lastName",  equalTo(author.getLastName()));
            
        //delete the alredy created author
        authorsApi.deleteAuthor(author.getId());
    }

    /**
     * Negative test: Create a new author with null ID.
     * Verifies that the API returns status code 400 and a validation error.
     */
    @Test 
    @Tag("negative")
    @DisplayName("Create a new author with null ID")   
    public void createAuthorWithNullId() {
        Author author = AuthorDataFactory.createAuthorWithNullId();
        Response response = authorsApi.createAuthor(author);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));   
    }

}