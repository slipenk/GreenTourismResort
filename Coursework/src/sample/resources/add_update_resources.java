package sample.resources;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.db_classes.Connection_db;
import sample.db_classes.Entertainments;
import sample.db_classes.Resources;
import sample.entertainments.Entertainment_controller;

import java.util.ArrayList;

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

    public void initialize() {
        Price.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*(\\.\\d*)?")) {
                    Price.setText(oldValue);
                }
            }
        });

        Count.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*(\\d*)?")) {
                    Count.setText(oldValue);
                }
            }
        });



    }

    public void setEntertainmentList() {
        ObservableList<String> enter = FXCollections.observableArrayList();
        for(Entertainments e : entertainments) {
            enter.add(e.getName_entertainment());
        }
        Entertainment.setItems(enter);
    }

    public void setValues() {
        Name.setText(resource.getName_Resources());
        Count.setText(String.valueOf(resource.getCount_resources()));
        Price.setText(String.valueOf(resource.getPrice_one()));
        Entertainment.setValue(resource.getEntertainment());
        if(!Add_Update) {
            Name_window.setText("Оновити ресурс");
            Add_btn.setText("Оновити");
        }
    }


    public void Add_method(ActionEvent actionEvent) {
        String query;

        if(Add_Update) {
            query = "INSERT INTO Resources VALUES ( N'" +
                    Name.getText() + "', " +
                    Count.getText() + ", " +
                    Price.getText() + ", " +
                    getEntertainmentID(Entertainment.getValue()) + ")";
        } else {
            query = "UPDATE Resources SET Name_Resources = N'" + Name.getText()  + "', Count_resources = " +
                    Count.getText() + ", Price_one = " + Price.getText() + ", ID_entertainment = " +
                    getEntertainmentID(Entertainment.getValue())
                    + " WHERE ID_Resources = " +
                    resource.getID_Resources();
        }

        Connection_db.executeQuery(query);
        rc.ShowResources();
        Connection_db.closeWindow(actionEvent);

    }

    private int getEntertainmentID(String name) {
        for(Entertainments e : entertainments) {
            if(e.getName_entertainment().equals(name))
                return e.getID_Entertainment();
        }
        return 0;
    }
}
