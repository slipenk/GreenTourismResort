package sample.tours;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.clients.Add_update_clients;
import sample.clients.Clients_controller;
import sample.db_classes.*;
import sample.entertainments.Entertainment_controller;
import sample.homesteads.Homesteads_controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class Tours_controller {

    @FXML
    private TableView<Tours> table_tours;
    @FXML
    private TableColumn<Tours, Boolean> Breakfast_col;
    @FXML
    private TableColumn<Tours, Float> Price_col;
    @FXML
    private TableColumn<Tours, Date> Date_start_col;
    @FXML
    private TableColumn<Tours, Date> Date_end_col;
    @FXML
    private TableColumn<Tours, Boolean> Active_col;
    @FXML
    private TableColumn<Tours, String> Homesteads_col;
    @FXML
    private TableColumn<Tours, ChoiceBox<Clients>> Clients_col;
    @FXML
    private TableColumn<Tours, ChoiceBox<Entertainments>> Enter_col;

    private Set<Clients> clients;
    private Set<Entertainments> entertainments;
    private Homesteads homesteads;
    private Workers workers;


    public void initialize() {
        clients = new HashSet<>();
        entertainments = new HashSet<>();
        ShowTours();
    }

    private void ShowTours() {
        ObservableList<Tours> list = getTours();

        Breakfast_col.setCellValueFactory(new PropertyValueFactory<>("IsBreakfast_tours"));
        Price_col.setCellValueFactory(new PropertyValueFactory<>("Cost_tour"));
        Date_start_col.setCellValueFactory(new PropertyValueFactory<>("Date_start_tour"));
        Date_end_col.setCellValueFactory(new PropertyValueFactory<>("Date_end_tour"));
        Active_col.setCellValueFactory(new PropertyValueFactory<>("Is_active_tours"));
        Homesteads_col.setCellValueFactory(new PropertyValueFactory<>("homestead"));
        Clients_col.setCellValueFactory(new PropertyValueFactory<>("clientsChoiceBox"));
        Enter_col.setCellValueFactory(new PropertyValueFactory<>("entertainmentsChoiceBox"));

        table_tours.setItems(list);
    }

    private ObservableList<Tours> getTours() {
        ObservableList<Tours> ToursList = FXCollections.observableArrayList();
        Connection conn = Connection_db.GetConnection();
        String query = "SELECT * FROM Tour";
        Statement st;
        ResultSet rs;
        int ID_homestead;
        String homestead;
        try {
            if(conn != null) {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                Tours tours;
                while (rs.next()) {
                    ID_homestead = rs.getInt("ID_homestead");
                   /* homestead = getHomesteadQuery("SELECT Name_homestead FROM Homesteads WHERE ID_homestead = " + ID_homestead);
                    tours = new Tours(rs.getInt("ID_tours"), rs.getBoolean("IsBreakfast_tours"),
                            rs.getFloat("Cost_tour"), rs.getDate("Date_start_tour"), rs.getDate("Date_end_tour"), rs.getBoolean("Is_active_tours"),
                            homestead, );*/
                    //HomesteadsList.add(homesteads);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ToursList;
    }

    private String getHomesteadQuery(String query) {
        Connection conn = Connection_db.GetConnection();
        Statement st;
        ResultSet rs;
        String homesteads = new String(" ");
        try {
            if(conn != null) {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                while (rs.next()) {
                   homesteads = rs.getString("Name_homestead");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return homesteads;
    }


    public Set<Clients> getClients() {
        return clients;
    }
    public Set<Entertainments> getEntertainments() {
        return entertainments;
    }
    public Homesteads getHomesteads() {
        return homesteads;
    }
    public Workers getWorkers() {
        return workers;
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

    public void AddHomesteads(Homesteads h) {
        homesteads = h;
    }

    public void AddWorkers(Workers h) {
        workers = h;
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

    public void SetHomestead() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/homesteads/homesteads.fxml"));
        Parent parent = fxmlLoader.load();
        Homesteads_controller homesteads_controller = fxmlLoader.getController();
        homesteads_controller.setTours(this);
        homesteads_controller.SetLabel();
        Connection_db.Get_Dialog(parent, 1000, 650);
    }



    public void Add_method(ActionEvent actionEvent) throws IOException {
        SetClients();
    }

    public void Update_method(ActionEvent actionEvent) throws IOException {
        SetHomestead();
    }

    public void Delete_method(ActionEvent actionEvent) throws IOException {
        SetEntertainments();
    }



}
