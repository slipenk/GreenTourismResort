package sample.login;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import sample.db_classes.Connection_db;
import sample.db_classes.Entertainments;
import sample.db_classes.Workers;
import sample.start_window.Start_window_controller;
import sample.workers.Add_update_workers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class Login_controller {
    @FXML
    private TextField Login_field;
    @FXML
    private PasswordField pass_field;
    @FXML
    private Label error_label;
    @FXML
    private AnchorPane root;

    public void initialize() {
        Login_field.setPromptText("Логін");
        pass_field.setPromptText("Пароль");
    }

    public void Login_action(ActionEvent actionEvent) throws IOException {
        if(Login_field.getText().isBlank() && pass_field.getText().isBlank()) {
            error_label.setText("Будь ласка, введіть логін та пароль");
            return;
        } else if(Login_field.getText().isBlank()) {
            error_label.setText("Будь ласка, введіть логін");
            return;
        } else if(pass_field.getText().isBlank()) {
            error_label.setText("Будь ласка, введіть пароль");
            return;
        }
        int id_worker = validateLogin();
        if(id_worker < 0) {
            return;
        }
        String s = GetWorker(id_worker);


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/start_window/Start_window.fxml"));
        Parent parent = fxmlLoader.load();
        Start_window_controller start_window_controller = fxmlLoader.getController();
        start_window_controller.setWorkers(s);
        root.getChildren().setAll(parent);

    }

    public int validateLogin() {
        Connection conn = Connection_db.GetConnection();

        String query = "SELECT ID_workers FROM Worker_account WHERE Login_worker = N'" + Login_field.getText().trim() +
                "' AND Password_worker = N'" + pass_field.getText().trim() + "'";
        Statement st;
        ResultSet rs;
        int id = -1;
        boolean enter = false;
        int count = 0;
        try {
            if(conn != null) {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                while (rs.next()) {
                    enter = true;
                    ++count;
                    id = rs.getInt("ID_workers");
                }
                if(!enter) {
                    error_label.setText("Неправильний логін або пароль. Спробуйте ще раз");
                    return -1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    private String GetWorker(int id) {
        String query = "SELECT w.Surname_worker, w.Name_worker FROM Worker w WHERE w.ID_workers = " + id;

        Connection conn = Connection_db.GetConnection();

        Statement st;
        ResultSet rs;
        StringBuilder workers = new StringBuilder(" ");
        try {
            if(conn != null) {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                while (rs.next()) {
                    workers.append(rs.getString("Surname_worker")).append(" ").append(rs.getString("Name_worker"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workers.toString();

    }

}
