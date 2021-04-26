package sample.options_enter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.db_classes.Connection_db;
import sample.db_classes.Options_enter;
import sample.db_classes.Workers;

import java.sql.*;

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

    private ObservableList<Options_enter> options_list;

    public void initialize() {
        options_list = FXCollections.observableArrayList();
        ShowOptions();
    }

    private void ShowOptions() {
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
        ObservableList<Options_enter> WorkersList = FXCollections.observableArrayList();
        Connection conn = Connection_db.GetConnection();

        Statement st;
        ResultSet rs;

        try {
            if(conn != null) {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                Options_enter options_enter;
                while (rs.next()) {
                   /* options_enter = new Options_enter(rs.getInt("ID_Options"), rs.getDate("Date_options"),
                            rs.getTime("Time_options"), rs.getByte("Count_people_options"), );
                   WorkersList.add(workers);*/
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return WorkersList;
    }

    public void Add_method(ActionEvent actionEvent) {
    }

    public void Update_method(ActionEvent actionEvent) {
    }

    public void Delete_method(ActionEvent actionEvent) {
    }
}
