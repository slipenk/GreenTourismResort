package sample.other_windows;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.entertainments.Entertainment_controller;
import sample.homesteads.Homesteads_controller;
import sample.options_enter.Options_enter_controller;
import sample.start_window.Start_window_controller;
import sample.tours.Tours_controller;

import java.io.IOException;

public class Categories_controller {
    @FXML
    private AnchorPane root;
    @FXML
    private Label Label_name;
    @FXML
    private Button Left;
    @FXML
    private Button Right;
    @FXML
    private Button Below;
    @FXML
    private ImageView Back_img;
    @FXML
    private Button All;

    private final int ALL = 1;
    private final int LEFT = 2;
    private final int RIGHT = 3;
    private final int BELOW = 4;

    private int Which_window = 0;

    private String Worker_name;
    public void SetWorker(String s) {
        Worker_name = s;
    }

    public void setWindow(int id) {
        Which_window = id;
    }

    public void setButtons() {
        if(Which_window == 1) {
            Left.setText("Активні");
            Right.setText("Неактивні");
            Below.setVisible(false);
            Label_name.setText("Тури");
        } else if(Which_window == 3) {
            Left.setText("З виходом на озеро");
            Right.setText("Із садом");
            Below.setText("Кемпінг");
            Label_name.setText("Категорії садиб");
        }
        else if(Which_window == 4) {
            Left.setText("Всі");
            Right.setText("Сьогодні");
            Below.setVisible(false);
            All.setVisible(false);
            Label_name.setText("Записи");
        }
    }



    public void initialize() {
        Back_img.setPickOnBounds(true);
    }

    public void All(ActionEvent actionEvent) throws IOException {
        if(Which_window == 1) {
            getWindowTours(ALL);
        } else if(Which_window == 2) {
            getWindowEnter(ALL);
        } else if(Which_window == 3) {
            getWindowHomesteads(ALL);
        }

    }

    public void Go_back(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/start_window/Start_window.fxml"));
        Parent parent = fxmlLoader.load();
        Start_window_controller start_window_controller = fxmlLoader.getController();
        start_window_controller.setWorkers(Worker_name);
        root.getChildren().setAll(parent);
    }

    public void Below(ActionEvent actionEvent) throws IOException {
        if(Which_window == 2) {
            getWindowEnter(BELOW);
        } else if(Which_window == 3) {
            getWindowHomesteads(BELOW);
        }

    }

    public void Right(ActionEvent actionEvent) throws IOException {
        if(Which_window == 1) {
            getWindowTours(RIGHT);
        } else if(Which_window == 2) {
            getWindowEnter(RIGHT);
        } else if(Which_window == 3) {
            getWindowHomesteads(RIGHT);
        } else if(Which_window == 4) {
            getWindowOptions(RIGHT);
        }
    }

    public void Left(ActionEvent actionEvent) throws IOException {
        if(Which_window == 1) {
            getWindowTours(LEFT);
        } else if(Which_window == 2) {
            getWindowEnter(LEFT);
        } else if(Which_window == 3) {
            getWindowHomesteads(LEFT);
        } else if(Which_window == 4) {
            getWindowOptions(LEFT);
        }
    }

    private void getWindowEnter(int Category) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/entertainments/Entertainments.fxml"));
        Parent parent = fxmlLoader.load();
        Entertainment_controller entertainment_controller = fxmlLoader.getController();
        entertainment_controller.setCategory(Category);
        entertainment_controller.setFromStartWindow(true, Worker_name);
        entertainment_controller.ShowEntertainments();
        root.getChildren().setAll(parent);
    }

    private void getWindowTours(int Category) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/tours/tours.fxml"));
        Parent parent = fxmlLoader.load();
        Tours_controller tours_controller = fxmlLoader.getController();
        tours_controller.setCategory(Category, Worker_name);
        tours_controller.ShowTours();
        root.getChildren().setAll(parent);
    }

    private void getWindowHomesteads(int Category) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/homesteads/Homesteads.fxml"));
        Parent parent = fxmlLoader.load();
        Homesteads_controller homesteads_controller = fxmlLoader.getController();
        homesteads_controller.setFromStartWindow(true, Category, Worker_name);
        homesteads_controller.ShowHomesteads();
        root.getChildren().setAll(parent);
    }
    private void getWindowOptions(int Category) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/options_enter/Options_enter.fxml"));
        Parent parent = fxmlLoader.load();
        Options_enter_controller options_enter_controller = fxmlLoader.getController();
        options_enter_controller.setFromStartWindow(true, Category, Worker_name);
        options_enter_controller.ShowOptions();
        root.getChildren().setAll(parent);
    }


}
