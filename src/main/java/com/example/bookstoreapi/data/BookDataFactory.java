package com.example.bookstoreapi.data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

import com.example.bookstoreapi.model.Book;
import com.github.javafaker.Faker;

public class BookDataFactory {

    private static final Faker faker = new Faker();
    // Helper method to get current date in ISO format
    private static String nowIsoDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }
    // Helper method to generate a random ID
    private static int randomId(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
    // Helper method to generate a random page count
    private static int randomPageCount() {
        return ThreadLocalRandom.current().nextInt(50, 1000);
    }

    // Base valid book generator
    public static Book createBaseBook() {
        return new Book(
                randomId(1, 200),// because of testing purposes I am limiting the ID to 200, as the fake API is returning books only for IDs in the range of 1-200
                faker.book().title(),
                faker.lorem().sentence(10),
                randomPageCount(),
                faker.lorem().paragraph(),
                nowIsoDate()
        );
    }


    // Negative: missing required field. Logically it is expected to not be able to create a book without its title, but the fake API is returning 200OK.
    public static Book createBookWithoutTitle() {
        Book book = createBaseBook();
        book.setTitle("");
        return book;
    }
    
    // Positive: missing non-required field description
    public static Book createBookWithoutDescription() {
       Book book = createBaseBook();
        book.setDescription("");
        return book;
    }

     //Positive: missing non-required field excerpt
     public static Book createBookWithoutExcerpt() {
        Book book = createBaseBook();
        book.setExcerpt("");
        return book;
    }

    // Negative: missing required field
    public static Book createBookWithoutId() {
        Book book = createBaseBook();
        book.setId(null);
        return book;
}
  
    // Negative: invalid data type (invalid page count)
    public static Book createBookWithNegativePageCount() {
        Book book = createBaseBook();
        book.setPageCount(-5);
        return book;
    }

    // Negative: null pages
    public static Book createBookWithNullPages() {
        Book book = createBaseBook();
        book.setPageCount(null);
        return book;
    }

    // Negative: invalid date format
    public static Book createBookWithInvalidDate() {
        Book book = createBaseBook();
        book.setPublishDate("08/15/2023"); // Wrong format
        return book;
    }

    // Negative: date field null
    public static Book createBookWithNullDate() {
        Book book = createBaseBook();
        book.setPublishDate(null);
        return book;
    }

    //Positive: update book with valid data
    public static Book updateBookWithValidData() {
        return createBaseBook();
    }

    //Negative: update book with empty title
    public static Book updateBookWithEmptyTitle() {
        return createBookWithoutTitle();
    }

    //Positive: update book with empty description
    public static Book updateBookWithEmptyDescription() {
        return createBookWithoutDescription();
    }

    //Positive: update book with empty excerpt
     public static Book updateBookWithEmptyExcerpt() {
        return createBookWithoutExcerpt();
    }

    //Negative: update book with null ID
    public static Book updateBookWithNullId() {
        return createBookWithoutId();
    }
    //Negative: update book with negative page count
    public static Book updateBookWithNegativePageCount() {
        return createBookWithNegativePageCount();
}
    //Negative: update book with null pages
    public static Book updateBookWithNullPages() {
        return createBookWithNullPages();
    }

    //Negative: update book with invalid date
    public static Book updateBookWithInvalidDate() {
        return createBookWithInvalidDate();
    }

    //Negative: update book with null date
    public static Book updateBookWithNullDate() {
        return createBookWithNullDate();
    }

}
