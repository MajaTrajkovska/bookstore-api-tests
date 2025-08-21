package com.example.bookstoreapi.model;

public class Book {
    private Integer id;
    private String title;
    private String description;
    private Integer pageCount;
    private String excerpt;
    private String publishDate;


    public Book(Integer id, String title, String description, Integer pageCount, String excerpt, String publishDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.pageCount = pageCount;
        this.excerpt = excerpt;
        this.publishDate = publishDate;
    }
    // Constructor without ID for creating new book without id so I can test the negative scenario for creatiing a book without mandatory field
    public Book(String title, String description, int pageCount, String excerpt, String publishDate) {
    this.title = title;
    this.description = description;
    this.pageCount = pageCount;
    this.excerpt = excerpt;
    this.publishDate = publishDate;
}

    /**
    * Getters and Setters
    */
    public Integer getId() { return id;}   
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getPageCount() { return pageCount; }
    public void setPageCount(Integer pageCount) { this.pageCount = pageCount; }

    public String getExcerpt() { return excerpt; }
    public void setExcerpt(String excerpt) { this.excerpt = excerpt; }

    public String getPublishDate() { return publishDate; }
    public void setPublishDate(String publishDate) { this.publishDate = publishDate; }

}