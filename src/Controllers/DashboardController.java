/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Core.Controller;
import Models.Auth;
import java.net.URL;
import Models.Book;
import Models.Borrowing;
import Models.Member;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Illuminate
 */
public class DashboardController extends Controller implements Initializable {
    private Book book;
    private Member member;
    private Borrowing borrowing;
    
    public DashboardController() {
        super();
        this.book = new Book();
        this.member = new Member();
        this.borrowing = new Borrowing();
    }
    
    @FXML
    private Label nameBox;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nameBox.setText(Auth.getName().toUpperCase());
        initCounter();
    }
    
    @FXML
    private Label totalBuku;
    @FXML
    private Label totalAnggota;
    @FXML
    private Label totalTransaksi;
    
    public void initCounter() {
        try {
            ResultSet books = this.book.getCount();
            if(books.next()) {
                totalBuku.setText(books.getString(1) + " (" + books.getString(2) + ")");
            }
            
            ResultSet members = this.member.getCount();
            if(members.next()) {
                totalAnggota.setText(members.getString(1));
            }
            
            ResultSet borrowings = this.borrowing.getCount();
            if(borrowings.next()) {
                totalTransaksi.setText(borrowings.getString(1));
            }
        } catch(Exception e) {
            this.showAlert(Alert.AlertType.ERROR, "Ups...", "", e.getMessage());
        }
    }
}
