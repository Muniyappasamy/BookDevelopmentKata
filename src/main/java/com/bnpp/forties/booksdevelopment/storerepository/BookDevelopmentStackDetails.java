package com.bnpp.forties.booksdevelopment.storerepository;

public enum BookDevelopmentStackDetails {

    CLEAN_CODE("Clean Code", "Robert Martin", 50.00),
    THE_CLEAN_CODER("The Clean Coder", "Robert Martin", 50.00),
    CLEAN_ARCHITECTURE("Clean Architecture", "Robert Martin", 50.00),
    TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE("Test-Driven Development By Example", "Kent Beck", 50.00),
    WORKING_EFFECTIVELY_WITH_LEGACY_CODE("Working Effectively With Legacy Code", "Michael C. Feathers", 50.00);

    private String bookTitle;
    private String author;
    private double price;

    BookDevelopmentStackDetails(String bookTitle, String author, double price) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.price = price;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}