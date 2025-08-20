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
 * API tests for PUT books.
 */
public class PutBookTests 
{
    private BooksApis booksApis;
    int createdBookId;
    
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
            .body("excerpt",  equalTo(book.getExcerpt()))
            .body("publishDate", containsString(book.getPublishDate().substring(0, 19)));

        //delete the alredy created book
        booksApis.deleteBook(createdBookId);  
    }

    // Description is not a required field in this fake API, so lets assume that is OK to have a book without description.
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
            .body("excerpt",  equalTo(book.getExcerpt()))
            .body("publishDate", containsString(book.getPublishDate().substring(0, 19)));

        //delete the alredy created book
        booksApis.deleteBook(book.getId());  
    }

    // Excerpt is not a required field in this fake API, so lets assume that is OK to have a book without excerpt.
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
            .body("excerpt",  equalTo(book.getExcerpt()))
            .body("publishDate", containsString(book.getPublishDate().substring(0, 19)));

        //delete the alredy created book
        booksApis.deleteBook(book.getId());  
    }

    // There is no logic to be able to have a book without title, but the fake API is returning 200OK.
    // The title should be a mandatory field as the data and the page number.
    // This test case is EXPECTED TO FAIL because the API should not allow a book without title.
    @Test
    @Tag("positive")
    @DisplayName("Update a book with empty title")
    public void updateBookWithoutTitle() {
        Book book = BookDataFactory.updateBookWithEmptyTitle();
        Response response = booksApis.updateBook(createdBookId, book);
        response.then().assertThat()
            .statusCode(400);
    }

    // The PUT API allows to update the id of the book as well
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

    // Negative test case for updateing a book with negative page count
    // This test case is EXPECTED TO FAIL because the API should not allow negative page counts.
    @Test
    @Tag("negative")
    @DisplayName("Update a book with negative page count")
    public void updateBookWithNegativePageCount() {
        Book book = BookDataFactory.updateBookWithNegativePageCount();
        Response response = booksApis.updateBook(createdBookId, book);
        response.then().assertThat()
            .statusCode(400);
    }

    
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

