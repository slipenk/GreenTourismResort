package sample.workers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import sample.db_classes.Connection_db;
import sample.db_classes.Workers;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Add_update_workers {
    @FXML
    private Label Name_window;
    @FXML
    private Button Add_btn;
    @FXML
    private TextField Job_name;
    @FXML
    private TextField Surname;
    @FXML
    private TextField Middle_name;
    @FXML
    private TextField Phone_number_worker;
    @FXML
    private DatePicker Date_birth_worker;
    @FXML
    private TextField Name;

    private Workers_controller cc;
    private boolean Add_Update;
    private Workers worker;

    public void setController(Workers_controller cc) {
        this.cc = cc;
    }
    public void setAdd_Update(boolean b) {
        Add_Update = b;
    }
    public void setWorker(Workers workers) {
        worker = workers;
    }

    public void initialize() {
        Phone_number_worker.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*(\\d*)?")) {
                    Phone_number_worker.setText(oldValue);
                }
            }
        });

        Date_birth_worker.setConverter(new StringConverter<LocalDate>() {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                Date_birth_worker.setPromptText(pattern.toLowerCase());
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


        Date_birth_worker.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) > 0 );
            }
        });


    }

    public void setValues() {
        Surname.setText(worker.getSurname_worker());
        Name.setText(worker.getName_worker());
        Middle_name.setText(worker.getMiddle_name_worker());
        Phone_number_worker.setText(worker.getPhone_number_worker());
        Date_birth_worker.setValue(worker.getDate_birth_worker().toLocalDate());
        Job_name.setText(worker.getJob_worker());
        if(!Add_Update) {
            Name_window.setText("Оновити гіда");
            Add_btn.setText("Оновити");
        }
    }



    public void Add_method(ActionEvent actionEvent) {
        DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        try {
            formatDate.parse(Date_birth_worker.getValue().toString());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Правильний формат дати \"yyyy-MM-dd\" ", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                return;
            }
        }
        String query;
        if(Add_Update) {
            query = "INSERT INTO Worker VALUES ( N'" +
                    Surname.getText().trim() + "', N'" +
                    Name.getText().trim() + "', N'" +
                    Middle_name.getText().trim() + "', N'" +
                    Phone_number_worker.getText().trim() + "', '" +
                    Date.valueOf(Date_birth_worker.getValue()) + "', N'" +
                    Job_name.getText().trim() + "')";
        } else {
            query = "UPDATE Worker SET Surname_worker = N'" + Surname.getText().trim()  + "', Name_worker = N'" +
                    Name.getText().trim() + "', Middle_name_worker = N'" + Middle_name.getText().trim() + "', Phone_number_worker = N'" +
                    Phone_number_worker.getText().trim() + "', Date_birth_worker = '" + Date.valueOf(Date_birth_worker.getValue()) + "', Job_worker = N'" +
                    Job_name.getText().trim() +"' WHERE ID_workers = " +
                    worker.getID_workers();
        }

        Connection_db.executeQuery(query);
        cc.ShowWorkers();
        Connection_db.closeWindow(actionEvent);
    }

}
