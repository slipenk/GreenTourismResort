package sample.resources;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.db_classes.Connection_db;
import sample.db_classes.Entertainments;
import sample.db_classes.Resources;
import sample.entertainments.Add_update_entertainments;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class resources_controller {
    @FXML
    private TableView<Resources> table_resources;
    @FXML
    private TableColumn<Resources, String> Name_col;
    @FXML
    private TableColumn<Resources, Short> Count_col;
    @FXML
    private TableColumn<Resources, Float> Price_col;
    @FXML
    private TableColumn<Resources, String> Entertainment_col;

    public void initialize() {
        ShowResources();
    }
    public void ShowResources() {

        ObservableList<Resources> list = getResources();

        Name_col.setCellValueFactory(new PropertyValueFactory<>("Name_Resources"));
        Count_col.setCellValueFactory(new PropertyValueFactory<>("Count_resources"));
        Price_col.setCellValueFactory(new PropertyValueFactory<>("Price_one"));
        Entertainment_col.setCellValueFactory(new PropertyValueFactory<>("Entertainment"));

        table_resources.setItems(list);
    }
    private ArrayList<Integer> enter;
    private ObservableList<Resources> getResources() {
        ObservableList<Resources> ResourcesList = FXCollections.observableArrayList();
        Connection conn = Connection_db.GetConnection();
        String query = "SELECT * FROM Resources";
        Statement st;
        ResultSet rs;
        enter = new ArrayList<>();

        try {
            if(conn != null) {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                Resources resources;
                while (rs.next()) {
                    resources = new Resources(rs.getInt("ID_Resources"), rs.getString("Name_Resources"),
                            rs.getShort("Count_resources"), rs.getFloat("Price_one"), "");
                    enter.add(rs.getInt("ID_entertainment"));
                    ResourcesList.add(resources);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ObservableList<Entertainments> EntertainmentsList = getEntertainments();
        if(EntertainmentsList.size() == ResourcesList.size()) {
            for (int i = 0; i < EntertainmentsList.size(); ++i) {
                ResourcesList.get(i).setEntertainment(EntertainmentsList.get(i).getName_entertainment());
            }
        }
        return ResourcesList;
    }

    private ObservableList<Entertainments> getEntertainments() {
        ObservableList<Entertainments> EntertainmentsList = FXCollections.observableArrayList();
        Connection conn = Connection_db.GetConnection();
        StringBuilder temp = new StringBuilder(" ");
        for(int i = 0; i < enter.size(); ++i) {
            if(enter.size() == 1) {
                temp.append(enter.get(i));
            } else {
                temp.append(enter.get(i)).append(", ");
            }
        }
        String query = "SELECT * FROM Entertainment WHERE ID_Entertainment IN ( " + temp + " )";
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add_update_resources.fxml"));
        Parent parent = fxmlLoader.load();
        add_update_resources Add_update_resources = fxmlLoader.getController();
        Add_update_resources.setController(this);
        Add_update_resources.setAdd_Update(true);
        Connection_db.Get_Dialog(parent);


    }

    public void Update_method(ActionEvent actionEvent) {
    }

    public void Delete_method(ActionEvent actionEvent) {
    }
}
