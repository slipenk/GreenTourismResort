package sample.homesteads;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.db_classes.Connection_db;
import sample.db_classes.Homesteads;

import java.io.IOException;
import java.sql.*;

public class Homesteads_controller {

    @FXML
    private Button Add_btn;
    @FXML
    private Button Update_btn;
    @FXML
    private Button Delete_btn;
    @FXML
    private TableView<Homesteads> table_homesteads;
    @FXML
    private TableColumn<Homesteads, String> Name_col;
    @FXML
    private TableColumn<Homesteads, Byte> Beds_col;
    @FXML
    private TableColumn<Homesteads, Byte> Rooms_col;
    @FXML
    private TableColumn<Homesteads, Byte> Floors_col;
    @FXML
    private TableColumn<Homesteads, Boolean> Air_col;
    @FXML
    private TableColumn<Homesteads, Boolean> Safe_col;
    @FXML
    private TableColumn<Homesteads, Boolean> Wi_Fi_col;
    @FXML
    private TableColumn<Homesteads, Boolean> Ref_col;
    @FXML
    private TableColumn<Homesteads, Boolean> Iron_col;
    @FXML
    private TableColumn<Homesteads, Boolean> Hair_col;
    @FXML
    private TableColumn<Homesteads, Integer> Rate_col;
    @FXML
    private TableColumn<Homesteads, Float> Price_col;
    @FXML
    private TableColumn<Homesteads, Boolean> Active_col;


    public void initialize() {
        ShowHomesteads();
    }


    private ObservableList<Homesteads> getHomesteads() {
        ObservableList<Homesteads> HomesteadsList = FXCollections.observableArrayList();
        Connection conn = Connection_db.GetConnection();
        String query = "SELECT * FROM Homesteads";
        Statement st;
        ResultSet rs;

        try {
           if(conn != null) {
               st = conn.createStatement();
               rs = st.executeQuery(query);
               Homesteads homesteads;
               while (rs.next()) {
                   homesteads = new Homesteads(rs.getInt("ID_Homestead"), rs.getString("Name_homestead"),
                           rs.getByte("Number_of_beds_homestead"), rs.getByte("Number_of_rooms_homestead"), rs.getByte("Number_of_floors_homestead"), rs.getBoolean("Is_Air_Conditioning"),
                           rs.getBoolean("Is_Safe"), rs.getBoolean("Is_Wi_Fi"), rs.getBoolean("Is_Refrigerator"), rs.getBoolean("Is_Clothes_Iron"),
                           rs.getBoolean("Is_Hair_Dryer"), rs.getInt("Rate_homestead"), rs.getFloat("Price_homestead"), rs.getBoolean("Is_Active"));
                   HomesteadsList.add(homesteads);
               }
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HomesteadsList;
    }

    public void ShowHomesteads() {
        ObservableList<Homesteads> list = getHomesteads();

        Name_col.setCellValueFactory(new PropertyValueFactory<>("Name_homestead"));
        Beds_col.setCellValueFactory(new PropertyValueFactory<>("Number_of_beds_homestead"));
        Rooms_col.setCellValueFactory(new PropertyValueFactory<>("Number_of_rooms_homestead"));
        Floors_col.setCellValueFactory(new PropertyValueFactory<>("Number_of_floors_homestead"));
        Air_col.setCellValueFactory(new PropertyValueFactory<>("Is_Air_Conditioning"));
        Safe_col.setCellValueFactory(new PropertyValueFactory<>("Is_Safe"));
        Wi_Fi_col.setCellValueFactory(new PropertyValueFactory<>("Is_Wi_Fi"));
        Ref_col.setCellValueFactory(new PropertyValueFactory<>("Is_Refrigerator"));
        Iron_col.setCellValueFactory(new PropertyValueFactory<>("Is_Clothes_Iron"));
        Hair_col.setCellValueFactory(new PropertyValueFactory<>("Is_Hair_Dryer"));
        Rate_col.setCellValueFactory(new PropertyValueFactory<>("Rate_homestead"));
        Price_col.setCellValueFactory(new PropertyValueFactory<>("Price_homestead"));
        Active_col.setCellValueFactory(new PropertyValueFactory<>("Is_Active"));
        table_homesteads.setItems(list);

    }

    public void Add_method(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Add_homesteads.fxml"));
        Parent parent = fxmlLoader.load();
        AddHomesteads AddHomesteads_controller = fxmlLoader.getController();
        AddHomesteads_controller.setController(this);
        Connection_db.Get_Dialog(parent);
    }

    public void Update_method(ActionEvent actionEvent) throws IOException {
        Homesteads homestead = table_homesteads.getSelectionModel().getSelectedItem();

        if(homestead != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Update_homesteads.fxml"));
            Parent parent = fxmlLoader.load();
            UpdateHomesteads UpdateHomesteads_controller = fxmlLoader.getController();
            UpdateHomesteads_controller.setController(this);
            UpdateHomesteads_controller.setHomestead(homestead);
            UpdateHomesteads_controller.setValues();
            Connection_db.Get_Dialog(parent);
        }
    }




    public void Delete_method(ActionEvent actionEvent) {
        Homesteads homestead = table_homesteads.getSelectionModel().getSelectedItem();
        if(homestead != null) {
        String query = "DELETE FROM Homesteads WHERE ID_Homestead = " + homestead.getID_homestead();
        Cancel_Dialog(query); }

    }


    private void Cancel_Dialog(String query)
    {
        ButtonType OK = new ButtonType("Видалити", ButtonBar.ButtonData.OK_DONE);
        ButtonType CANCEL = new ButtonType("Скасувати", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.NONE, "Ви впевнені, що хочете видалити?", OK, CANCEL);
        alert.showAndWait();
        if (alert.getResult() == OK) {
            Connection_db.executeQuery(query);
            ShowHomesteads();
        }
    }
}
