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
import sample.entertainments.Entertainment_controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
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
    private HashSet<Integer> enter;
    private ArrayList<Integer> enter_list;

    private ObservableList<Resources> getResources() {
        ObservableList<Resources> ResourcesList = FXCollections.observableArrayList();
        Connection conn = Connection_db.GetConnection();
        String query = "SELECT * FROM Resources";
        Statement st;
        ResultSet rs;
        enter = new HashSet<Integer>();
        enter_list = new ArrayList<>();

        try {
            if(conn != null) {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                Resources resources;
                while (rs.next()) {
                    resources = new Resources(rs.getInt("ID_Resources"), rs.getString("Name_Resources"),
                            rs.getShort("Count_resources"), rs.getFloat("Price_one"), "");
                    enter.add(rs.getInt("ID_entertainment"));
                    enter_list.add(rs.getInt("ID_entertainment"));
                    ResourcesList.add(resources);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

       StringBuilder temp = new StringBuilder(" ");
        if(enter.size() == 1) {
            for(int i: enter) {
                temp.append(i);
            }

        } else if (enter.size() > 1) {
            for (int i : enter) {
                temp.append(i).append(", ");
            }
            temp.deleteCharAt(temp.lastIndexOf(","));
        }






        String query_2 = "SELECT * FROM Entertainment WHERE ID_Entertainment IN ( " + temp + " )";

        if(enter.size() != 0) {
            ObservableList<Entertainments> EntertainmentsList = Entertainment_controller.getEntertainments(query_2);
            if (enter_list.size() == ResourcesList.size()) {
                for (int i = 0; i < enter_list.size(); ++i) {
                    for(Entertainments e : EntertainmentsList) {
                        if(e.getID_Entertainment() == enter_list.get(i)) {
                            ResourcesList.get(i).setEntertainment(e.getName_entertainment());
                        }
                    }
                }
            }
        }
        return ResourcesList;
    }


    public void Add_method(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add_update_resources.fxml"));
        Parent parent = fxmlLoader.load();
        add_update_resources Add_update_resources = fxmlLoader.getController();
        Add_update_resources.setController(this);
        Add_update_resources.setAdd_Update(true);
        Add_update_resources.setEntertainment(Entertainment_controller.getEntertainments("SELECT * FROM Entertainment"));
        Add_update_resources.setEntertainmentList();
        Connection_db.Get_Dialog(parent);
    }

    public void Update_method(ActionEvent actionEvent) throws IOException {
        Resources resources = table_resources.getSelectionModel().getSelectedItem();
        if(resources != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add_update_resources.fxml"));
            Parent parent = fxmlLoader.load();
            add_update_resources Add_update_resources = fxmlLoader.getController();
            Add_update_resources.setController(this);
            Add_update_resources.setAdd_Update(false);
            Add_update_resources.setResource(resources);
            Add_update_resources.setEntertainment(Entertainment_controller.getEntertainments("SELECT * FROM Entertainment"));
            Add_update_resources.setEntertainmentList();
            Add_update_resources.setValues();
            Connection_db.Get_Dialog(parent);
        }
    }

    public void Delete_method(ActionEvent actionEvent) {
        Resources resources = table_resources.getSelectionModel().getSelectedItem();
        if(resources != null) {
            String query = "DELETE FROM Resources WHERE ID_Resources = " + resources.getID_Resources();
            Connection_db.Cancel_Dialog(query);
            ShowResources();
        }
    }
}
