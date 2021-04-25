package sample.workers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.clients.Add_update_clients;
import sample.db_classes.Clients;
import sample.db_classes.Connection_db;
import sample.db_classes.Homesteads;
import sample.db_classes.Workers;
import sample.tours.Tours_controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;

public class Workers_controller {

    @FXML
    private TableView<Workers> table_workers;
    @FXML
    private TableColumn<Workers, String> Surname_col;
    @FXML
    private TableColumn<Workers, String> Name_col;
    @FXML
    private TableColumn<Workers, String> Middle_name_col;
    @FXML
    private TableColumn<Workers, String> Phone_col;
    @FXML
    private TableColumn<Workers, Date> Date_birth_col;
    @FXML
    private TableColumn<Workers, String> Job_col;
    @FXML
    private TextField search_field;
    @FXML
    private Button Add_workers_tour;
    @FXML
    private Label Label_set;

    private Tours_controller tours_controller;

    private ObservableList<String> workers_list_str;


    public void setTours(Tours_controller tours_controller) {
        this.tours_controller = tours_controller;
    }


    public void initialize() {
        workers_list_str = FXCollections.observableArrayList();
        ShowWorkers();
    }

    public void ShowWorkers() {
        ObservableList<Workers> list = getWorkers();

        Surname_col.setCellValueFactory(new PropertyValueFactory<>("Surname_worker"));
        Name_col.setCellValueFactory(new PropertyValueFactory<>("Name_worker"));
        Middle_name_col.setCellValueFactory(new PropertyValueFactory<>("Middle_name_worker"));
        Phone_col.setCellValueFactory(new PropertyValueFactory<>("Phone_number_worker"));
        Date_birth_col.setCellValueFactory(new PropertyValueFactory<>("Date_birth_worker"));
        Job_col.setCellValueFactory(new PropertyValueFactory<>("Job_worker"));



        FilteredList<Workers> filteredData = new FilteredList<>(list, b -> true);

        search_field.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(worker -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if(worker.getSurname_worker().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(worker.getPhone_number_worker().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(worker.getJob_worker().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Workers> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(table_workers.comparatorProperty());

        table_workers.setItems(sortedData);

    }

    private ObservableList<Workers> getWorkers() {
        ObservableList<Workers> WorkersList = FXCollections.observableArrayList();
        Connection conn = Connection_db.GetConnection();
        String query = "SELECT * FROM Worker";
        Statement st;
        ResultSet rs;

        try {
            if(conn != null) {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                Workers workers;
                while (rs.next()) {
                    workers = new Workers(rs.getInt("ID_workers"), rs.getString("Surname_worker"),
                            rs.getString("Name_worker"), rs.getString("Middle_name_worker"), rs.getString("Phone_number_worker"),
                            rs.getDate("Date_birth_worker"), rs.getString("Job_worker"));
                    WorkersList.add(workers);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return WorkersList;
    }

    public void Add_method(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Add_update_workers.fxml"));
        Parent parent = fxmlLoader.load();
        Add_update_workers add_update_workers = fxmlLoader.getController();
        add_update_workers.setController(this);
        add_update_workers.setAdd_Update(true);
        Connection_db.Get_Dialog(parent, 490, 680);
    }

    public void Update_method(ActionEvent actionEvent) throws IOException {
        Workers workers = table_workers.getSelectionModel().getSelectedItem();
        if(workers != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Add_update_workers.fxml"));
            Parent parent = fxmlLoader.load();
            Add_update_workers add_update_workers = fxmlLoader.getController();
            add_update_workers.setController(this);
            add_update_workers.setAdd_Update(false);
            add_update_workers.setWorker(workers);
            add_update_workers.setValues();
            Connection_db.Get_Dialog(parent, 490, 680);
        }
    }

    public void Delete_method(ActionEvent actionEvent) {
        Workers workers = table_workers.getSelectionModel().getSelectedItem();
        if(workers != null) {
            String query = "DELETE FROM Worker WHERE ID_workers = " + workers.getID_workers();
            Connection_db.Cancel_Dialog(query);
            ShowWorkers();
        }
    }

    public void Add_worker_tour(ActionEvent actionEvent) {
        Workers workers = table_workers.getSelectionModel().getSelectedItem();
        if(workers != null) {
            tours_controller.AddWorkers(workers);
            SetLabel();
            Add_workers_tour.setVisible(false);

        }
    }

    public void Delete_worker_tour(ActionEvent actionEvent) {
        Add_workers_tour.setVisible(true);
        tours_controller.AddHomesteads(null);
        Label_set.setText(" ");
    }

    public void SetLabel() {
        if(tours_controller.getWorkers() != null)
            Label_set.setText(tours_controller.getWorkers().getSurname_worker());
    }
}
