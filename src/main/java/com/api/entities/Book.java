package com.api.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Book {

    @Id
    private int bookId;
    private String title;
    private String author;
    private int quantity;
    private String Available="true";
    
    public Book() {
    	this.Available = "true";
    }

    public Book(int bookId, String title, String author,int quantity, String Available) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.quantity = quantity;
        this.Available = "true";
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.Available = quantity > 0 ? "true" : "false";
    }

    public String getAvailable() {
        return Available;
    }

    public void setAvailable(String available) {
        this.Available = available;
    }

}