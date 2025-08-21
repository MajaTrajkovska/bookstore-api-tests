package com.example.bookstoreapi.api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import com.example.bookstoreapi.config.ConfigReader;

/**
 * {@code AuthorsApis} provides methods for interacting
 * with the Author-related endpoints of the Bookstore API.
 * <p>
 * Each method executes a REST request and returns the raw
 * {@link Response} object from RestAssured. No assertions
 * or validations are performed here — that responsibility
 * belongs to the test layer.
 * </p>
 */
public class AuthorsApis {

    private static final String BASE_PATH = "/api/v1/Authors";

    // Static initializer to set base URI once
    static {
        baseURI = ConfigReader.get("base.url");
    }

    /**
     * Retrieves all authors from the API.
     *
     * @return the {@link Response} containing the list of authors
     */
    public Response getAllAuthors() {
        return given()
            .filter(new AllureRestAssured())
            .accept(ContentType.JSON)
            .when()
            .get(BASE_PATH);
    }

    /**
     * Retrieves an author by its numeric ID.
     *
     * @param id the ID of the author
     * @return the {@link Response} containing the author details
     */
    public Response getAuthorById(Integer id) {
        return given()
            .filter(new AllureRestAssured())
            .accept(ContentType.JSON)
            .when()
            .get(BASE_PATH + "/" + id);
    }

    /**
     * Retrieves an author by its string-based ID.
     *
     * @param id the ID of the author (string format)
     * @return the {@link Response} containing the author details
     */
    public Response getAuthorById(String id) {
        return given()
            .filter(new AllureRestAssured())
            .accept(ContentType.JSON)
            .when()
            .get(BASE_PATH + "/" + id);
    }

    /**
     * Retrieves an author by a numeric book ID.
     *
     * @param idBook the ID of the book
     * @return the {@link Response} containing authors linked to the book
     */
    public Response getAuthorByBookId(Integer idBook) {
        return given()
            .filter(new AllureRestAssured())
            .accept(ContentType.JSON)
            .when()
            .get(BASE_PATH + "/authors/books/" + idBook);
    }

    /**
     * Retrieves an author by a string-based book ID.
     *
     * @param bookId the ID of the book (string format)
     * @return the {@link Response} containing authors linked to the book
     */
    public Response getAuthorByBookId(String bookId) {
        return given()
            .filter(new AllureRestAssured())
            .accept(ContentType.JSON)
            .when()
            .get(BASE_PATH + "/authors/books/" + bookId);
    }

    /**
     * Creates a new author in the system.
     *
     * @param author the author object (POJO or map) to create
     * @return the {@link Response} containing the created author
     */
    public Response createAuthor(Object author) {
        return given()
            .filter(new AllureRestAssured())
            .contentType(ContentType.JSON)
            .body(author)
            .when()
            .post(BASE_PATH);
    }

    /**
     * Updates an existing author by numeric ID.
     *
     * @param id     the ID of the author to update
     * @param author the updated author object
     * @return the {@link Response} containing the updated author
     */
    public Response updateAuthor(Integer id, Object author) {
        return given()
            .filter(new AllureRestAssured())
            .contentType(ContentType.JSON)
            .body(author)
            .when()
            .put(BASE_PATH + "/" + id);
    }

    /**
     * Deletes an author by numeric ID.
     *
     * @param id the ID of the author to delete
     * @return the {@link Response} from the delete request
     */
    public Response deleteAuthor(Integer id) {
        return given()
            .filter(new AllureRestAssured())
            .when()
            .delete(BASE_PATH + "/" + id);
    }

    /**
     * Deletes an author by string-based ID.
     *
     * @param id the ID of the author to delete (string format)
     * @return the {@link Response} from the delete request
     */
    public Response deleteAuthor(String id) {
        return given()
            .filter(new AllureRestAssured())
            .when()
            .delete(BASE_PATH + "/" + id);
    }

}
