/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Core.Controller;
import Models.Auth;
import Models.Book;
import Models.Author;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Illuminate
 */
public class BookController extends Controller implements Initializable {
    private Book book = null;
    private final ObservableList<Author> authorList = FXCollections.observableArrayList();
    
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
        this.initList();
        loadData();
    }
    
    public void initList() {
        try { 
            // Author
            authorInput.setConverter(new StringConverter<Author>() {
                @Override
                public String toString(Author object) {
                    return object.getName();
                }

                @Override
                public Author fromString(String string) {
                    return authorInput.getItems().stream().filter(ap -> 
                        ap.getName().equals(string)).findFirst().orElse(null);
                }
                
            });
            
            ResultSet authors = new Author().getAll();
            
            while(authors.next()) {
                authorList.add(new Author(
                        authors.getInt("id"),
                        authors.getString("name"),
                        authors.getString("phone"),
                        authors.getString("address"),
                        authors.getTimestamp("created_at").toString()
                ));
                this.authorInput.setItems(authorList);
            }
            
            // Publisher
            
            // Shelves
        } catch(Exception e) {
            this.showAlert(Alert.AlertType.ERROR, "Ups...", "", e.getMessage());
        }
    }
    
    @FXML
    private Button btnTambah;
    
    public void btnTambahHandle(ActionEvent evt) {
        this.btnSubmit.setText("Tambah");
        this.resetForm();
        formPage.setVisible(true);
    }
    
    @FXML
    private Button btnEdit;
    
    public void btnEditHandle(ActionEvent evt) {
        if(tableBuku.getSelectionModel().getSelectedItem() == null) {
            this.showAlert(Alert.AlertType.ERROR, "Ups...", "", "Silahkan pilih petugas terlebih dahulu");
        } else {
            this.book = (Book) tableBuku.getSelectionModel().getSelectedItem();
            this.setForm(this.book);
            this.btnSubmit.setText("Simpan");
            formPage.setVisible(true);        
        }
    }
    
    @FXML
    private Button btnHapus;
    
    public void btnHapusHandle(ActionEvent evt) {
        
    }
    
    @FXML
    private TextField idInput;
    @FXML
    private TextField isbnInput;
    @FXML
    private TextField titleInput;
    @FXML
    private TextField priceInput;
    @FXML
    private ComboBox<Author> authorInput;
    @FXML
    private ComboBox publisherInput;
    @FXML
    private ComboBox shelfInput;
    
    private void setForm(Book book) {
        idInput.setText(String.valueOf(book.getId()));
        isbnInput.setText(book.getIsbn());
        titleInput.setText(book.getTitle());
        priceInput.setText(String.valueOf(book.getPrice()));
//        authorInput.setValue(book.getAuthor());
//        publisherInput.setValue(book.getAddress());
//        shelfInput.setValue(book.getAddress());
    }
    
    private void resetForm() {
        idInput.setText(null);
        isbnInput.setText(null);
        titleInput.setText(null);
        priceInput.setText(null);
        authorInput.setValue(null);
        publisherInput.setValue(null);
        shelfInput.setValue(null);
    }
    
    @FXML
    private Button btnKembaliFormPage;
    
    public void btnKembaliFormPageHandle(ActionEvent act) {
        this.resetForm();
        formPage.setVisible(false);
    }
    
    @FXML
    private Button btnSubmit;
    
    public void btnSubmitHandle(ActionEvent act) {
        Author author = authorInput.getSelectionModel().getSelectedItem();
        System.out.println(author.getId());
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
