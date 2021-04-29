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
import java.util.Objects;

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
        Phone_number.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*(\\d*)?")) {
                    Phone_number.setText(oldValue);
                }
            }
        });

        Date_birth.setConverter(new StringConverter<LocalDate>() {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                Date_birth.setPromptText(pattern.toLowerCase());
            }

            @Override public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

        Date_reg.setConverter(new StringConverter<LocalDate>() {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                Date_reg.setPromptText(pattern.toLowerCase());
            }

            @Override public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

        Date_birth.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) > 0 );
            }
        });
        Date_reg.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) > 0 );
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
        if(!Add_Update) {
            Name_window.setText("Оновити клієнта");
            Add_btn.setText("Оновити");
        }
    }



    public void Add_method(ActionEvent actionEvent)  {

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            format.parse(Date_birth.getValue().toString());
            format.parse(Date_reg.getValue().toString());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Правильний формат дати \"yyyy-MM-dd\" ", ButtonType.OK);
            alert.getDialogPane().getStylesheets().add(
                    Objects.requireNonNull(getClass().getResource("/sample/style.css")).toExternalForm());
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
               return;
            }
        }

        String query;
        if(Add_Update) {
            query = "INSERT INTO Client VALUES ( N'" +
                    Surname.getText().trim() + "', N'" +
                    Name.getText().trim() + "', N'" +
                    Middle_name.getText().trim() + "', N'" +
                    Phone_number.getText().trim() + "', '" +
                    Date.valueOf(Date_birth.getValue()) + "', N'" +
                    Id_document.getText().trim() + "', '" +
                    Date.valueOf(Date_reg.getValue()) + "')";
        } else {
            query = "UPDATE Client SET Surname_client = N'" + Surname.getText().trim()  + "', Name_client = N'" +
                    Name.getText().trim() + "', Middle_name_client = N'" + Middle_name.getText().trim() + "', Phone_number_client = N'" +
                    Phone_number.getText().trim() + "', Date_birth_client = '" + Date.valueOf(Date_birth.getValue()) + "', Document_id_client = N'" +
                    Id_document.getText().trim() + "', Registration_date_client = '" + Date.valueOf(Date_reg.getValue()) + "' WHERE ID_client = " +
                    client.getID_client();
        }

        Connection_db.executeQuery(query);
        cc.ShowClients();
        Connection_db.closeWindow(actionEvent);
    }
}
