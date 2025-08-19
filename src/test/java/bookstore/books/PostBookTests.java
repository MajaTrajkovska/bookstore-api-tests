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
 * API tests for POST books.
 */
public class PostBookTests 
{
    private BooksApis booksApis;
    
    @BeforeEach
    public void setup() {
       booksApis = new BooksApis();
    }

    @Test
    @Tag("positive")
    @DisplayName("Create a new book with valid data")

    public void createNewBook() {
        Book book = BookDataFactory.createBaseBook();
        Response response = booksApis.createBook(book);
    response.then().assertThat()
            .statusCode(200)
            .body("title", equalTo(book.getTitle()))
            .body("pageCount", equalTo(book.getPageCount()))
            .body("description", equalTo(book.getDescription()))
            .body("excerpt",  equalTo(book.getExcerpt()))
            .body("publishDate", equalTo(book.getPublishDate()));

        //delete the alredy created book
        booksApis.deleteBook(book.getId());  
    }

    // Description is not a required field in this fake API, so lets assume that is OK to have a book without description.
    @Test
    @Tag("positive")
    @DisplayName("Create a new book without description")
    public void createBookWithoutDescription() {
        Book book = BookDataFactory.createBookWithoutDescription();
        Response response = booksApis.createBook(book);
        response.then().assertThat()
            .statusCode(200)
            .body("title", equalTo(book.getTitle()))
            .body("pageCount", equalTo(book.getPageCount()))
            .body("description", equalTo(book.getDescription()))
            .body("excerpt",  equalTo(book.getExcerpt()))
            .body("publishDate", equalTo(book.getPublishDate()));

        //delete the alredy created book
        booksApis.deleteBook(book.getId());  
    }

    // Excerpt is not a required field in this fake API, so lets assume that is OK to have a book without excerpt.
    @Test
    @Tag("positive")
    @DisplayName("Create a new book without excerpt")
    public void createBookWithoutExcerpt() {
        Book book = BookDataFactory.createBookWithoutExcerpt();
        Response response = booksApis.createBook(book);
        response.then().assertThat()
            .statusCode(200)
            .body("title", equalTo(book.getTitle()))
            .body("pageCount", equalTo(book.getPageCount()))
            .body("description", equalTo(book.getDescription()))
            .body("excerpt",  equalTo(book.getExcerpt()))
            .body("publishDate", equalTo(book.getPublishDate()));

        //delete the alredy created book
        booksApis.deleteBook(book.getId());  
    }

    // There is no logic to be able to create a book without title, but the fake API is returning 200OK.
    // The title should be a mandatory field as the data and  the page number.
    // This test case is EXPECTED TO FAIL because the API should not allow a book without title.
    @Test
    @Tag("positive")
    @DisplayName("Create a new book without title")
    public void createBookWithoutTitle() {
        Book book = BookDataFactory.createBookWithoutTitle();
        Response response = booksApis.createBook(book);
        response.then().assertThat()
            .statusCode(400);
    }

    @Test
    @Tag("negative")
    @DisplayName("Create a new book without id")
    public void createBookWithoutId() {
        Book book = BookDataFactory.createBookWithoutId();
        Response response = booksApis.createBook(book);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }

    // Negative test case for creating a book with negative page count
    // This test case is EXPECTED TO FAIL because the API should not allow negative page counts.
    @Test
    @Tag("negative")
    @DisplayName("Create a new book with negative page count")
    public void createBookWithNegativePageCount() {
        Book book = BookDataFactory.createBookWithNegativePageCount();
        Response response = booksApis.createBook(book);
        response.then().assertThat()
            .statusCode(400);
    }

    
    @Test
    @Tag("negative")
    @DisplayName("Create a new book with null as page number")
    public void createBookWithNullPageNumber() {
        Book book = BookDataFactory.createBookWithNullPages();
        Response response = booksApis.createBook(book);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }

    @Test
    @Tag("negative")
    @DisplayName("Create a new book with invalid date")
    public void createBookWithInvalidDate() {
        Book book = BookDataFactory.createBookWithInvalidDate();
        Response response = booksApis.createBook(book);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }

    @Test
    @Tag("negative")
    @DisplayName("Create a new book with null date")
    public void createBookWithNullDate() {
        Book book = BookDataFactory.createBookWithNullDate();
        Response response = booksApis.createBook(book);
        response.then().assertThat()
            .statusCode(400)
            .body("title", equalTo("One or more validation errors occurred."));
    }
}
