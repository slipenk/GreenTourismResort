package sample.clients;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import sample.db_classes.Clients;
import sample.db_classes.Connection_db;
import sample.db_classes.Homesteads;
import sample.homesteads.Homesteads_controller;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Add_update_clients {

    @FXML
    private Button Add_btn;
    @FXML
    private Label Name_window;
    @FXML
    private TextField Id_document;
    @FXML
    private TextField Surname;
    @FXML
    private TextField Name;
    @FXML
    private TextField Middle_name;
    @FXML
    private TextField Phone_number;
    @FXML
    private DatePicker Date_birth;
    @FXML
    private DatePicker Date_reg;

    private Clients_controller cc;
    private boolean Add_Update;
    private Clients client;

    public void setController(Clients_controller cc) {
        this.cc = cc;
    }
    public void setAdd_Update(boolean b) {
        Add_Update = b;
    }
    public void setClient(Clients clients) {
        client = clients;
    }

    public void initialize() {

        Id_document.setText(" ");
        Surname.setText(" ");
        Name.setText(" ");
        Middle_name.setText(" ");
        Phone_number.setText(" ");
        Date_birth.setValue(LocalDate.now());
        Date_reg.setValue(LocalDate.now());

        Phone_number.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("[0-9]")) {
                    Phone_number.setText(oldValue);
                }
            }
        });

    }

    public void setValues() {
        Surname.setText(client.getSurname_client());
        Name.setText(client.getName_client());
        Middle_name.setText(client.getMiddle_name_client());
        Phone_number.setText(client.getPhone_number_client());
        Date_birth.setValue(client.getDate_birth_client().toLocalDate());
        Id_document.setText(client.getDocument_id_client());
        Date_reg.setValue(client.getRegistration_date_client().toLocalDate());
    }



    public void Add_method(ActionEvent actionEvent)  {

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            format.parse(Date_birth.getValue().toString());
            format.parse(Date_reg.getValue().toString());
        } catch (ParseException e) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Правильний формат дати \"yyyy-MM-dd\" ", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
               return;
            }
        }

        String query;
        if(Add_Update) {
            query = "INSERT INTO Client VALUES ( N'" +
                    Surname.getText() + "', N'" +
                    Name.getText() + "', N'" +
                    Middle_name.getText() + "', N'" +
                    Phone_number.getText() + "', " +
                    Date.valueOf(Date_birth.getValue()) + ", N'" +
                    Id_document.getText() + "', " +
                    Date.valueOf(Date_reg.getValue());
        } else {
            Name_window.setText("Оновити клієнта");
            Add_btn.setText("Оновити");
            query = "UPDATE Homesteads SET Surname_client = N'" + Surname.getText()  + "', Name_client = N'" +
                    Name.getText() + "', Middle_name_client = N'" + Middle_name.getText() + "', Phone_number_client = N'" +
                    Phone_number.getText() + "', Date_birth_client = " + Date.valueOf(Date_birth.getValue()) + ", Document_id_client = N'" +
                    Id_document.getText() + "', Registration_date_client = " + Date.valueOf(Date_reg.getValue()) + " WHERE ID_client = " +
                    client.getID_client();
        }

        Connection_db.executeQuery(query);
        cc.ShowClients();
        Connection_db.closeWindow(actionEvent);
    }
}
