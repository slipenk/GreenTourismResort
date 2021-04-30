package sample.homesteads;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.db_classes.Categories;
import sample.db_classes.Connection_db;
import sample.db_classes.Homesteads;
import sample.homesteads.Homesteads_controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UpdateHomesteads {
    @FXML
    private ChoiceBox<String> Category;
    @FXML
    private ChoiceBox<Byte> Number_of_floors;
    @FXML
    private ChoiceBox<Byte> Number_of_beds;
    @FXML
    private CheckBox Is_Hair;
    @FXML
    private CheckBox Is_Iron;
    @FXML
    private CheckBox Is_ref;
    @FXML
    private CheckBox Is_Wi_Fi;
    @FXML
    private CheckBox Is_safe;
    @FXML
    private CheckBox Is_air_conditioning;
    @FXML
    private Button Update_btn;
    @FXML
    private TextField Price_homestead;
    @FXML
    private TextField Name_homestead;
    @FXML
    private ChoiceBox<Byte> Number_of_rooms;
    @FXML
    private CheckBox is_available;
    @FXML
    private ChoiceBox<Integer> rate_box;
    @FXML
    private Tooltip Category_tooltip;

    private Homesteads_controller hc;
    private Homesteads homestead;
    private ObservableList<Categories> category;
    private ObservableList<Categories> categoryAll;
    private Set<String> set;
    private Boolean delete_cat = false;

    public void setController(Homesteads_controller hc) {
        this.hc = hc;
    }
    public void setHomestead(Homesteads homesteads) {
        homestead = homesteads;
    }

    public void initialize() {
        Number_of_floors.setItems(FXCollections.observableArrayList(
                (byte)1, (byte)2, (byte)3));
        Number_of_floors.setValue((byte)1);
        Number_of_beds.setItems(FXCollections.observableArrayList(
                (byte)1, (byte)2, (byte)3, (byte)4, (byte)5, (byte)6, (byte)7, (byte)8));
        Number_of_beds.setValue((byte)1);
        Number_of_rooms.setItems(FXCollections.observableArrayList(
                (byte)1, (byte)2, (byte)3, (byte)4, (byte)5, (byte)6, (byte)7, (byte)8));
        Number_of_rooms.setValue((byte)1);
        rate_box.setItems(FXCollections.observableArrayList(
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        rate_box.setValue(0);
        set = new HashSet<>();

        Price_homestead.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*(\\.\\d*)?")) {
                    Price_homestead.setText(oldValue);
                }
            }
        });
        Price_homestead.setPromptText("Ціна");
        Name_homestead.setPromptText("Назва");
        Category_tooltip.setText("Виберіть категорії:");
    }

    public void setValues() {
        Number_of_floors.setValue(homestead.getNumber_of_floors_homestead());
        Number_of_beds.setValue(homestead.getNumber_of_beds_homestead());
        Number_of_rooms.setValue(homestead.getNumber_of_rooms_homestead());
        is_available.setSelected(homestead.isIs_Active());
        Price_homestead.setText(String.valueOf(homestead.getPrice_homestead()));
        Name_homestead.setText(homestead.getName_homestead());
        Is_air_conditioning.setSelected(homestead.isIs_Air_Conditioning());
        Is_safe.setSelected(homestead.isIs_Safe());
        Is_Wi_Fi.setSelected(homestead.isIs_Wi_Fi());
        Is_ref.setSelected(homestead.isIs_Refrigerator());
        Is_Iron.setSelected(homestead.isIs_Clothes_Iron());
        Is_Hair.setSelected(homestead.isIs_Hair_Dryer());

    }

    private void GetAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.NONE, text, ButtonType.OK);
        alert.getDialogPane().getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/sample/style.css")).toExternalForm());
        alert.showAndWait();
    }

    public void Update_method(ActionEvent actionEvent) {

        if (Name_homestead.getText().isBlank() || Price_homestead.getText().isBlank()) {
            GetAlert("Введіть назву та ціну садиби");
            return;
        }

        String query = "UPDATE Homesteads SET Name_homestead = N'" + Name_homestead.getText().trim()  + "', Number_of_beds_homestead = " +
                Number_of_beds.getValue() + ", Number_of_rooms_homestead = " + Number_of_rooms.getValue() + ", Number_of_floors_homestead = " +
                Number_of_floors.getValue() + ", Is_Air_Conditioning = " + (Is_air_conditioning.isSelected() ? 1 : 0) + ", Is_Safe = " +
                (Is_safe.isSelected() ? 1 : 0) + ", Is_Wi_Fi = " + (Is_Wi_Fi.isSelected() ? 1 : 0) + ", Is_Refrigerator = " +
                (Is_ref.isSelected() ? 1 : 0) + ", Is_Clothes_Iron = " + (Is_Iron.isSelected() ? 1 : 0) + ", Is_Hair_Dryer = " +
                (Is_Hair.isSelected() ? 1 : 0) + ", Rate_homestead = " + (rate_box.getValue() + homestead.getRate_homestead()) + ", Price_homestead = " +
                Float.parseFloat(Price_homestead.getText()) + ", Is_active = " + (is_available.isSelected() ? 1 : 0) + " WHERE ID_Homestead = " +
                homestead.getID_homestead();
        Connection_db.executeQuery(query);

        Connection_db.closeWindow(actionEvent);


        ObservableList<Homesteads> list = Homesteads_controller.getHomesteads("SELECT * FROM Homesteads");

        String query_3 = "DELETE FROM Category_Homesteads WHERE ID_Homestead =  " + homestead.getID_homestead();
        if(delete_cat)
        Connection_db.executeQuery(query_3);
        for( Categories c : categoryAll) {
            for (String s : set) {
                if (s.equals(c.getName_category())) {
                    String query_2 = " INSERT INTO Category_Homesteads VALUES ( " + homestead.getID_homestead() + ", " +
                            c.getID_category() + ")";
                    Connection_db.executeQuery(query_2);
                }
            }
        }
        hc.ShowHomesteads();
    }

    public void GetAllCategories_of_homestead() {
        String query = "SELECT ch1.ID_category, ch1.Name_category " +
                "FROM Category_H ch1 " +
                "JOIN Category_Homesteads ch ON ch.ID_category = ch1.ID_category " +
                "JOIN Homesteads h ON ch.ID_homestead = h.ID_Homestead " +
                "WHERE h.ID_Homestead = " + homestead.getID_homestead();
        category = Connection_db.getCategories(query);
        StringBuilder s = new StringBuilder("");
        for(Categories c : category)  {
            s.append(c.getName_category()).append(" ");
        }
        Category_tooltip.setText(String.valueOf(s));
        categoryAll  = Connection_db.getCategories("SELECT * FROM Category_H");
        ObservableList<String> category = FXCollections.observableArrayList();

        for(Categories e : categoryAll) {
            category.add(e.getName_category());
        }
        Category.setItems(category);
        Category.setValue(category.get(category.size()-1));
    }

    private StringBuilder string = new StringBuilder(" ");
    public void Add_category(ActionEvent actionEvent) {
        delete_cat = true;
        set.add(Category.getValue());
        string.append(Category.getValue()).append(" ");
        Category_tooltip.setText(String.valueOf(string));
    }
}
