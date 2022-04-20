/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Core.Controller;
import Config.BCrypt;
import Models.Admin;
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
import javafx.scene.control.ComboBox;
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
 * @author Illuminate
 */
public class AdminController extends Controller implements Initializable {
    private Admin admin = null;
    private Admin selectionData = null;
    private final ObservableList<String> genderList = FXCollections.observableArrayList("Pria", "Wanita");
    
    public AdminController() {
        admin = new Admin();
    }
    
    @FXML
    private Label nameBox;
    
    @FXML
    private AnchorPane formPage;
    
    @FXML
    private TableView tablePetugas;
    @FXML
    private TableColumn <Admin, Integer> idCol;
    @FXML
    private TableColumn <Admin, String> nameCol;
    @FXML
    private TableColumn <Admin, String> usernameCol;
    @FXML
    private TableColumn <Admin, String> phoneCol;
    @FXML
    private TableColumn <Admin, String> genderCol;
    @FXML
    private TableColumn <Admin, String> addressCol;
    @FXML
    private TableColumn <Admin, String> createdAtCol;
    
    ObservableList<Admin> AdminList = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nameBox.setText(new Auth().getName().toUpperCase());
        formPage.setVisible(false);
        this.genderInput.setItems(genderList);
        this.loadData();
    }
    
    @FXML
    private Button btnTambah;
    
    public void btnTambahHandle(ActionEvent act) {
        this.btnSubmit.setText("Tambah");
        this.resetForm();
        formPage.setVisible(true);
    }
    
    @FXML
    private Button btnEdit;
    
    public void btnEditHandle(ActionEvent act) {
        if(tablePetugas.getSelectionModel().getSelectedItem() == null) {
            this.showAlert(Alert.AlertType.ERROR, "Ups...", "", "Silahkan pilih petugas terlebih dahulu");
        } else {
            this.selectionData = (Admin) tablePetugas.getSelectionModel().getSelectedItem();
            this.setForm(this.selectionData);
            this.btnSubmit.setText("Simpan");
            formPage.setVisible(true);
        }
    }
    
    @FXML
    private Button btnHapus;
    
    public void btnHapusHandle(ActionEvent act) throws SQLException {
        if(tablePetugas.getSelectionModel().getSelectedItem() == null) {
            this.showAlert(Alert.AlertType.ERROR, "Ups...", "", "Silahkan pilih petugas terlebih dahulu");
        } else {
            if (showConfirm("Anda yakin akan menghpus data ini?", "Data yang anda hapus tidak akan bisa dikembalikan").get() == ButtonType.OK) {
                this.selectionData = (Admin) tablePetugas.getSelectionModel().getSelectedItem();
                this.admin.delete(this.selectionData.getId());
                loadData();
            }
        }
    }
    
    
    //    Halaman From Tambah dan Edit
    
    @FXML
    private Button btnKembaliFormPage;
    
    public void btnKembaliFormPageHandle(ActionEvent act) {
        this.resetForm();
        formPage.setVisible(false);
    }
    
    @FXML
    private TextField idInput;
    @FXML
    private TextField nameInput;
    @FXML
    private ComboBox genderInput;
    @FXML
    private TextField usernameInput;
    @FXML
    private TextField passwordInput;
    @FXML
    private TextField nomorInput;
    @FXML
    private TextArea addressInput;
    
    private void setForm(Admin admin) {
        idInput.setText(String.valueOf(admin.getId()));
        nameInput.setText(admin.getName());
        genderInput.setValue(admin.getGender());
        usernameInput.setText(admin.getUsername());
        nomorInput.setText(admin.getPhone());
        addressInput.setText(admin.getAddress());
    }
    
    private void resetForm() {
        idInput.setText(null);
        nameInput.setText(null);
        genderInput.setValue(null);
        usernameInput.setText(null);
        passwordInput.setText(null);
        nomorInput.setText(null);
        addressInput.setText(null);
    }
    
    private void inputValidation() throws Exception {
        if(nameInput.getText() == null || nameInput.getText().equals("")) {
            throw new Exception("Nama wajib diisi");
        }
        
        if(genderInput.getValue() == null) {
            throw new Exception("Gender wajib diisi");
        }
        
        if(usernameInput.getText() == null || usernameInput.getText().equals("")) {
            throw new Exception("Username wajib diisi");
        }
        
        if(passwordInput.getText() == null && btnSubmit.getText().toLowerCase().equals("tambah")) {
            throw new Exception("Password wajib diisi");
        }
        
        if(nomorInput.getText() == null || nomorInput.getText().equals("")) {
            throw new Exception("Nomor wajib diisi");
        }
        
        if(addressInput.getText() == null || addressInput.getText().equals("")) {
            throw new Exception("Alamat wajib diisi");
        }
    } 
    
    @FXML
    private Button btnSubmit;
    
    public void btnSubmitHandle(ActionEvent act) {
        try {
            this.inputValidation();
            String  name = nameInput.getText(),
                    username = usernameInput.getText(),
                    password = passwordInput.getText().equals("") ? passwordInput.getText() : BCrypt.hashpw(passwordInput.getText(), BCrypt.gensalt()),
                    phone = nomorInput.getText(),
                    gender = (String) genderInput.getValue(),
                    address = addressInput.getText();
            
            if(btnSubmit.getText().toLowerCase().equals("tambah")) {
                this.admin.store(name, username, password, phone, gender, address);
                
                this.showAlert(Alert.AlertType.INFORMATION, "SUKSES", "", "Data petugas berhasil ditambahkan");
            } else {
                int id = Integer.valueOf(idInput.getText());
                ResultSet currentFullData = this.admin.getById(id);
                String checkPassword = null;
                while(currentFullData.next()) {
                    checkPassword = password.equals("") ? currentFullData.getString("password") : password;
                }
                this.admin.update(id, name, username, checkPassword, phone, gender, address);
                
                this.showAlert(Alert.AlertType.INFORMATION, "SUKSES", "", "Data petugas berhasil diubah");
            }
            
            this.loadData();
            formPage.setVisible(false);
        } catch(Exception e) {
            this.showAlert(Alert.AlertType.ERROR, "Ups...", "", e.getMessage());
        }
    }
    
    
    
    
    // Set Tableview
    public void setDataTable() {
        try {
            AdminList.clear();
            ResultSet admins = this.admin.getAll();
            
            while(admins.next()) {
                if(!admins.getString("username").equals(Auth.getUsername())) {
                    AdminList.add(new Admin(
                            admins.getInt("id"),
                            admins.getString("name"),
                            admins.getString("username"),
                            admins.getString("phone"),
                            admins.getString("gender"),
                            admins.getString("address"),
                            admins.getTimestamp("created_at").toString()
                    ));
                    tablePetugas.setItems(AdminList);
                }
            }
        } catch (Exception e) {
            this.showAlert(Alert.AlertType.ERROR, "Ups...", "", e.getMessage());
        }
    }
    
    public void loadData() {
        setDataTable();
        
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        createdAtCol.setCellValueFactory(new PropertyValueFactory<>("created_at"));
    }
}
