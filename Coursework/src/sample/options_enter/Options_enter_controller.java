package sample.options_enter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.db_classes.*;
import sample.entertainments.Entertainment_controller;
import sample.tours.Add_update_tours;
import sample.tours.Tours_controller;

import java.io.IOException;
import java.sql.*;
import java.util.Set;

public class Options_enter_controller {
    @FXML
    private TableView<Options_enter> table_options;
    @FXML
    private TableColumn<Options_enter, Date> Date_col;
    @FXML
    private TableColumn<Options_enter, Time> Time_col;
    @FXML
    private TableColumn<Options_enter, Byte> Count_col;
    @FXML
    private TableColumn<Options_enter, String> Enter_col;
    @FXML
    private TableColumn<Options_enter, ChoiceBox<String>> Clients_col;
    @FXML
    private TextField search_field;
    @FXML
    private ChoiceBox<String> enter_box;


    private ObservableList<Entertainments> entertainments;
    private ObservableList<String> entertainments_str;
    private Tours tours;

    public void SetTour(Tours t) {
        tours = t;
    }

    public void initialize() {
        entertainments = FXCollections.observableArrayList();
        entertainments_str = FXCollections.observableArrayList();
        ShowOptions();
    }

    public void GetEnters() {
        entertainments = Entertainment_controller.getEntertainments("SELECT * " +
                "FROM Entertainment e " +
                "JOIN Tours_entertainment te ON e.ID_Entertainment = te.ID_entertainments \n" +
                "WHERE te.ID_tours = " + tours.getID_tours());
        for(Entertainments e : entertainments) {
            entertainments_str.add(e.getName_entertainment());
        }
        enter_box.setItems(entertainments_str);
        if(entertainments_str.size() != 0) {
            enter_box.setValue(entertainments_str.get(0));
        }
    }

    public void ShowOptions() {
        ObservableList<Options_enter> list = getOptions("SELECT * FROM Options");

        Date_col.setCellValueFactory(new PropertyValueFactory<>("Date_options"));
        Time_col.setCellValueFactory(new PropertyValueFactory<>("Time_options"));
        Count_col.setCellValueFactory(new PropertyValueFactory<>("Count_people_options"));
        Enter_col.setCellValueFactory(new PropertyValueFactory<>("Enters"));
        Clients_col.setCellValueFactory(new PropertyValueFactory<>("clients"));

        FilteredList<Options_enter> filteredData = new FilteredList<>(list, b -> true);

        search_field.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(options -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if(options.getDate_options().toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(options.getEnters().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Options_enter> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(table_options.comparatorProperty());

        table_options.setItems(sortedData);
    }

    private ObservableList<Options_enter> getOptions(String query) {
        ObservableList<Options_enter> OptionsList = FXCollections.observableArrayList();
        Connection conn = Connection_db.GetConnection();

        Statement st;
        ResultSet rs;
        ObservableList<String> enters;
        ObservableList<String> clients;
        try {
            if(conn != null) {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                Options_enter options_enter;
                while (rs.next()) {
                    enters = Tours_controller.getEnterQuery("SELECT e.Name_entertainment " +
                            "FROM [Options] o " +
                            "JOIN Tours_entertainment te ON te.ID_TEN = o.ID_tours_enter " +
                            "JOIN Entertainment e ON e.ID_Entertainment = te.ID_entertainments " +
                            "WHERE o.ID_Options = " + rs.getInt("ID_Options"));
                    clients = Tours_controller.getClientsQuery("SELECT c.Surname_client, c.Name_client, c.Phone_number_client \n" +
                            "FROM [Options] o " +
                            "JOIN Tours_entertainment te ON te.ID_TEN = o.ID_tours_enter " +
                            "JOIN Tour t ON t.ID_tours = te.ID_tours " +
                            "JOIN Clients_tours ct ON ct.ID_tours = t.ID_tours " +
                            "JOIN Client c ON c.ID_client = ct.ID_clients " +
                            "WHERE o.ID_Options = " + rs.getInt("ID_Options"));
                    options_enter = new Options_enter(rs.getInt("ID_Options"), rs.getDate("Date_options"),
                            rs.getTime("Time_options"), rs.getByte("Count_people_options"), rs.getInt("ID_tours_enter"),
                            enters.get(0), clients);
                    OptionsList.add(options_enter);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OptionsList;
    }






    public void Add_method(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Add_update_options_enter.fxml"));
        Parent parent = fxmlLoader.load();
        Add_update_options_enter add_update_options_enter = fxmlLoader.getController();
        add_update_options_enter.SetEnters(entertainments, enter_box.getValue(), this, true, tours);
        Connection_db.Get_Dialog(parent, 490, 680);
    }

    public void Update_method(ActionEvent actionEvent) throws IOException {
        Options_enter options_enter = table_options.getSelectionModel().getSelectedItem();
        if(options_enter != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Add_update_options_enter.fxml"));
            Parent parent = fxmlLoader.load();
            Add_update_options_enter add_update_options_enter = fxmlLoader.getController();
            add_update_options_enter.SetEnters(entertainments, enter_box.getValue(), this, true, tours);
            add_update_options_enter.setOptions_enter(options_enter);
            add_update_options_enter.setValues();
            Connection_db.Get_Dialog(parent, 490, 680);
        }
    }

    public void Delete_method(ActionEvent actionEvent) {
        Options_enter options_enter = table_options.getSelectionModel().getSelectedItem();
        if(options_enter != null) {
            String query = "DELETE FROM Options WHERE ID_Options = " + options_enter.getID_Options();
            Connection_db.Cancel_Dialog(query);
            ShowOptions();
        }
    }

}
