package sample.resources;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.db_classes.Entertainments;
import sample.db_classes.Resources;
import sample.entertainments.Entertainment_controller;

public class add_update_resources {
    @FXML
    private Label Name_window;
    @FXML
    private Button Add_btn;
    @FXML
    private TextField Name;
    @FXML
    private TextField Price;
    @FXML
    private ChoiceBox<String> Entertainment;
    @FXML
    private TextField Count;

    private resources_controller rc;
    private boolean Add_Update;
    private Resources resource;
    private ObservableList<Entertainments> entertainments;

    public void setController(resources_controller rc) {
        this.rc = rc;
    }
    public void setAdd_Update(boolean b) {
        Add_Update = b;
    }
    public void setResource(Resources resources) {
        resource = resources;
    }
    public void setEntertainment(ObservableList<Entertainments> entertainments) {
        this.entertainments = entertainments;
    }


    public void Add_method(ActionEvent actionEvent) {
    }
}
