/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import Config.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Illuminate
 */
abstract public class Model {
    protected String table;
    
    public String getTable() {
        return this.table;
    }
    
    public void setTable(String table) {
        this.table = table;
    }
    
    public ResultSet getQuery(String query) throws SQLException
    {
        Connection conn = Database.GetConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet result = ps.executeQuery();
            
        return result;
    }
    
    public ResultSet getAll() throws SQLException
    {
        String query = "SELECT * FROM " + this.table;
        ResultSet response = this.getQuery(query);
            
        return response;
    }
    
    public ResultSet getById(int id) throws SQLException
    {
        String query = "SELECT * FROM " + this.table + " WHERE id = '" + id + "'";
        ResultSet response = this.getQuery(query);

        return response;
    }
    
    public ResultSet getById(String id) throws SQLException
    {
        String query = "SELECT * FROM " + this.table + " WHERE id = '" + id + "'";
        ResultSet response = this.getQuery(query);

        return response;
    }
    
    public ResultSet getByUsername(String username) throws SQLException
    {
        String query = "SELECT * FROM " + this.table + " WHERE username = '" + username + "'";
        ResultSet response = this.getQuery(query);

        return response;
    }
    
    public boolean delete(int id) throws SQLException {
        String query = "DELETE FROM " + this.table + " WHERE id = '" + id + "'";
        boolean response = this.executeQuery(query);
        
        return response;
    }
    
     public boolean executeQuery(String query) throws SQLException
    {
        Connection conn = Database.GetConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        return ps.execute(); 
    }
}
