/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Core.Controller;
import Models.Auth;
import Models.Borrowing;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Illuminate
 */
public class MostVanishController extends Controller implements Initializable {

    private Borrowing borrowings;
    
    public MostVanishController() {
        borrowings = new Borrowing();
    }
    
    @FXML
    private Label nameBox;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nameBox.setText(Auth.getName().toUpperCase());
        setDataTable();
    }
    
    @FXML
    private TableView<Borrowing> tableRiwayat;
    @FXML
    private TableColumn<Borrowing, Integer> idCol;
    @FXML
    private TableColumn<Borrowing, String> nameCol;
    @FXML
    private TableColumn<Borrowing, Integer> totalCol;
    
    ObservableList<Borrowing> mostVanish = FXCollections.observableArrayList();
    
    private void loadData() {
        try {
            ResultSet borrowings = this.borrowings.mostHilang();
            
            while(borrowings.next()) {
                mostVanish.add(new Borrowing(
                        borrowings.getInt("member_id"),
                        borrowings.getString("name"),
                        borrowings.getInt("total_hilang")
                ));
                tableRiwayat.setItems(mostVanish);
            }
        } catch(Exception e) {
            this.showAlert(Alert.AlertType.ERROR, "Ups...", "", e.getMessage());
        }
    }

    private void setDataTable() {
        loadData();
        
        idCol.setCellValueFactory(new PropertyValueFactory<>("member_id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("member_name"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
    }
}
