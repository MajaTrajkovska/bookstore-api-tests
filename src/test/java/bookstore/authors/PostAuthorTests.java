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
 * API tests for POST books.
 */
public class PostAuthorTests 
{
    private AuthorsApis authorsApi;
    
    @BeforeEach
    public void setup() {
       authorsApi = new AuthorsApis();
    }

    // The validation of the bookID is skipped because the fake API alway return 0 for its id.
    // Becase we do not want all the tests to be failed, we will not validate the bookId in these tests.
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

    // This test case is EXPECTED TO FAIL because the API should not allow creating an author wuth bookId null, but it returns 200OK.
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

    // Let's assume that the first name is not mandatory field. In this case, the API is returning 200OK.
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

    // Let's assume that the first name is not mandatory field. In this case, the API is returning 200OK.
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

    // If we imagine that the ID must be higher than 0, then this test case is negative.
    // Leaded by this fact, this test case is EXPECTED TO FAIL!
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
