package sample.start_window;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import sample.clients.Clients_controller;
import sample.homesteads.Homesteads_controller;
import sample.options_enter.Options_enter_controller;
import sample.other_windows.Categories_controller;
import sample.tours.Tours_controller;
import sample.workers.Workers_controller;

import java.io.IOException;

public class Start_window_controller {
    @FXML
    private AnchorPane root;
    @FXML
    private Label Worker_name;

    private String worker_name;

    private final int TOUR = 1;
    private final int ENTER = 2;
    private final int HOME = 3;
    private final int OPTIONS = 4;

    public void setWorkers(String s) {
        worker_name = s;
        Worker_name.setText(s);

    }

    public void Go_tours(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/other_windows/Categories.fxml"));
        Parent parent = fxmlLoader.load();
        Categories_controller categories_controller = fxmlLoader.getController();
        categories_controller.SetWorker(worker_name);
        categories_controller.setWindow(TOUR);
        categories_controller.setButtons();
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
        Categories_controller categories_controller = fxmlLoader.getController();
        categories_controller.SetWorker(worker_name);
        categories_controller.setWindow(ENTER);
        root.getChildren().setAll(parent);
    }

    public void Go_workers(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/workers/Workers.fxml"));
        Parent parent = fxmlLoader.load();
        Workers_controller workers_controller = fxmlLoader.getController();
        workers_controller.setFromStartWindow(true, worker_name);
        root.getChildren().setAll(parent);
    }

    public void Go_options(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/other_windows/Categories.fxml"));
        Parent parent = fxmlLoader.load();
        Categories_controller categories_controller = fxmlLoader.getController();
        categories_controller.SetWorker(worker_name);
        categories_controller.setWindow(OPTIONS);
        categories_controller.setButtons();
        root.getChildren().setAll(parent);
    }

    public void Go_homesteads(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/other_windows/Categories.fxml"));
        Parent parent = fxmlLoader.load();
        Categories_controller categories_controller = fxmlLoader.getController();
        categories_controller.SetWorker(worker_name);
        categories_controller.setWindow(HOME);
        categories_controller.setButtons();
        root.getChildren().setAll(parent);
    }

    public void Exit(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/login/Login.fxml"));
        Parent parent = fxmlLoader.load();
        root.getChildren().setAll(parent);
    }


}
