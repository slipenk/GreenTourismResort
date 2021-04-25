package sample.tours;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import sample.clients.Add_update_clients;
import sample.clients.Clients_controller;
import sample.db_classes.Clients;
import sample.db_classes.Connection_db;
import sample.db_classes.Entertainments;
import sample.entertainments.Entertainment_controller;

import java.io.IOException;
import java.util.*;

public class Tours_controller {

    public Set<Clients> clients;
    public Set<Entertainments> entertainments;


    public void initialize() {

        clients = new HashSet<>();
        entertainments = new HashSet<>();
    }

    public void AddClients(Clients c) {
        clients.add(c);
    }

    public void DeleteClients(Clients c) {
        clients.remove(c);

    }


    public void AddEntertainments(Entertainments e) {
        entertainments.add(e);
    }

    public void DeleteEntertainments(Entertainments e) {
       entertainments.remove(e);

    }

    public void SetEntertainments() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/entertainments/Entertainments.fxml"));
        Parent parent = fxmlLoader.load();
        Entertainment_controller entertainment_controller = fxmlLoader.getController();
        entertainment_controller.setTours(this);
        entertainment_controller.SetChoiceBox();
        Connection_db.Get_Dialog(parent, 1000, 650);
    }


    public void SetClients() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/clients/Clients.fxml"));
        Parent parent = fxmlLoader.load();
        Clients_controller clients_controller = fxmlLoader.getController();
        clients_controller.setTours(this);
        clients_controller.SetChoiceBox();
        Connection_db.Get_Dialog(parent, 1000, 650);
    }

    public void Add_method(ActionEvent actionEvent) throws IOException {
        SetClients();
    }

    public void Update_method(ActionEvent actionEvent) {
        int i = 0;
    }

    public void Delete_method(ActionEvent actionEvent) throws IOException {
        SetEntertainments();
    }
}
