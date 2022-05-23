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
public class BorrowingDetail extends Model {
    protected String table = "borrowing_details";

    public BorrowingDetail() {
        this.setTable(this.table);
    }
}
