package sample.clients;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.db_classes.Clients;
import sample.db_classes.Connection_db;
import sample.db_classes.Homesteads;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;

public class Clients_controller {
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

    public void initialize() {
        ShowClients();
    }

    public void ShowClients() {
        ObservableList<Clients> list = getClients();

        Surname_col.setCellValueFactory(new PropertyValueFactory<>("Surname_client"));
        Name_col.setCellValueFactory(new PropertyValueFactory<>("Name_client"));
        Middle_name_col.setCellValueFactory(new PropertyValueFactory<>("Middle_name_client"));
        Phone_col.setCellValueFactory(new PropertyValueFactory<>("Phone_number_client"));
        Date_birth_col.setCellValueFactory(new PropertyValueFactory<>("Date_birth_client"));
        Document_col.setCellValueFactory(new PropertyValueFactory<>("Document_id_client"));
        Registration_date_col.setCellValueFactory(new PropertyValueFactory<>("Registration_date_client"));

        table_clients.setItems(list);
    }

    private ObservableList<Clients> getClients() {
        ObservableList<Clients> ClientsList = FXCollections.observableArrayList();
        Connection conn = Connection_db.GetConnection();
        String query = "SELECT * FROM Client";
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
        Connection_db.Get_Dialog(parent);
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
        Connection_db.Get_Dialog(parent);
        }
    }

    public void Delete_method(ActionEvent actionEvent) {
        Clients clients = table_clients.getSelectionModel().getSelectedItem();
        if(clients != null) {
            String query = "DELETE FROM Client WHERE ID_client = " + clients.getID_client();
            Cancel_Dialog(query); }
    }

    private void Cancel_Dialog(String query)
    {
        ButtonType OK = new ButtonType("Видалити", ButtonBar.ButtonData.OK_DONE);
        ButtonType CANCEL = new ButtonType("Скасувати", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.NONE, "Ви впевнені, що хочете видалити?", OK, CANCEL);
        alert.showAndWait();
        if (alert.getResult() == OK) {
            Connection_db.executeQuery(query);
            ShowClients();
        }
    }
}
