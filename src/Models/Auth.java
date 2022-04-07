/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Illuminate
 */
public class Auth extends Model {
    static {
        table = "admins";
    }
    
    private static int id;
    private static String username;
    
    public String getName() {
        try {
            ResultSet rs = (ResultSet) this.getByUsername(Auth.getUsername());
            String name = null;

            while(rs.next()) {
                name = rs.getString("name");
            }

            return name;
        } catch (SQLException e) {
            return e.getMessage();
        }
    }
    
    public static void setId(int id)
    {
        Auth.id = id;
    }
    
    public static void setUsername(String username)
    {
        Auth.username = username;
    }
    
    public static int getId()
    {
        return Auth.id;
    }
    
    public static String getUsername()
    {
        return Auth.username;
    }
}
