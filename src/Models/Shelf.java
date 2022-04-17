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
public class Shelf extends Model {
    protected String table = "shelves";
    int id;
    String code, location, created_at;

    public Shelf(int id, String code, String location, String created_at) {
        this.id = id;
        this.code = code;
        this.location = location;
        this.created_at = created_at;
    }

    public Shelf() {
        this.setTable(this.table);
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getLocation() {
        return location;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}