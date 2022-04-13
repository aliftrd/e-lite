/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Core.Model;

/**
 *
 * @author Illuminate
 */
public class Book extends Model {
    protected String table = "books";
    
    int id, publish_year, stock, price;
    String title, isbn, created_at;

    public Book(int id, String isbn, String title, int stock, int price, int publish_year) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.stock = stock;
        this.price = price;
        this.publish_year = publish_year;
    }
    
    public Book() {
        this.setTable(this.table);
    }
    
    public int getId() {
        return id;
    }

    public int getPublish_year() {
        return publish_year;
    }

    public int getStock() {
        return stock;
    }

    public int getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPublish_year(int publish_year) {
        this.publish_year = publish_year;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
