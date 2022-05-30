/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Core.Model;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Illuminate
 */
public class Borrowing extends Model {

    protected String table = "borrowings";
    protected int id, member_id, admin_id, total;
    protected String member_name, admin_name, borrow_date, return_date, created_at;

    public Borrowing(int member_id, int admin_id, String member_name, String admin_name, String borrow_date, String return_date, String created_at) {
        this.member_id = member_id;
        this.admin_id = admin_id;
        this.member_name = member_name;
        this.admin_name = admin_name;
        this.borrow_date = borrow_date;
        this.return_date = return_date;
        this.created_at = created_at;
    }

    public Borrowing() {
        this.setTable(this.table);
    }

    public Borrowing(int id, int total, String member_name, String return_date) {
        this.id = id;
        this.total = total;
        this.member_name = member_name;
        this.return_date = return_date;
    }
    
    public ResultSet getNotReturned() throws SQLException {
        Model member = new Member();
        Model borrowing_detail = new BorrowingDetail();
        Model reversion = new Reversion();
        
        String query = "SELECT " + this.table + ".id, " + this.table + ".return_date, " + member.getTable() + ".name as name, (SELECT COUNT(*) FROM " + borrowing_detail.getTable() + " WHERE " + borrowing_detail.getTable() + ".borrowing_id = " + this.table + ".id) as total FROM " + this.table + " JOIN " +  member.getTable() + " ON " + member.getTable() + ".id = " + this.table + ".member_id WHERE NOT EXISTS (SELECT null FROM " + reversion.getTable() + " WHERE " + reversion.getTable() + ".borrowing_id = " + this.table + ".id)";
        ResultSet response = this.getQuery(query);
        
        return response;
    }
    
    public ResultSet getNotReturned(int search) throws SQLException {
        Model member = new Member();
        Model borrowing_detail = new BorrowingDetail();
        Model reversion = new Reversion();
        
        String query = "SELECT " + this.table + ".id, (SELECT COUNT(*) FROM " + borrowing_detail.getTable() + " WHERE " + borrowing_detail.getTable() + ".borrowing_id = " + this.table + ".id) as total, " + member.getTable() + ".name as name FROM " + this.table + " JOIN " +  member.getTable() + " ON " + member.getTable() + ".id = " + this.table + ".member_id WHERE NOT EXISTS (SELECT null FROM " + reversion.getTable() + " WHERE " + reversion.getTable() + ".borrowing_id = " + this.table + ".id) AND " + this.table + ".id = " + search;
        ResultSet response = this.getQuery(query);
        
        return response;
    }

    public int getNotReturnedMember(int member_id) throws SQLException {
        int result = 0;
        Reversion reversion = new Reversion();
        String query = "SELECT COUNT(*) FROM " + this.table + " WHERE NOT EXISTS (SELECT null FROM " + reversion.getTable() + " WHERE " + reversion.getTable() + ".borrowing_id = " + this.table + ".id) AND member_id = " + member_id;
        ResultSet response = this.getQuery(query);
        if (response.next()) {
            if (response.getInt(1) > 0) {
                result = 1;
            }
        }
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getMember_name() {
        return member_name;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public String getBorrow_date() {
        return borrow_date;
    }

    public String getReturn_date() {
        return return_date;
    }

    public int getMember_id() {
        return member_id;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public void setBorrow_date(String borrow_date) {
        this.borrow_date = borrow_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
