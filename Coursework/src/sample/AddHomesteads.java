package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.db_classes.Homesteads;

import java.sql.Connection;
import java.sql.Statement;

public class AddHomesteads {
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
    private Button Add_btn;
    @FXML
    private TextField Price_homestead;
    @FXML
    private TextField Name_homestead;
    @FXML
    private ChoiceBox<Byte> Number_of_rooms;

    private Homesteads_controller hc;

    public void setWindow(Homesteads_controller hc) {
        this.hc = hc;
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

    public void Add_method(ActionEvent actionEvent) {
        String query = "INSERT INTO Homesteads VALUES ( N'" +
                Name_homestead.getText() + "', " +
                Number_of_beds.getValue()  + ", " +
                Number_of_rooms.getValue() + ", " +
                Number_of_floors.getValue() + ", " +
                (Is_air_conditioning.isSelected() ? 1 : 0) + ", " +
                (Is_safe.isSelected()  ? 1 : 0) + ", " +
                (Is_Wi_Fi.isSelected()  ? 1 : 0) + ", " +
                (Is_ref.isSelected()  ? 1 : 0) + ", " +
                (Is_Iron.isSelected()  ? 1 : 0) + ", " +
                (Is_Hair.isSelected()  ? 1 : 0) + ", " +
                0 + ", " +
                Float.parseFloat(Price_homestead.getText()) + ", " +
                0 + ") ";
        executeQuery(query);
        hc.ShowHomesteads();
        closeWindow(actionEvent);

    }

    private void executeQuery(String query) {
        Connection conn = hc.GetConnection();
        Statement st;
        try {
            assert conn != null;
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void closeWindow(ActionEvent actionEvent) {
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
