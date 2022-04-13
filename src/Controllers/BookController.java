/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Core.Controller;
import Models.Auth;
import Models.Book;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Illuminate
 */
public class BookController extends Controller implements Initializable {
    private Book book = null;
    
    public BookController() {
        book = new Book();
    }
    
    @FXML
    private Label nameBox;
    
    @FXML
    private AnchorPane formPage;
    
    @FXML
    private TableView tableBuku;
    @FXML
    private TableColumn <Book, Integer> idCol;
    @FXML
    private TableColumn <Book, String> isbnCol;
    @FXML
    private TableColumn <Book, String> titleCol;
    @FXML
    private TableColumn <Book, Integer> stockCol;
    @FXML
    private TableColumn <Book, Integer> priceCol;
    @FXML
    private TableColumn <Book, Integer> publishYearCol;
    
    ObservableList<Book> BookList = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nameBox.setText(new Auth().getName().toUpperCase());
        formPage.setVisible(false);
        loadData();
    }
    
    @FXML
    private Button btnTambah;
    
    public void btnTambahHandle(ActionEvent evt) {
        
    }
    
    @FXML
    private Button btnEdit;
    
    public void btnEditHandle(ActionEvent evt) {
        
    }
    
    @FXML
    private Button btnHapus;
    
    public void btnHapusHandle(ActionEvent evt) {
        
    }
    
    @FXML
    private Button btnKembaliFormPage;
    
    public void btnKembaliFormPageHandle(ActionEvent act) {
//        this.resetForm();
        formPage.setVisible(false);
    }
    
    @FXML
    private Button btnSubmit;
    
    public void btnSubmitHandle(ActionEvent act) {
        
    }
    
    public void setDataTable() {
        try {
            BookList.clear();
            ResultSet books = this.book.getAll();
            
            while(books.next()) {
                BookList.add(new Book(
                        books.getInt("id"),
                        books.getString("isbn"),
                        books.getString("title"),
                        books.getInt("stock"),
                        books.getInt("price"),
                        books.getInt("publish_year")
                ));
                tableBuku.setItems(BookList);
            }
        } catch(Exception e) {
            this.showAlert(Alert.AlertType.ERROR, "Ups...", "", e.getMessage());
        }
    }
    
    public void loadData() {
        this.setDataTable();
        
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        publishYearCol.setCellValueFactory(new PropertyValueFactory<>("publish_year"));
    }
}
