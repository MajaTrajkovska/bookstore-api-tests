package com.example.bookstoreapi.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import com.example.bookstoreapi.config.ConfigReader;
import io.qameta.allure.restassured.AllureRestAssured;

/**
 * BooksApis is a utility class that provides methods for interacting
 * with the Book-related endpoints of the API.
 * 
 * Each method performs a REST operation and returns the response.
 * No assertions or validations are done inside this class.
 */
public class BooksApis {

    private static final String BASE_PATH = "/api/v1/Books";

    // Static initializer to set base URI once
    static {
        baseURI = ConfigReader.get("base.url"); 
    }

public Response getAllBooks() {
    return given()
        .filter(new AllureRestAssured())
        .accept(ContentType.JSON)
        .when()
        .get(BASE_PATH);
}

public Response getBookById(Integer id) {
    return given()
        .filter(new AllureRestAssured())
        .accept(ContentType.JSON)
        .when()
        .get(BASE_PATH + "/" + id);
}

public Response getBookById(String id) {
    return given()
        .filter(new AllureRestAssured())
        .accept(ContentType.JSON)
        .when()
        .get(BASE_PATH + "/" + id);
}

public Response createBook(Object book) {
    return given()
        .filter(new AllureRestAssured())
        .contentType(ContentType.JSON)
        .body(book)
        .when()
        .post(BASE_PATH);
}

public Response updateBook(Integer id, Object book) {
    return given()
        .filter(new AllureRestAssured())
        .contentType(ContentType.JSON)
        .body(book)
        .when()
        .put(BASE_PATH + "/" + id);
}

public Response deleteBook(Integer id) {
    return given()
        .filter(new AllureRestAssured())
        .when()
        .delete(BASE_PATH + "/" + id);
}

public Response deleteBook(String id) {
    return given()
        .filter(new AllureRestAssured())
        .when()
        .delete(BASE_PATH + "/" + id);
}

}