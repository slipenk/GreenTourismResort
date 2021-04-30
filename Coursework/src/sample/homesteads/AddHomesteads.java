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
import sample.db_classes.Entertainments;
import sample.db_classes.Homesteads;
import sample.homesteads.Homesteads_controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class AddHomesteads {
    @FXML
    private Tooltip Category_tooltip;
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
    private Button Add_btn;
    @FXML
    private TextField Price_homestead;
    @FXML
    private TextField Name_homestead;
    @FXML
    private ChoiceBox<Byte> Number_of_rooms;
    @FXML
    private ChoiceBox<String> category;


    private Set<String> list_cat;
    private ObservableList<Categories> CategoriesList;

    private Homesteads_controller hc;

    public void setController(Homesteads_controller hc) {
        this.hc = hc;
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
        list_cat = new HashSet<>();
        Category_tooltip.setText("Виберіть категорії:");
    }


    private void GetAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.NONE, text, ButtonType.OK);
        alert.getDialogPane().getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/sample/style.css")).toExternalForm());
        alert.showAndWait();
    }

    public void Add_method(ActionEvent actionEvent) {

        if (Name_homestead.getText().isBlank() || Price_homestead.getText().isBlank()) {
            GetAlert("Введіть назву та ціну садиби");
            return;
        }

        String query = "INSERT INTO Homesteads VALUES ( N'" +
                Name_homestead.getText().trim() + "', " +
                Number_of_beds.getValue()  + ", " +
                Number_of_rooms.getValue() + ", " +
                Number_of_floors.getValue() + ", " +
                (Is_air_conditioning.isSelected() ? 1 : 0) + ", " +
                (Is_safe.isSelected()  ? 1 : 0) + ", " +
                (Is_Wi_Fi.isSelected()  ? 1 : 0) + ", " +
                (Is_ref.isSelected()  ? 1 : 0) + ", " +
                (Is_Iron.isSelected()  ? 1 : 0) + ", " +
                (Is_Hair.isSelected()  ? 1 : 0) + ", " +
                0 + ", " +
                Float.parseFloat(Price_homestead.getText()) + ", " +
                0 + ") ";
        Connection_db.executeQuery(query);

        Connection_db.closeWindow(actionEvent);




        ObservableList<Homesteads> list = Homesteads_controller.getHomesteads("SELECT * FROM Homesteads");

        for( Categories c : CategoriesList) {
            for (String s : list_cat) {
                if (s.equals(c.getName_category())) {
                    String query_2 = "INSERT INTO Category_Homesteads VALUES ( " + list.get(list.size() - 1).getID_homestead() + ", " +
                            c.getID_category() + ")";
                    Connection_db.executeQuery(query_2);
                }
            }
        }
        hc.ShowHomesteads();

    }

    public void setCategory() {

        CategoriesList = Connection_db.getCategories("SELECT * FROM Category_H");
        ObservableList<String> category_List = FXCollections.observableArrayList();

        for(Categories e : CategoriesList) {
            category_List.add(e.getName_category());
        }
        category.setItems(category_List);
        category.setValue(category_List.get(category_List.size()-1));
    }

    private  StringBuilder string = new StringBuilder(" ");
    public void add_category(ActionEvent actionEvent) {
        list_cat.add(category.getValue());
        string.append(category.getValue()).append(" ");
        Category_tooltip.setText(String.valueOf(string));
    }
}
