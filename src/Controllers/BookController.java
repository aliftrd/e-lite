/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Config.Storage;
import Core.Controller;
import Models.Auth;
import Models.Book;
import Models.Author;
import Models.Publisher;
import Models.Shelf;
import java.io.File;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Illuminate
 */
public class BookController extends Controller implements Initializable {
    private Book book = null;
    private Book selectionData = null;
    private final ObservableList<Author> authorList = FXCollections.observableArrayList();
    private final ObservableList<Publisher> publisherList = FXCollections.observableArrayList();
    private final ObservableList<Shelf> shelvesList = FXCollections.observableArrayList();
    
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
            ResultSet authors, publishers, shelves;
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
            
            authors = new Author().getAll();
            
            while(authors.next()) {
                authorList.add(new Author(
                        authors.getInt("id"),
                        authors.getString("name"),
                        authors.getString("phone"),
                        authors.getString("address"),
                        authors.getTimestamp("created_at").toString()
                ));
                authorInput.setItems(authorList);
            }
            
            // Publisher
            publisherInput.setConverter(new StringConverter<Publisher>() {
                @Override
                public String toString(Publisher object) {
                    return object.getName();
                }

                @Override
                public Publisher fromString(String string) {
                    return publisherInput.getItems().stream().filter(ap -> 
                        ap.getName().equals(string)).findFirst().orElse(null);
                }
            });
            
            publishers = new Publisher().getAll();
            
            while(publishers.next()) {
                publisherList.add(new Publisher(
                        publishers.getInt("id"),
                        publishers.getString("name"),
                        publishers.getString("phone"),
                        publishers.getString("address"),
                        publishers.getString("created_at")
                ));
                
                publisherInput.setItems(publisherList);
            }
            
            // Shelves
            shelfInput.setConverter(new StringConverter<Shelf>() {
                @Override
                public String toString(Shelf object) {
                    return object.getLocation();
                }

                @Override
                public Shelf fromString(String string) {
                    return shelfInput.getItems().stream().filter(ap -> 
                        ap.getLocation().equals(string)).findFirst().orElse(null);
                }
            });
            
            shelves = new Shelf().getAll();
            
            while(shelves.next()) {
                shelvesList.add(new Shelf(
                        shelves.getInt("id"),
                        shelves.getString("code"),
                        shelves.getString("location"),
                        shelves.getString("created_at")
                ));
                
                shelfInput.setItems(shelvesList);
            }
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
            this.selectionData = (Book) tableBuku.getSelectionModel().getSelectedItem();
            this.setForm(this.selectionData);
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
    private ComboBox<Publisher> publisherInput;
    @FXML
    private ComboBox<Shelf> shelfInput;
    @FXML
    private ImageView previewPhoto;
    @FXML
    private TextField photoInput;
    @FXML
    private TextArea descInput;
    
    
    private void setForm(Book book) {
        idInput.setText(String.valueOf(book.getId()));
        isbnInput.setText(book.getIsbn());
        titleInput.setText(book.getTitle());
        priceInput.setText(String.valueOf(book.getPrice()));
        
        authorList.filtered(author -> {
            if(author.getId() == book.getAuthor_id()) {
                authorInput.getSelectionModel().select(author);
            }
            return true;
        });
        publisherList.filtered(publisher -> {
            if(publisher.getId() == book.getPublisher_id()) {
                publisherInput.getSelectionModel().select(publisher);
            }
            return true;
        });
        shelvesList.filtered(shelf -> {
            if(shelf.getId() == book.getShelf_id()) {
                shelfInput.getSelectionModel().select(shelf);
            }
            return true;
        });
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
        System.out.println(selectionData.getPrice());
    }
    
    @FXML
    private Button btnChoosePhoto;
    
    public void btnChoosePhotoHandle(ActionEvent act) {
        File selectedFile = new Storage().open("image");
        Image image =  new Image(getClass().getResourceAsStream(selectedFile.toString()));
        previewPhoto.setImage(image);
        System.out.println(selectedFile);
    }
    
    
    
    
    // Set Datatable
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
                        books.getInt("publish_year"),
                        books.getInt("author_id"),
                        books.getInt("publisher_id"),
                        books.getInt("shelf_id")
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
