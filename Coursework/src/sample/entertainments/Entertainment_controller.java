package sample.entertainments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.clients.Add_update_clients;
import sample.db_classes.Clients;
import sample.db_classes.Connection_db;
import sample.db_classes.Entertainments;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;

public class Entertainment_controller {
    @FXML
    private TableView<Entertainments> table_entertainments;
    @FXML
    private TableColumn<Entertainments, String> Name_col;
    @FXML
    private TableColumn<Entertainments, Byte> Count_people_col;
    @FXML
    private TableColumn<Entertainments, Time> Time_start_col;
    @FXML
    private TableColumn<Entertainments, Time> Time_end_col;
    @FXML
    private TableColumn<Entertainments, Integer> Rate_col;
    @FXML
    private TableColumn<Entertainments, Float> Price_col;
    @FXML
    private TableColumn<Entertainments, Byte> Duration_col;

    public void initialize() {
        ShowEntertainments();
    }
    public void ShowEntertainments() {

        ObservableList<Entertainments> list = getEntertainments();

        Name_col.setCellValueFactory(new PropertyValueFactory<>("Name_entertainment"));
        Count_people_col.setCellValueFactory(new PropertyValueFactory<>("Max_People_entertainment"));
        Time_start_col.setCellValueFactory(new PropertyValueFactory<>("Time_start_entertainment"));
        Time_end_col.setCellValueFactory(new PropertyValueFactory<>("Time_end_entertainment"));
        Rate_col.setCellValueFactory(new PropertyValueFactory<>("Rate_entertainment"));
        Price_col.setCellValueFactory(new PropertyValueFactory<>("Price_entertainment"));
        Duration_col.setCellValueFactory(new PropertyValueFactory<>("Duration"));

        table_entertainments.setItems(list);
    }

    private ObservableList<Entertainments> getEntertainments() {
        ObservableList<Entertainments> EntertainmentsList = FXCollections.observableArrayList();
        Connection conn = Connection_db.GetConnection();
        String query = "SELECT * FROM Entertainment";
        Statement st;
        ResultSet rs;

        try {
            if(conn != null) {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                Entertainments entertainments;
                while (rs.next()) {
                    entertainments = new Entertainments(rs.getInt("ID_Entertainment"), rs.getString("Name_entertainment"),
                            rs.getByte("Max_People_entertainment"), rs.getTime("Time_start_entertainment"), rs.getTime("Time_end_entertainment"), rs.getInt("Rate_entertainment"),
                            rs.getFloat("Price_entertainment"), rs.getByte("Duration"));
                    EntertainmentsList.add(entertainments);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return EntertainmentsList;
    }

    public void Add_method(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add_update_entertainments.fxml"));
        Parent parent = fxmlLoader.load();
        Add_update_entertainments add_update_entertainments = fxmlLoader.getController();
        add_update_entertainments.setController(this);
        add_update_entertainments.setAdd_Update(true);
        Connection_db.Get_Dialog(parent);
    }

    public void Update_method(ActionEvent actionEvent) throws IOException {
        Entertainments entertainments = table_entertainments.getSelectionModel().getSelectedItem();
        if(entertainments != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add_update_entertainments.fxml"));
            Parent parent = fxmlLoader.load();
            Add_update_entertainments add_update_entertainments = fxmlLoader.getController();
            add_update_entertainments.setController(this);
            add_update_entertainments.setAdd_Update(false);
            add_update_entertainments.setEntertainment(entertainments);
            add_update_entertainments.setValues();
            Connection_db.Get_Dialog(parent);
        }
    }

    public void Delete_method(ActionEvent actionEvent) {
        Entertainments entertainments = table_entertainments.getSelectionModel().getSelectedItem();
        if(entertainments != null) {
            String query = "DELETE FROM Entertainment WHERE ID_Entertainment = " + entertainments.getID_Entertainment();
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
            ShowEntertainments();
        }
    }
}
