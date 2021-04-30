package sample.clients;

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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.db_classes.Entertainments;
import sample.start_window.Start_window_controller;
import sample.tours.Add_update_tours;
import sample.tours.Tours_controller;
import sample.db_classes.Clients;
import sample.db_classes.Connection_db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Clients_controller {
    @FXML
    private ImageView back_img;
    @FXML
    private AnchorPane root;
    @FXML
    private Button Add_client_tour;
    @FXML
    private Button Delete_client_tour;
    @FXML
    private ChoiceBox<String> Clients_tour;
    @FXML
    private TextField search_field;
    @FXML
    private TableView<Clients> table_clients;
    @FXML
    private TableColumn<Clients, String> Surname_col;
    @FXML
    private TableColumn<Clients, String> Name_col;
    @FXML
    private TableColumn<Clients, String> Middle_name_col;
    @FXML
    private TableColumn<Clients, String> Phone_col;
    @FXML
    private TableColumn<Clients, Date> Date_birth_col;
    @FXML
    private TableColumn<Clients, String> Document_col;
    @FXML
    private TableColumn<Clients, Date> Registration_date_col;

    private Add_update_tours tours_controller;

    private ObservableList<String> clients_list_str;

    private boolean FromStartWindow;
    private String Worker_name;

    public void setFromStartWindow(boolean b, String s) {
        Worker_name = s;
        FromStartWindow = b;
        if(b) {
            Add_client_tour.setVisible(false);
            Delete_client_tour.setVisible(false);
            Clients_tour.setVisible(false);
        }
    }


    public void setTours(Add_update_tours tours_controller) {
        this.tours_controller = tours_controller;
    }


    public void initialize() {
        back_img.setPickOnBounds(true);
        clients_list_str = FXCollections.observableArrayList();
        ShowClients();
    }

    public void ShowClients() {

        ObservableList<Clients> list = getClients("SELECT * FROM Client");

        Surname_col.setCellValueFactory(new PropertyValueFactory<>("Surname_client"));
        Name_col.setCellValueFactory(new PropertyValueFactory<>("Name_client"));
        Middle_name_col.setCellValueFactory(new PropertyValueFactory<>("Middle_name_client"));
        Phone_col.setCellValueFactory(new PropertyValueFactory<>("Phone_number_client"));
        Date_birth_col.setCellValueFactory(new PropertyValueFactory<>("Date_birth_client"));
        Document_col.setCellValueFactory(new PropertyValueFactory<>("Document_id_client"));
        Registration_date_col.setCellValueFactory(new PropertyValueFactory<>("Registration_date_client"));

        FilteredList<Clients> filteredData = new FilteredList<>(list, b -> true);

        search_field.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(client -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if(client.getSurname_client().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(client.getDocument_id_client().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(client.getPhone_number_client().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(client.getDate_birth_client().toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(client.getRegistration_date_client().toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Clients> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(table_clients.comparatorProperty());


        table_clients.setItems(sortedData);
        table_clients.refresh();
    }

    public static ObservableList<Clients> getClients(String query) {
        ObservableList<Clients> ClientsList = FXCollections.observableArrayList();
        Connection conn = Connection_db.GetConnection();

        Statement st;
        ResultSet rs;

        try {
            if(conn != null) {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                Clients clients;
                while (rs.next()) {
                    clients = new Clients(rs.getInt("ID_client"), rs.getString("Surname_client"),
                            rs.getString("Name_client"), rs.getString("Middle_name_client"), rs.getString("Phone_number_client"), rs.getDate("Date_birth_client"),
                            rs.getString("Document_id_client"), rs.getDate("Registration_date_client"));
                    ClientsList.add(clients);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ClientsList;
    }

    public void Add_method(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add_update_clients.fxml"));
        Parent parent = fxmlLoader.load();
        Add_update_clients add_update_clients = fxmlLoader.getController();
        add_update_clients.setController(this);
        add_update_clients.setAdd_Update(true);
        Connection_db.Get_Dialog(parent, 490, 680);
    }

    public void Update_method(ActionEvent actionEvent) throws IOException {
        Clients clients = table_clients.getSelectionModel().getSelectedItem();
        if(clients != null) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add_update_clients.fxml"));
        Parent parent = fxmlLoader.load();
        Add_update_clients add_update_clients = fxmlLoader.getController();
        add_update_clients.setController(this);
        add_update_clients.setAdd_Update(false);
        add_update_clients.setClient(clients);
        add_update_clients.setValues();
        Connection_db.Get_Dialog(parent, 490, 680);
        }
    }

    public void Delete_method(ActionEvent actionEvent) {
        Clients clients = table_clients.getSelectionModel().getSelectedItem();
        if(clients != null) {

            String query = "BEGIN TRY BEGIN TRAN " +
                    "DELETE FROM Clients_tours  WHERE ID_clients = " + clients.getID_client() +
                    " DELETE FROM Client WHERE ID_client = " + clients.getID_client() +
                    " COMMIT TRAN END TRY BEGIN CATCH " +
                    "SELECT error_message() AS ErrorMessage " +
                    "ROLLBACK TRAN END CATCH";

            new Connection_db().Cancel_Dialog(query);
            ShowClients();
        }
    }


    public void Add_client_tour(ActionEvent actionEvent) {
        Clients clients = table_clients.getSelectionModel().getSelectedItem();
        if(clients != null) {
            tours_controller.AddClients(clients);
            clients_list_str.clear();
            SetChoiceBox();
        }
    }

    public void Delete_client_tour(ActionEvent actionEvent) {
        Clients lastElement = null;
        if(tours_controller.getClients().size() != 0) {
            Iterator<Clients> iterator = tours_controller.getClients().iterator();
            while(iterator.hasNext()){
                lastElement = iterator.next();
                if(lastElement.getSurname_client().equals(Clients_tour.getValue())) {
                    break;
                }
            }
            tours_controller.DeleteClients(lastElement);;
        }

            clients_list_str.clear();
            SetChoiceBox();
    }

    public void SetChoiceBox() {
        for(Clients c : tours_controller.getClients()) {
            clients_list_str.add(c.getSurname_client());
        }
        Clients_tour.setItems(clients_list_str);
        if(clients_list_str.size() != 0) {
            Clients_tour.setValue(clients_list_str.get(0));
        }
    }

    public void Go_back(MouseEvent mouseEvent) throws IOException {
        if(FromStartWindow) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/start_window/Start_window.fxml"));
            Parent parent = fxmlLoader.load();
            Start_window_controller start_window_controller = fxmlLoader.getController();
            start_window_controller.setWorkers(Worker_name);
            root.getChildren().setAll(parent);
        } else {
            Connection_db.closeWindowImg(mouseEvent);
        }
    }
}
