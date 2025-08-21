package com.example.bookstoreapi.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import com.example.bookstoreapi.config.ConfigReader;
import io.qameta.allure.restassured.AllureRestAssured;

/**
 * {@code BooksApis} is a utility class that provides methods for interacting
 * with the Book-related endpoints of the Bookstore API.
 * <p>
 * Each method performs a REST operation and returns the raw
 * {@link Response} object from RestAssured. No assertions or validations
 * are performed here — those should be done in the test classes.
 * </p>
 */
public class BooksApis {

    private static final String BASE_PATH = "/api/v1/Books";

    // Static initializer to set base URI once
    static {
        baseURI = ConfigReader.get("base.url");
    }

    /**
     * Retrieves all books from the API.
     *
     * @return the {@link Response} containing the list of books
     */
    public Response getAllBooks() {
        return given()
            .filter(new AllureRestAssured())
            .accept(ContentType.JSON)
            .when()
            .get(BASE_PATH);
    }

    /**
     * Retrieves a book by its numeric ID.
     *
     * @param id the ID of the book
     * @return the {@link Response} containing the book details
     */
    public Response getBookById(Integer id) {
        return given()
            .filter(new AllureRestAssured())
            .accept(ContentType.JSON)
            .when()
            .get(BASE_PATH + "/" + id);
    }

    /**
     * Retrieves a book by its string-based ID.
     *
     * @param id the ID of the book (string format)
     * @return the {@link Response} containing the book details
     */
    public Response getBookById(String id) {
        return given()
            .filter(new AllureRestAssured())
            .accept(ContentType.JSON)
            .when()
            .get(BASE_PATH + "/" + id);
    }

    /**
     * Creates a new book in the system.
     *
     * @param book the book object (POJO or map) to create
     * @return the {@link Response} containing the created book
     */
    public Response createBook(Object book) {
        return given()
            .filter(new AllureRestAssured())
            .contentType(ContentType.JSON)
            .body(book)
            .when()
            .post(BASE_PATH);
    }

    /**
     * Updates an existing book by numeric ID.
     *
     * @param id   the ID of the book to update
     * @param book the updated book object
     * @return the {@link Response} containing the updated book
     */
    public Response updateBook(Integer id, Object book) {
        return given()
            .filter(new AllureRestAssured())
            .contentType(ContentType.JSON)
            .body(book)
            .when()
            .put(BASE_PATH + "/" + id);
    }

    /**
     * Deletes a book by numeric ID.
     *
     * @param id the ID of the book to delete
     * @return the {@link Response} from the delete request
     */
    public Response deleteBook(Integer id) {
        return given()
            .filter(new AllureRestAssured())
            .when()
            .delete(BASE_PATH + "/" + id);
    }

    /**
     * Deletes a book by string-based ID.
     *
     * @param id the ID of the book to delete (string format)
     * @return the {@link Response} from the delete request
     */
    public Response deleteBook(String id) {
        return given()
            .filter(new AllureRestAssured())
            .when()
            .delete(BASE_PATH + "/" + id);
    }

}
