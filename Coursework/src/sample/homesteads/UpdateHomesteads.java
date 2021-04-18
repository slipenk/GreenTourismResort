package sample.homesteads;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import sample.db_classes.Connection_db;
import sample.db_classes.Homesteads;
import sample.homesteads.Homesteads_controller;

import java.util.ArrayList;

public class UpdateHomesteads {
    @FXML
    private ChoiceBox<Byte> Number_of_floors;
    @FXML
    private ChoiceBox<Byte> Number_of_beds;
    @FXML
    private CheckBox Is_Hair;
    @FXML
    private CheckBox Is_Iron;
    @FXML
    private CheckBox Is_ref;
    @FXML
    private CheckBox Is_Wi_Fi;
    @FXML
    private CheckBox Is_safe;
    @FXML
    private CheckBox Is_air_conditioning;
    @FXML
    private Button Update_btn;
    @FXML
    private TextField Price_homestead;
    @FXML
    private TextField Name_homestead;
    @FXML
    private ChoiceBox<Byte> Number_of_rooms;
    @FXML
    private CheckBox is_available;
    @FXML
    private ChoiceBox<Integer> rate_box;

    private Homesteads_controller hc;
    private Homesteads homestead;

    public void setController(Homesteads_controller hc) {
        this.hc = hc;
    }
    public void setHomestead(Homesteads homesteads) {
        homestead = homesteads;
    }

    public void initialize() {
        Number_of_floors.setItems(FXCollections.observableArrayList(
                (byte)1, (byte)2, (byte)3));
        Number_of_floors.setValue((byte)1);
        Number_of_beds.setItems(FXCollections.observableArrayList(
                (byte)1, (byte)2, (byte)3, (byte)4, (byte)5, (byte)6, (byte)7, (byte)8));
        Number_of_beds.setValue((byte)1);
        Number_of_rooms.setItems(FXCollections.observableArrayList(
                (byte)1, (byte)2, (byte)3, (byte)4, (byte)5, (byte)6, (byte)7, (byte)8));
        Number_of_rooms.setValue((byte)1);
        rate_box.setItems(FXCollections.observableArrayList(
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        rate_box.setValue(1);


        Price_homestead.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*(\\.\\d*)?")) {
                    Price_homestead.setText(oldValue);
                }
            }
        });
        Price_homestead.setText("0");
        Name_homestead.setText(" ");
    }

    public void setValues() {
        Number_of_floors.setValue(homestead.getNumber_of_floors_homestead());
        Number_of_beds.setValue(homestead.getNumber_of_beds_homestead());
        Number_of_rooms.setValue(homestead.getNumber_of_rooms_homestead());
        is_available.setSelected(homestead.isIs_Active());
        Price_homestead.setText(String.valueOf(homestead.getPrice_homestead()));
        Name_homestead.setText(homestead.getName_homestead());
        Is_air_conditioning.setSelected(homestead.isIs_Air_Conditioning());
        Is_safe.setSelected(homestead.isIs_Safe());
        Is_Wi_Fi.setSelected(homestead.isIs_Wi_Fi());
        Is_ref.setSelected(homestead.isIs_Refrigerator());
        Is_Iron.setSelected(homestead.isIs_Clothes_Iron());
        Is_Hair.setSelected(homestead.isIs_Hair_Dryer());

    }

    public void Update_method(ActionEvent actionEvent) {

        String query = "UPDATE Homesteads SET Name_homestead = N'" + Name_homestead.getText()  + "', Number_of_beds_homestead = " +
                Number_of_beds.getValue() + ", Number_of_rooms_homestead = " + Number_of_rooms.getValue() + ", Number_of_floors_homestead = " +
                Number_of_floors.getValue() + ", Is_Air_Conditioning = " + (Is_air_conditioning.isSelected() ? 1 : 0) + ", Is_Safe = " +
                (Is_safe.isSelected() ? 1 : 0) + ", Is_Wi_Fi = " + (Is_Wi_Fi.isSelected() ? 1 : 0) + ", Is_Refrigerator = " +
                (Is_ref.isSelected() ? 1 : 0) + ", Is_Clothes_Iron = " + (Is_Iron.isSelected() ? 1 : 0) + ", Is_Hair_Dryer = " +
                (Is_Hair.isSelected() ? 1 : 0) + ", Rate_homestead = " + (rate_box.getValue() + homestead.getRate_homestead()) + ", Price_homestead = " +
                Float.parseFloat(Price_homestead.getText()) + ", Is_active = " + (is_available.isSelected() ? 1 : 0) + " WHERE ID_Homestead = " +
                homestead.getID_homestead();
        Connection_db.executeQuery(query);
        hc.ShowHomesteads();
        Connection_db.closeWindow(actionEvent);
    }
}
