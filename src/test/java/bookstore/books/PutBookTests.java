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
 * Test class for API PUT book endpoint.
 * <p>
 * Contains positive, negative, and edge-case tests for updating books via the API.
 * <ul>
 *   <li>Positive tests verify successful update with valid and some missing data.</li>
 *   <li>Negative tests cover invalid, missing, null, and edge-case fields.</li>
 * </ul>
 * <p>
 * Comments above each test method describe the expected behavior and any known issues with the fake API.
 */
public class PutBookTests 
{
    private BooksApis booksApis;
    int createdBookId;
    
    /**
     * Initializes the BooksApis instance before each test and creates a random book for update tests.
     */
    @BeforeEach
    public void setup() {
       booksApis = new BooksApis();
     // Create a random book and store its ID for use in tests
        Book book = BookDataFactory.createBaseBook();
        Response createResponse = booksApis.createBook(book);
        createdBookId = createResponse.then()
            .statusCode(200)
            .extract()
            .path("id");
    }

    /**
     * Positive test: Update a book with valid data.
     * Verifies that the API returns status code 200 and correct book data.
     */
    @Test
    @Tag("positive")
    @DisplayName("Update a book with valid data")
    public void updateBookWithValidData() {
        Book book = BookDataFactory.updateBookWithValidData();
        Response response = booksApis.updateBook(createdBookId, book);
        response.then().assertThat()
            .statusCode(200)
            .body("title", equalTo(book.getTitle()))
            .body("pageCount", equalTo(book.getPageCount()))
            .body("description", equalTo(book.getDescription()))
            .body("excerpt",  equalTo(book.getExcerpt()));

        //delete the alredy created book
        booksApis.deleteBook(createdBookId);  
    }

    /**
     * Positive test: Update book with empty description.
     * Description is not a required field in this fake API, so lets assume that is OK to have a book without description.
     * Verifies that the API returns status code 200 and correct book data.
     */
    @Test
    @Tag("positive")
    @DisplayName("Update book with empty description")
    public void updateBookWithEmptyDescription() {
        Book book = BookDataFactory.updateBookWithEmptyDescription();
        Response response = booksApis.updateBook(createdBookId, book);
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
     * Positive test: Update book with empty excerpt.
     * Excerpt is not a required field in this fake API, so lets assume that is OK to have a book without excerpt.
     * Verifies that the API returns status code 200 and correct book data.
     */
    @Test
    @Tag("positive")
    @DisplayName("Update book with empty excerpt")
    public void updateBookWithoutExcerpt() {
        Book book = BookDataFactory.updateBookWithEmptyExcerpt();
        Response response = booksApis.updateBook(createdBookId, book);
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
     * Positive test: Update a book with empty title.
     * There is no logic to be able to have a book without title, but the fake API is returning 200OK.
     * The title should be a mandatory field as the data and the page number.
     * This test case is EXPECTED TO FAIL because the API should not allow a book without title.
     * Verifies that the API returns status code 400.
     */
    @Test
    @Tag("positive")
    @DisplayName("Update a book with empty title")
    public void updateBookWithoutTitle() {
        Book book = BookDataFactory.updateBookWithEmptyTitle();
        Response response = booksApis.updateBook(createdBookId, book);
        response.then().assertThat()
            .statusCode(400);
    }

    /**
     * Negative test: Update a book with null id.
     * The PUT API allows to update the id of the book as well.
     * Verifies that the API returns status code 400 and a validation error.
     */
    @Test
    @Tag("negative")
    @DisplayName("Update a book with null id")
    public void updateBookWithEmptyId() {
        Book book = BookDataFactory.updateBookWithNullId();
        Response response = booksApis.updateBook(createdBookId, book);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }

    /**
     * Negative test: Update a book with negative page count.
     * Negative test case for updating a book with negative page count.
     * This test case is EXPECTED TO FAIL because the API should not allow negative page counts.
     * Verifies that the API returns status code 400.
     */
    @Test
    @Tag("negative")
    @DisplayName("Update a book with negative page count")
    public void updateBookWithNegativePageCount() {
        Book book = BookDataFactory.updateBookWithNegativePageCount();
        Response response = booksApis.updateBook(createdBookId, book);
        response.then().assertThat()
            .statusCode(400);
    }

    /**
     * Negative test: Update a book with null as page number.
     * Verifies that the API returns status code 400 and a validation error.
     */
    @Test
    @Tag("negative")
    @DisplayName("Update a book with null as page number")
    public void updateBookWithNullPageNumber() {
        Book book = BookDataFactory.updateBookWithNullPages();
        Response response = booksApis.updateBook(createdBookId, book);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }

    /**
     * Negative test: Update a book with invalid date.
     * Verifies that the API returns status code 400 and a validation error.
     */
    @Test
    @Tag("negative")
    @DisplayName("Update a book with invalid date")
    public void updateBookWithInvalidDate() {
        Book book = BookDataFactory.updateBookWithInvalidDate();
        Response response = booksApis.updateBook(createdBookId, book);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }

    /**
     * Negative test: Update a book with null date.
     * Verifies that the API returns status code 400 and a validation error.
     */
    @Test
    @Tag("negative")
    @DisplayName("Update a book with null date")
    public void updateBookWithNullDate() {
        Book book = BookDataFactory.updateBookWithNullDate();
        Response response = booksApis.updateBook(createdBookId, book);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }
}