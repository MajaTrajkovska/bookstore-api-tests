package com.example.bookstoreapi.api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import com.example.bookstoreapi.config.ConfigReader;

/**
 * AuthorsApis is a utility class that provides methods for interacting
 * with the Author-related endpoints of the API.
 * 
 * Each method performs a REST operation and returns the response.
 * No assertions or validations are done inside this class.
 */
public class AuthorsApis {

    private static final String BASE_PATH = "/api/v1/Authors";

    // Static initializer to set base URI once
    static {
        baseURI = ConfigReader.get("base.url"); 
    }

public Response getAllAuthors() {
    return given()
        .filter(new AllureRestAssured())
        .accept(ContentType.JSON)
        .when()
        .get(BASE_PATH);
}

public Response getAuthorById(Integer id) {
    return given()
        .filter(new AllureRestAssured())
        .accept(ContentType.JSON)
        .when()
        .get(BASE_PATH + "/" + id);
}
public Response getAuthorById(String id) {
    return given()
        .filter(new AllureRestAssured())
        .accept(ContentType.JSON)
        .when()
        .get(BASE_PATH + "/" + id);
}

public Response getAuthorByBookId(Integer idBook) {
    return given()
        .filter(new AllureRestAssured())
        .accept(ContentType.JSON)
        .when()
        .get(BASE_PATH + "/authors/books/" + idBook);
}

public Response getAuthorByBookId(String bookId) {
    return given()
        .filter(new AllureRestAssured())
        .accept(ContentType.JSON)
        .when()
        .get(BASE_PATH + "/authors/books/" + bookId);
}

public Response createAuthor(Object author) {
    return given()
        .filter(new AllureRestAssured())
        .contentType(ContentType.JSON)
        .body(author)
        .when()
        .post(BASE_PATH);
}

public Response updateAuthor(Integer id, Object author) {
    return given()
        .filter(new AllureRestAssured())
        .contentType(ContentType.JSON)
        .body(author)
        .when()
        .put(BASE_PATH + "/" + id);
}

public Response deleteAuthor(Integer id) {
    return given()
        .filter(new AllureRestAssured())
        .when()
        .delete(BASE_PATH + "/" + id);
}

public Response deleteAuthor(String id) {
    return given()
        .filter(new AllureRestAssured())
        .when()
        .delete(BASE_PATH + "/" + id);
}

}