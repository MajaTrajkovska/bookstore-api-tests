package com.example.bookstoreapi.data;

import java.util.concurrent.ThreadLocalRandom;

import com.example.bookstoreapi.model.Author;
import com.github.javafaker.Faker;

public class AuthorDataFactory {

    private static final Faker faker = new Faker();

    // Helper method to generate a random ID
    private static int randomId(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    // Base valid author generator. 
    // Creates an author with a valid ID (between 1 and 604) and valid book Id (between 1 and 200) because in this fake API only for those IDs the API is returning an author.
    public static Author createBaseAuthor() {
        return new Author(
                randomId(1, 604),
                randomId(1, 200),
                faker.name().firstName(),
                faker.name().lastName()     
        );
    }

    // Creates an author with a fixed ID and book ID for testing purposes.
    public static Author createAuthorWithFixedIds() {
        Author author = createBaseAuthor();
        author.setId(1); 
        author.setBookId(1); 
        return author;
    }

    // Negative: missing required field. Logically it is expected to not be able to create an author without the id.
    public static Author createAuthorWithEmptyId(){
        Author author = createBaseAuthor();
        author.setId(null); // Set ID to null
        return author;
    }

    // Negative: missing required field. To each author there must be linked bookId.
    public static Author createAuthorWithEmptyBookId() {
        Author author = createBaseAuthor();
        author.setBookId(null); // Set book ID to null
        return author;
    }

    // Positive: missing non-required field first Name. 
    // Logically it is expected to not be able to create an author without the first name, but inn this case we will imagine that the first name is not mandatory field.
    public static Author createAuthorWithEmptyFirstName() {
        Author author = createBaseAuthor();
        author.setFirstName("");
        return author;
    }

    // Positive: missing non-required field last Name. 
    // Logically it is expected to not be able to create an author without the first name, but inn this case we will imagine that the last name is not mandatory field.
    public static Author createAuthorWithEmptyLastName() {
        Author author = createBaseAuthor();
        author.setLastName("");
        return author;
    }   

    // Negative: create an author with a negative ID
    public static Author createAuthorWithNegativeId() {
        Author author = createBaseAuthor();
        author.setId(-1); // Set ID to a negative value
        return author;
    }

    // Edge: create an author with a maximum integer ID
    public static Author createAuthorWithMaxIntegerId() {
        Author author = createBaseAuthor();
        author.setId(Integer.MAX_VALUE); // Set ID to the maximum integer value
        return author;
    }

    // Edge: create an author with a minimum integer ID
    public static Author createAuthorWithMinIntegerId() {
        Author author = createBaseAuthor();
        author.setId(Integer.MIN_VALUE); // Set ID to the minimum integer value
        return author;
    }

    // Negative: create an author with a null ID
    public static Author createAuthorWithNullId() {
        Author author = createBaseAuthor();
        author.setId(null); // Set ID to null
        return author;
    }   

    // Positive: update author with valid data
    public static Author updateAuthorWithValidData() {
        return createBaseAuthor();
    }

    // Negtaive: update author with empty book ID
    public static Author updateAuthorWithEmptyBookId() {
        return createAuthorWithEmptyBookId();
    }

    // Positive: update author with empty first name
    public static Author updateAuthorWithEmptyFirstName() {
        return createAuthorWithEmptyFirstName();
    }

    // Positive: update author with empty last name
    public static Author updateAuthorWithEmptyLastName() {
        return createAuthorWithEmptyLastName();
    }   

    // Negative: update author with null ID
    public static Author updateAuthorWithNullId() {
        return createAuthorWithNullId();
    }

    // Negative: update author with negative ID
    public static Author updateAuthorWithNegativeId() {
        return createAuthorWithNegativeId();
    }

}
