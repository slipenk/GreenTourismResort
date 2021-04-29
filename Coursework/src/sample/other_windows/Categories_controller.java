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
import sample.clients.Clients_controller;
import sample.entertainments.Entertainment_controller;

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

    public void initialize() {
        Back_img.setPickOnBounds(true);
    }

    public void All(ActionEvent actionEvent) throws IOException {
        getWindow(ALL);
    }

    public void Go_back(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/start_window/Start_window.fxml"));
        Parent parent = fxmlLoader.load();
        root.getChildren().setAll(parent);
    }

    public void Below(ActionEvent actionEvent) throws IOException {
        getWindow(BELOW);
    }

    public void Right(ActionEvent actionEvent) throws IOException {
        getWindow(RIGHT);
    }

    public void Left(ActionEvent actionEvent) throws IOException {
        getWindow(LEFT);
    }

    private void getWindow(int Category) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/entertainments/Entertainments.fxml"));
        Parent parent = fxmlLoader.load();
        Entertainment_controller entertainment_controller = fxmlLoader.getController();
        entertainment_controller.setCategory(Category);
        entertainment_controller.setFromStartWindow(true);
        entertainment_controller.ShowEntertainments();
        root.getChildren().setAll(parent);
    }


}
