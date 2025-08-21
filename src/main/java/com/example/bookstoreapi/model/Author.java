package com.example.bookstoreapi.model;

public class Author {
    private Integer id;
    private Integer bookId;
    private String firstName;
    private String lastName;


    public Author(Integer id, Integer bookId, String firstName, String lastName) {
        this.id = id;
        this.bookId = bookId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
    * Getters and Setters
    */
    public Integer getId() { return id;}   
    public void setId(Integer id) { this.id = id; }

    public Integer getBookId() { return bookId; }
    public void setBookId(Integer bookId) { this.bookId = bookId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }


}