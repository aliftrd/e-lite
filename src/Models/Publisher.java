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
public class Publisher extends Model {
    protected String table = "publishers";
    
    int id;
    String name, phone, address, created_at;
    
    public Publisher(int id, String name, String phone, String address, String created_at){
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.created_at = created_at;
    }
    
    public Publisher() {
        this.setTable(this.table);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    
    
}
