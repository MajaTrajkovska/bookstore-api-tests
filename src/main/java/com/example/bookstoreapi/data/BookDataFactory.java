package com.example.bookstoreapi.data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

import com.example.bookstoreapi.model.Book;
import com.github.javafaker.Faker;

public class BookDataFactory {

    private static final Faker faker = new Faker();

    /**
     * Helper method to get current date in ISO format.
     *
     * @return the current date and time as an ISO 8601 formatted string
     */
    private static String nowIsoDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }

    /**
     * Helper method to generate a random ID.
     *
     * @param min minimum value (inclusive)
     * @param max maximum value (exclusive)
     * @return a random integer between min (inclusive) and max (exclusive)
     */
    private static int randomId(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    /**
     * Helper method to generate a random page count.
     *
     * @return a random integer representing page count between 50 and 1000
     */
    private static int randomPageCount() {
        return ThreadLocalRandom.current().nextInt(50, 1000);
    }

    /**
     * Base valid book generator.
     * Because of testing purposes I am limiting the ID to 200, as the fake API is returning books only for IDs in the range of 1-200.
     *
     * @return a valid Book instance with random data
     */
    public static Book createBaseBook() {
        return new Book(
                randomId(1, 200),
                faker.book().title(),
                faker.lorem().sentence(10),
                randomPageCount(),
                faker.lorem().paragraph(),
                nowIsoDate()
        );
    }

    /**
     * Negative: missing required field. Logically it is expected to not be able to create a book without its title, but the fake API is returning 200OK.
     *
     * @return a Book instance with an empty title
     */
    public static Book createBookWithoutTitle() {
        Book book = createBaseBook();
        book.setTitle("");
        return book;
    }

    /**
     * Positive: missing non-required field description.
     *
     * @return a Book instance with an empty description
     */
    public static Book createBookWithoutDescription() {
        Book book = createBaseBook();
        book.setDescription("");
        return book;
    }

    /**
     * Positive: missing non-required field excerpt.
     *
     * @return a Book instance with an empty excerpt
     */
    public static Book createBookWithoutExcerpt() {
        Book book = createBaseBook();
        book.setExcerpt("");
        return book;
    }

    /**
     * Negative: missing required field.
     *
     * @return a Book instance with null ID
     */
    public static Book createBookWithoutId() {
        Book book = createBaseBook();
        book.setId(null);
        return book;
    }

    /**
     * Negative: invalid data type (invalid page count).
     *
     * @return a Book instance with negative page count
     */
    public static Book createBookWithNegativePageCount() {
        Book book = createBaseBook();
        book.setPageCount(-5);
        return book;
    }

    /**
     * Negative: null pages.
     *
     * @return a Book instance with null page count
     */
    public static Book createBookWithNullPages() {
        Book book = createBaseBook();
        book.setPageCount(null);
        return book;
    }

    /**
     * Negative: invalid date format.
     *
     * @return a Book instance with an invalid publish date format
     */
    public static Book createBookWithInvalidDate() {
        Book book = createBaseBook();
        book.setPublishDate("08/15/2023"); // Wrong format
        return book;
    }

    /**
     * Negative: date field null.
     *
     * @return a Book instance with null publish date
     */
    public static Book createBookWithNullDate() {
        Book book = createBaseBook();
        book.setPublishDate(null);
        return book;
    }

    /**
     * Positive: update book with valid data.
     *
     * @return a valid Book instance for update
     */
    public static Book updateBookWithValidData() {
        return createBaseBook();
    }

    /**
     * Negative: update book with empty title.
     *
     * @return a Book instance with empty title for update
     */
    public static Book updateBookWithEmptyTitle() {
        return createBookWithoutTitle();
    }

    /**
     * Positive: update book with empty description.
     *
     * @return a Book instance with empty description for update
     */
    public static Book updateBookWithEmptyDescription() {
        return createBookWithoutDescription();
    }

    /**
     * Positive: update book with empty excerpt.
     *
     * @return a Book instance with empty excerpt for update
     */
    public static Book updateBookWithEmptyExcerpt() {
        return createBookWithoutExcerpt();
    }

    /**
     * Negative: update book with null ID.
     *
     * @return a Book instance with null ID for update
     */
    public static Book updateBookWithNullId() {
        return createBookWithoutId();
    }

    /**
     * Negative: update book with negative page count.
     *
     * @return a Book instance with negative page count for update
     */
    public static Book updateBookWithNegativePageCount() {
        return createBookWithNegativePageCount();
    }

    /**
     * Negative: update book with null pages.
     *
     * @return a Book instance with null page count for update
     */
    public static Book updateBookWithNullPages() {
        return createBookWithNullPages();
    }

    /**
     * Negative: update book with invalid date.
     *
     * @return a Book instance with invalid publish date for update
     */
    public static Book updateBookWithInvalidDate() {
        return createBookWithInvalidDate();
    }

    /**
     * Negative: update book with null date.
     *
     * @return a Book instance with null publish date for update
     */
    public static Book updateBookWithNullDate() {
        return createBookWithNullDate();
    }

}