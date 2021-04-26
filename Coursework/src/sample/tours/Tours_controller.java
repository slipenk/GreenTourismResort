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
import sample.workers.Add_update_workers;
import sample.workers.Workers_controller;

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
    private TableColumn<Tours, ChoiceBox<String>> Clients_col;
    @FXML
    private TableColumn<Tours, ChoiceBox<String>> Enter_col;
    @FXML
    private TableColumn<Tours, ChoiceBox<String>> Worker_col;

    public void initialize() {
        ShowTours();
    }

    public void ShowTours() {
        ObservableList<Tours> list = getTours();

        Breakfast_col.setCellValueFactory(new PropertyValueFactory<>("IsBreakfast_tours"));
        Price_col.setCellValueFactory(new PropertyValueFactory<>("Cost_tour"));
        Date_start_col.setCellValueFactory(new PropertyValueFactory<>("Date_start_tour"));
        Date_end_col.setCellValueFactory(new PropertyValueFactory<>("Date_end_tour"));
        Active_col.setCellValueFactory(new PropertyValueFactory<>("Is_active_tours"));
        Homesteads_col.setCellValueFactory(new PropertyValueFactory<>("homestead"));
        Clients_col.setCellValueFactory(new PropertyValueFactory<>("clientsChoiceBox"));
        Enter_col.setCellValueFactory(new PropertyValueFactory<>("entertainmentsChoiceBox"));
        Worker_col.setCellValueFactory(new PropertyValueFactory<>("workersChoiceBox"));
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
        ObservableList<String> clients_list = FXCollections.observableArrayList();
        ObservableList<String> entertainments_list = FXCollections.observableArrayList();
        ObservableList<String> workers_list = FXCollections.observableArrayList();

        try {
            if(conn != null) {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                Tours tours;
               
                while (rs.next()) {
                    ID_homestead = rs.getInt("ID_homestead");
                    homestead = getHomesteadQuery("SELECT Name_homestead FROM Homesteads WHERE ID_homestead = " + ID_homestead);
                    clients_list = getClientsQuery("SELECT Surname_client, Name_client, Phone_number_client " +
                            "FROM Clients_tours ct " +
                            "JOIN Tour t ON ct.ID_tours = t.ID_tours " +
                            "JOIN Client c ON ct.ID_clients = c.ID_client " +
                            "WHERE ct.ID_tours = " + rs.getInt("ID_tours"));
                    workers_list = getWorkersQuery("SELECT Surname_worker, Name_worker, Phone_number_worker " +
                            "FROM Tours_worker tw " +
                            "JOIN Tour t ON t.ID_tours = tw.ID_tours " +
                            "JOIN Worker w ON tw.ID_workers = w.ID_workers " +
                            "WHERE tw.ID_tours = " + rs.getInt("ID_tours"));
                    entertainments_list = getEnterQuery("SELECT Name_entertainment " +
                            "FROM Tours_entertainment te " +
                            "JOIN Tour t ON t.ID_tours = te.ID_tours " +
                            "JOIN Entertainment e ON te.ID_entertainments = e.ID_Entertainment " +
                            "WHERE te.ID_tours = " + rs.getInt("ID_tours"));

                    tours = new Tours(rs.getInt("ID_tours"), rs.getBoolean("IsBreakfast_tours"),
                            rs.getFloat("Cost_tour"), rs.getDate("Date_start_tour"), rs.getDate("Date_end_tour"), rs.getBoolean("Is_active_tours"),
                            homestead, clients_list, entertainments_list, workers_list);
                    ToursList.add(tours);
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

    private ObservableList<String> getWorkersQuery(String query) {
        Connection conn = Connection_db.GetConnection();
        Statement st;
        ResultSet rs;
        String workers = new String(" ");
        ObservableList<String> workers_list = FXCollections.observableArrayList();
        try {
            if(conn != null) {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                while (rs.next()) {
                    workers = rs.getString("Surname_worker") + " " + rs.getString("Name_worker") + " " + rs.getString("Phone_number_worker");
                    workers_list.add(workers);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workers_list;
    }

    private ObservableList<String> getClientsQuery(String query) {
        Connection conn = Connection_db.GetConnection();
        Statement st;
        ResultSet rs;
        String clients = new String(" ");
        ObservableList<String> clients_list = FXCollections.observableArrayList();
        try {
            if(conn != null) {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                while (rs.next()) {
                    clients = rs.getString("Surname_client") + " " + rs.getString("Name_client") + " " + rs.getString("Phone_number_client");
                    clients_list.add(clients);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clients_list;
    }

    private ObservableList<String> getEnterQuery(String query) {
        Connection conn = Connection_db.GetConnection();
        Statement st;
        ResultSet rs;
        String enters = new String(" ");
        ObservableList<String> enters_list = FXCollections.observableArrayList();
        try {
            if(conn != null) {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                while (rs.next()) {
                    enters = rs.getString("Name_entertainment");
                    enters_list.add(enters);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enters_list;
    }















    public void Add_method(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Add_update_tours.fxml"));
        Parent parent = fxmlLoader.load();
        Add_update_tours add_update_tours = fxmlLoader.getController();
        add_update_tours.setController(this);
        add_update_tours.setAdd_Update(true);
        Connection_db.Get_Dialog(parent, 490, 680);
    }

    public void Update_method(ActionEvent actionEvent) throws IOException {

    }

    public void Delete_method(ActionEvent actionEvent) throws IOException {

    }



}
