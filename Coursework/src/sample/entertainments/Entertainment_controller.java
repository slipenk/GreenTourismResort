package sample.entertainments;

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
import sample.clients.Add_update_clients;
import sample.db_classes.Clients;
import sample.db_classes.Connection_db;
import sample.db_classes.Entertainments;
import sample.other_windows.Categories_controller;
import sample.tours.Add_update_tours;
import sample.tours.Tours_controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Entertainment_controller {
    @FXML
    private Button Add_client_enter;
    @FXML
    private Button Delete_client_enter;
    @FXML
    private ImageView img_back;
    @FXML
    private AnchorPane root;
    @FXML
    private ChoiceBox<String> Enter_tour;
    @FXML
    private TextField search_field;
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


    private Add_update_tours tours_controller;
    private ObservableList<String> entertainments_list_str;

    private int Category_enter = 0;

    private boolean FromStartWindow;
    private String worker;

    public void setFromStartWindow(boolean b, String workers) {
        worker = workers;
        FromStartWindow = b;
        if(b) {
            Add_client_enter.setVisible(false);
            Delete_client_enter.setVisible(false);
            Enter_tour.setVisible(false);
        }
    }


    public void setCategory(int Category) {
        Category_enter = Category;
    }


    public void setTours(Add_update_tours tours_controller) {
        this.tours_controller = tours_controller;
    }


    public void initialize() {
        entertainments_list_str = FXCollections.observableArrayList();
        img_back.setPickOnBounds(true);
        //ShowEntertainments();
    }


    public void ShowEntertainments() {
        String query = " ";
        if (Category_enter == 2) {
            query = "SELECT * FROM Entertainment e JOIN Category_Entertainment ce ON e.ID_Entertainment = ce.ID_entertainment WHERE ce.ID_category = 1";
        } else if (Category_enter == 3) {
            query = "SELECT * FROM Entertainment e JOIN Category_Entertainment ce ON e.ID_Entertainment = ce.ID_entertainment WHERE ce.ID_category = 3";
        }
        else if (Category_enter == 4) {
            query = "SELECT * FROM Entertainment e JOIN Category_Entertainment ce ON e.ID_Entertainment = ce.ID_entertainment WHERE ce.ID_category = 2";
        } else {
            query = "SELECT * FROM Entertainment";
        }
        ObservableList<Entertainments> list = getEntertainments(query);

        Name_col.setCellValueFactory(new PropertyValueFactory<>("Name_entertainment"));
        Count_people_col.setCellValueFactory(new PropertyValueFactory<>("Max_People_entertainment"));
        Time_start_col.setCellValueFactory(new PropertyValueFactory<>("Time_start_entertainment"));
        Time_end_col.setCellValueFactory(new PropertyValueFactory<>("Time_end_entertainment"));
        Rate_col.setCellValueFactory(new PropertyValueFactory<>("Rate_entertainment"));
        Price_col.setCellValueFactory(new PropertyValueFactory<>("Price_entertainment"));
        Duration_col.setCellValueFactory(new PropertyValueFactory<>("Duration"));

        FilteredList<Entertainments> filteredData = new FilteredList<>(list, b -> true);

        search_field.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entertainments -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                return entertainments.getName_entertainment().toLowerCase().indexOf(lowerCaseFilter) != -1;
            });
        });

        SortedList<Entertainments> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(table_entertainments.comparatorProperty());

        table_entertainments.setItems(sortedData);
    }

    public static ObservableList<Entertainments> getEntertainments(String query) {
        ObservableList<Entertainments> EntertainmentsList = FXCollections.observableArrayList();
        Connection conn = Connection_db.GetConnection();
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
        add_update_entertainments.setCategory();
        Connection_db.Get_Dialog(parent, 490, 680);
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
            add_update_entertainments.GetAllCategories_of_enter();
            Connection_db.Get_Dialog(parent, 490, 680);
        }
    }

    public void Delete_method(ActionEvent actionEvent) {
        Entertainments entertainments = table_entertainments.getSelectionModel().getSelectedItem();
        if(entertainments != null) {
            String query_4 = "DELETE FROM Tours_entertainment t WHERE t.ID_entertainments = " + entertainments.getID_Entertainment();
            String query_3 = "DELETE FROM [Options] o JOIN Tours_entertainment t ON o.ID_tours_enter = t.ID_TEN WHERE t.ID_entertainments = " + entertainments.getID_Entertainment();
            String query_2 = "DELETE FROM Category_Entertainment WHERE ID_entertainment = " + entertainments.getID_Entertainment();
            String query = "DELETE FROM Entertainment WHERE ID_Entertainment = " + entertainments.getID_Entertainment();
            Connection_db.Cancel_Dialog(query_3 + query_4 + query_2 + query);
            ShowEntertainments();
        }
    }

    public void Add_enter_tour(ActionEvent actionEvent) {
        Entertainments entertainments = table_entertainments.getSelectionModel().getSelectedItem();
        if(entertainments != null) {
            tours_controller.AddEntertainments(entertainments);
            entertainments_list_str.clear();
            SetChoiceBox();
        }
    }

    public void Delete_enter_tour(ActionEvent actionEvent) {
        Entertainments lastElement = null;
        if(tours_controller.getEntertainments().size() != 0) {
            Iterator<Entertainments> iterator = tours_controller.getEntertainments().iterator();
            while(iterator.hasNext()){
                lastElement = iterator.next();
                if(lastElement.getName_entertainment().equals(Enter_tour.getValue())) {
                    break;
                }
            }
            tours_controller.DeleteEntertainments(lastElement);
        }

        entertainments_list_str.clear();
        SetChoiceBox();
    }

    public void SetChoiceBox() {
        for(Entertainments c : tours_controller.getEntertainments()) {
            entertainments_list_str.add(c.getName_entertainment());
        }
        Enter_tour.setItems(entertainments_list_str);
        if(entertainments_list_str.size() != 0) {
            Enter_tour.setValue(entertainments_list_str.get(0));
        }
    }

    public void Go_back(MouseEvent mouseEvent) throws IOException {
        if(FromStartWindow) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/other_windows/Categories.fxml"));
            Parent parent = fxmlLoader.load();
            Categories_controller categories_controller = fxmlLoader.getController();
            categories_controller.SetWorker(worker);
            root.getChildren().setAll(parent);
        } else {
            Connection_db.closeWindowImg(mouseEvent);
        }
    }

}
