/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Core.Controller;
import Models.Author;
import Models.Auth;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author acer
 */
public class AuthorController extends Controller implements Initializable {
    private Author author = null;
    private Author selectionData = null;
     
    public AuthorController() {
        author = new Author();
    }
    
    @FXML
    private Label nameBox;
    
    @FXML
    private AnchorPane formPage;
    
    @FXML
    private TableView tablePengarang;
    @FXML
    private TableColumn <Author, Integer> idCol;
    @FXML
    private TableColumn <Author, String> nameCol;
    @FXML
    private TableColumn <Author, String> phoneCol;
    @FXML
    private TableColumn <Author, String> addressCol;
    @FXML
    private TableColumn <Author, String> createdAtCol;

    
    ObservableList<Author> AuthorList = FXCollections.observableArrayList();
    
    
    /**
     * Initializes the controller class.
     */
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nameBox.setText(new Auth().getName().toUpperCase());
        formPage.setVisible(false);
        this.loadData();
    }    
    
    @FXML
    private Button btnTambah;
    
    public void btnTambahHandle(ActionEvent act){
        this.btnSubmit.setText("Tambah");
        this.resetForm();
        formPage.setVisible(true);
    }
    
    @FXML
    private Button btnEdit;
    
    public void btnEditHandle(ActionEvent act) {
        if(tablePengarang.getSelectionModel().getSelectedItem() == null) {
            this.showAlert(Alert.AlertType.ERROR, "Ups...", "", "Silahkan pilih pengarang terlebih dahulu");
        } else {
            this.selectionData = (Author) tablePengarang.getSelectionModel().getSelectedItem();
            this.setForm(this.selectionData);
            this.btnSubmit.setText("Simpan");
            formPage.setVisible(true);      
        }   
    }
    
    
    @FXML
    private Button btnHapus;
    
    public void btnHapusHandle(ActionEvent act) throws SQLException {
        if(tablePengarang.getSelectionModel().getSelectedItem() == null) {
            this.showAlert(Alert.AlertType.ERROR, "Ups...", "", "Silahkan pilih pengarang terlebih dahulu");
        } else {
            if (showConfirm("Anda yakin akan menghpus data ini?", "Data yang anda hapus tidak akan bisa dikembalikan").get() == ButtonType.OK) {
                this.selectionData = (Author) tablePengarang.getSelectionModel().getSelectedItem();
                this.author.delete(this.selectionData.getId());
                loadData();
            }
        }
    }
    
    
    
    //Halaman form tambah dan edit
    
    @FXML
    private Button btnKembaliFormPage;
    
    public void btnKembaliFormPageHandle(ActionEvent act){
        this.resetForm();
        formPage.setVisible(false);
    }
    
    
    @FXML
    private TextField idInput;
    @FXML
    private TextField nameInput;
    @FXML
    private TextField nomorInput;
    @FXML
    private TextArea addressInput;    
    
    
    private void setForm(Author author) {
        idInput.setText(String.valueOf(author.getId()));
        nameInput.setText(author.getName());
        nomorInput.setText(author.getPhone());
        addressInput.setText(author.getAddress());
    }
    
    private void resetForm() {
        idInput.setText(null);
        nameInput.setText(null);
        nomorInput.setText(null);
        addressInput.setText(null);
    }
    
        private void inputValidation() throws Exception {
//        String message = "wajib diisi";
        if(nameInput.getText() == null || nameInput.getText().equals("")) {
            throw new Exception("Nama wajib diisi");
        }
        if(nomorInput.getText() == null || nomorInput.getText().equals("")) {
            throw new Exception("Nomor wajib diisi");
        }
        if(addressInput.getText() == null || nomorInput.getText().equals("")) {
            throw new Exception("Alamat wajib diisi");
        }
        
    } 
        
    @FXML
    private Button btnSubmit;
       public void btnSubmitHandle(ActionEvent act) {
           
        try {
            this.inputValidation();
               String name = nameInput.getText(),
               phone = nomorInput.getText(),
               address = addressInput.getText();       
            if(btnSubmit.getText().toLowerCase().equals("tambah")) {
                this.author.store(name, phone, address);
                
                this.showAlert(Alert.AlertType.INFORMATION, "SUKSES", "", "Data pengarang berhasil ditambahkan");
            } else {
                int id = Integer.valueOf(idInput.getText());
                this.author.update(id, name, phone, address);
                
                this.showAlert(Alert.AlertType.INFORMATION, "SUKSES", "", "Data pengarang berhasil diubah");
            }
            loadData();
            formPage.setVisible(false);
        } catch(Exception e) {
         this.showAlert(Alert.AlertType.ERROR, "Upss...", "", e.getMessage());
        }
    }
    
    
//       Set Tableview
    public void setDataTable() {
        try {
           AuthorList.clear();
           
            ResultSet authors = this.author.getAll();
            
            while(authors.next()) {
                   AuthorList.add(new Author(
                        authors.getInt("id"),
                        authors.getString("name"),
                        authors.getString("phone"),
                        authors.getString("address"),
                        authors.getTimestamp("created_at").toString()
                   ));
                   tablePengarang.setItems(AuthorList);
                }
            
        } catch (Exception e) {
             this.showAlert(Alert.AlertType.ERROR, "Ups...", "", e.getMessage());
        }
    }
    
    public void loadData() {
        setDataTable();
        
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
createdAtCol.setCellValueFactory(new PropertyValueFactory<>("created_at"));
    }
    
}
