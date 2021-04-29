package sample.start_window;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import sample.clients.Clients_controller;
import sample.tours.Tours_controller;

import java.io.IOException;

public class Start_window_controller {
    @FXML
    private AnchorPane root;
    @FXML
    private Label Worker_name;

    private String worker_name;

    public void setWorkers(String s) {
        worker_name = s;
        Worker_name.setText(s);

    }

    public void Go_tours(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/tours/tours.fxml"));
        Parent parent = fxmlLoader.load();
        Tours_controller tours_controller = fxmlLoader.getController();
        tours_controller.SetWorker(worker_name);
        root.getChildren().setAll(parent);
    }

    public void Go_clients(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/clients/Clients.fxml"));
        Parent parent = fxmlLoader.load();
        Clients_controller clients_controller = fxmlLoader.getController();
        clients_controller.setFromStartWindow(true, worker_name);
        root.getChildren().setAll(parent);

    }

    public void Go_enters(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/other_windows/Categories.fxml"));
        Parent parent = fxmlLoader.load();
        root.getChildren().setAll(parent);
    }

    public void Go_workers(ActionEvent actionEvent) {
    }

    public void Go_options(ActionEvent actionEvent) {
    }

    public void Go_homesteads(ActionEvent actionEvent) {
    }

    public void Exit(ActionEvent actionEvent) {
    }
}
