package sample.homesteads;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.w3c.dom.css.RGBColor;
import sample.db_classes.Categories;
import sample.db_classes.Clients;
import sample.db_classes.Connection_db;
import sample.db_classes.Homesteads;
import sample.other_windows.Categories_controller;
import sample.start_window.Start_window_controller;
import sample.tours.Add_update_tours;
import sample.tours.Tours_controller;

import java.io.IOException;
import java.sql.*;

public class Homesteads_controller {

    @FXML
    private AnchorPane root;
    @FXML
    private Button Delete_homestead_tour;
    @FXML
    private ImageView Back_img;
    @FXML
    private Button Add_homestead_tour;
    @FXML
    private TextField search_field;
    @FXML
    private Label Label_set;
    @FXML
    private Button Add_btn;
    @FXML
    private Button Update_btn;
    @FXML
    private Button Delete_btn;
    @FXML
    private TableView<Homesteads> table_homesteads;
    @FXML
    private TableColumn<Homesteads, String> Name_col;
    @FXML
    private TableColumn<Homesteads, Byte> Beds_col;
    @FXML
    private TableColumn<Homesteads, Byte> Rooms_col;
    @FXML
    private TableColumn<Homesteads, Byte> Floors_col;
    @FXML
    private TableColumn<Homesteads, Boolean> Air_col;
    @FXML
    private TableColumn<Homesteads, Boolean> Safe_col;
    @FXML
    private TableColumn<Homesteads, Boolean> Wi_Fi_col;
    @FXML
    private TableColumn<Homesteads, Boolean> Ref_col;
    @FXML
    private TableColumn<Homesteads, Boolean> Iron_col;
    @FXML
    private TableColumn<Homesteads, Boolean> Hair_col;
    @FXML
    private TableColumn<Homesteads, Integer> Rate_col;
    @FXML
    private TableColumn<Homesteads, Float> Price_col;
    @FXML
    private TableColumn<Homesteads, Boolean> Active_col;

    private Add_update_tours tours_controller;

    private ObservableList<String> homesteads_list_str;

    private boolean FromStartWindow;
    private int Category = 0;
    private String worker;

    public void setFromStartWindow(boolean b, int id, String workers) {
        worker = workers;
        Category = id;
        FromStartWindow = b;
        if(b) {
            Add_homestead_tour.setVisible(false);
            Delete_homestead_tour.setVisible(false);
            Label_set.setVisible(false);
        }
    }


    public void setTours(Add_update_tours tours_controller) {
        this.tours_controller = tours_controller;
    }

    public void initialize() {
        Back_img.setPickOnBounds(true);
        homesteads_list_str = FXCollections.observableArrayList();

    }


    public static ObservableList<Homesteads> getHomesteads(String query) {
        ObservableList<Homesteads> HomesteadsList = FXCollections.observableArrayList();
        Connection conn = Connection_db.GetConnection();
        Statement st;
        ResultSet rs;

        try {
           if(conn != null) {
               st = conn.createStatement();
               rs = st.executeQuery(query);
               Homesteads homesteads;
               while (rs.next()) {
                   homesteads = new Homesteads(rs.getInt("ID_Homestead"), rs.getString("Name_homestead"),
                           rs.getByte("Number_of_beds_homestead"), rs.getByte("Number_of_rooms_homestead"), rs.getByte("Number_of_floors_homestead"), rs.getBoolean("Is_Air_Conditioning"),
                           rs.getBoolean("Is_Safe"), rs.getBoolean("Is_Wi_Fi"), rs.getBoolean("Is_Refrigerator"), rs.getBoolean("Is_Clothes_Iron"),
                           rs.getBoolean("Is_Hair_Dryer"), rs.getInt("Rate_homestead"), rs.getFloat("Price_homestead"), rs.getBoolean("Is_Active"));
                   HomesteadsList.add(homesteads);
               }
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HomesteadsList;
    }

    public void ShowHomesteads() {


        String query = " ";
        if (Category == 2) {
            query = "SELECT * FROM Homesteads h JOIN Category_Homesteads ch ON h.ID_Homestead = ch.ID_homestead WHERE ch.ID_category = 0";
        } else if (Category == 3) {
            query = "SELECT * FROM Homesteads h JOIN Category_Homesteads ch ON h.ID_Homestead = ch.ID_homestead WHERE ch.ID_category = 1";
        }
        else if (Category == 4) {
            query = "SELECT * FROM Homesteads h JOIN Category_Homesteads ch ON h.ID_Homestead = ch.ID_homestead WHERE ch.ID_category = 2";
        } else {
            query = "SELECT * FROM Homesteads";
        }



        ObservableList<Homesteads> list = getHomesteads(query);

        Name_col.setCellValueFactory(new PropertyValueFactory<>("Name_homestead"));
        Beds_col.setCellValueFactory(new PropertyValueFactory<>("Number_of_beds_homestead"));
        Rooms_col.setCellValueFactory(new PropertyValueFactory<>("Number_of_rooms_homestead"));
        Floors_col.setCellValueFactory(new PropertyValueFactory<>("Number_of_floors_homestead"));
        Air_col.setCellValueFactory(new PropertyValueFactory<>("Is_Air_Conditioning"));
        Safe_col.setCellValueFactory(new PropertyValueFactory<>("Is_Safe"));
        Wi_Fi_col.setCellValueFactory(new PropertyValueFactory<>("Is_Wi_Fi"));
        Ref_col.setCellValueFactory(new PropertyValueFactory<>("Is_Refrigerator"));
        Iron_col.setCellValueFactory(new PropertyValueFactory<>("Is_Clothes_Iron"));
        Hair_col.setCellValueFactory(new PropertyValueFactory<>("Is_Hair_Dryer"));
        Rate_col.setCellValueFactory(new PropertyValueFactory<>("Rate_homestead"));
        Price_col.setCellValueFactory(new PropertyValueFactory<>("Price_homestead"));
        Active_col.setCellValueFactory(new PropertyValueFactory<>("Is_Active"));


        FilteredList<Homesteads> filteredData = new FilteredList<>(list, b -> true);

        search_field.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(homesteads -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                return homesteads.getName_homestead().toLowerCase().indexOf(lowerCaseFilter) != -1;
            });
        });

        SortedList<Homesteads> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(table_homesteads.comparatorProperty());

        table_homesteads.setItems(sortedData);
        table_homesteads.refresh();


    }


    public void Add_method(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Add_homesteads.fxml"));
        Parent parent = fxmlLoader.load();
        AddHomesteads AddHomesteads_controller = fxmlLoader.getController();
        AddHomesteads_controller.setController(this);
        AddHomesteads_controller.setCategory();
        Connection_db.Get_Dialog(parent, 490, 680);
    }

    public void Update_method(ActionEvent actionEvent) throws IOException {
        Homesteads homestead = table_homesteads.getSelectionModel().getSelectedItem();

        if(homestead != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Update_homesteads.fxml"));
            Parent parent = fxmlLoader.load();
            UpdateHomesteads UpdateHomesteads_controller = fxmlLoader.getController();
            UpdateHomesteads_controller.setController(this);
            UpdateHomesteads_controller.setHomestead(homestead);
            UpdateHomesteads_controller.setValues();
            UpdateHomesteads_controller.GetAllCategories_of_homestead();
            Connection_db.Get_Dialog(parent, 490, 680);
        }
    }

    public void Delete_method(ActionEvent actionEvent) {
        Homesteads homestead = table_homesteads.getSelectionModel().getSelectedItem();
        if(homestead != null) {

            String query = "BEGIN TRY BEGIN TRAN " +
                    "UPDATE Tour SET ID_homestead = NULL WHERE ID_homestead = " + homestead.getID_homestead() +
                    " DELETE FROM Category_Homesteads WHERE ID_homestead = " + homestead.getID_homestead() +
                    " DELETE FROM Homesteads WHERE ID_Homestead = " + homestead.getID_homestead() +
                    " COMMIT TRAN END TRY BEGIN CATCH " +
                    "SELECT error_message() AS ErrorMessage " +
                    "ROLLBACK TRAN END CATCH";

            new Connection_db().Cancel_Dialog(query);
            ShowHomesteads();
        }

    }


    public void Add_homesteads_tour(ActionEvent actionEvent) {
        Homesteads homestead = table_homesteads.getSelectionModel().getSelectedItem();
        if(homestead != null) {
            tours_controller.AddHomesteads(homestead);
            SetLabel();
            Add_homestead_tour.setVisible(false);

        }
    }

    public void Delete_homesteads_tour(ActionEvent actionEvent) {
        Add_homestead_tour.setVisible(true);
        tours_controller.AddHomesteads(null);
        Label_set.setText(" ");
    }

    public void SetLabel() {
        if(tours_controller.getHomesteads() != null)
        Label_set.setText(tours_controller.getHomesteads().getName_homestead());
    }

    public void Go_back(MouseEvent mouseEvent) throws IOException {
        if(FromStartWindow) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/other_windows/Categories.fxml"));
            Parent parent = fxmlLoader.load();
            Categories_controller categories_controller = fxmlLoader.getController();
            categories_controller.setWindow(3);
            categories_controller.setButtons();
            categories_controller.SetWorker(worker);
            root.getChildren().setAll(parent);
        } else {
            Connection_db.closeWindowImg(mouseEvent);
        }
    }

}
