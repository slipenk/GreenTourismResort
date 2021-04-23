package sample.entertainments;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.clients.Clients_controller;
import sample.db_classes.*;
import sample.homesteads.Homesteads_controller;

import java.sql.Date;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.HashSet;
import java.util.Set;

public class Add_update_entertainments {
    @FXML
    private ChoiceBox<String> category;
    @FXML
    private Tooltip Category_tooltip;
    @FXML
    private ChoiceBox<Byte> Duration;
    @FXML
    private ChoiceBox<Byte> Count_people;
    @FXML
    private ChoiceBox<Byte> rate_box;
    @FXML
    private Label Name_window;
    @FXML
    private Button Add_btn;
    @FXML
    private TextField Price;
    @FXML
    private TextField Name;

    @FXML
    private TextField Time_start;
    @FXML
    private TextField Time_end;


    private Set<String> list_cat;
    private ObservableList<Categories> CategoriesList;
    private ObservableList<Categories> CategoriesList_Update;

    private Entertainment_controller ec;
    private boolean Add_Update;
    private Entertainments entertainment;

    public void setController(Entertainment_controller ec) {
        this.ec = ec;
    }
    public void setAdd_Update(boolean b) {
        Add_Update = b;
    }
    public void setEntertainment(Entertainments entertainments) {
        entertainment = entertainments;
    }

    public void initialize() {

        Price.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*(\\.\\d*)?")) {
                    Price.setText(oldValue);
                }
            }
        });
        Price.setText("0");

        Duration.setItems(FXCollections.observableArrayList(
                (byte)1, (byte)2, (byte)3, (byte)4, (byte)5));
        Duration.setValue((byte)1);
        Count_people.setItems(FXCollections.observableArrayList(
                (byte)1, (byte)2, (byte)3, (byte)4, (byte)5, (byte)6, (byte)7, (byte)8, (byte)9,
                (byte)10, (byte)11, (byte)12,(byte)13, (byte)14, (byte)15));
        Count_people.setValue((byte)1);
        rate_box.setItems(FXCollections.observableArrayList((byte)0,
                (byte)1, (byte)2, (byte)3, (byte)4, (byte)5, (byte)6, (byte)7, (byte)8, (byte)9, (byte)10));
        rate_box.setValue((byte)0);
        Time_end.setPromptText("гг:хx:cc");
        Time_start.setPromptText("гг:хx:cc");
        list_cat = new HashSet<>();
        Category_tooltip.setText("Виберіть категорії:");

    }

    public void setValues() {
        Name.setText(entertainment.getName_entertainment());
        Count_people.setValue(entertainment.getMax_People_entertainment());
        Time_start.setText(entertainment.getTime_start_entertainment().toString());
        Time_end.setText(entertainment.getTime_end_entertainment().toString());
        Price.setText(String.valueOf(entertainment.getPrice_entertainment()));
        Duration.setValue(entertainment.getDuration());
        if(!Add_Update) {
            Name_window.setText("Оновити розвагу");
            Add_btn.setText("Оновити");
        }
    }

    public void Add_method(ActionEvent actionEvent) {
        DateTimeFormatter strictTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
                .withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalTime.parse(Time_start.getText(), strictTimeFormatter);
            LocalTime.parse(Time_end.getText(), strictTimeFormatter);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Правильний формат часу \"гг:хх:cc\" ", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                return;
            }
        }

        String query;
        if(Add_Update) {
            query = "INSERT INTO Entertainment VALUES ( N'" +
                    Name.getText() + "', " +
                    Count_people.getValue() + ", '" +
                    Time_start.getText() + "', '" +
                    Time_end.getText() + "', " +
                    0 + ", " +
                    Float.parseFloat(Price.getText()) + ", " +
                    Duration.getValue() + ")";
        } else {
             query = "UPDATE Entertainment SET Name_entertainment = N'" + Name.getText()  + "', Max_People_entertainment = " +
                    Count_people.getValue() + ", Time_start_entertainment = '" + Time_start.getText() + "', Time_end_entertainment = '" +
                     Time_end.getText() + "', Rate_entertainment = " + (rate_box.getValue() + entertainment.getRate_entertainment()) + ", Price_entertainment = " +
                    Price.getText() + ", Duration = " +
                    Duration.getValue() + " WHERE ID_Entertainment = " +
                    entertainment.getID_Entertainment();
        }

        Connection_db.executeQuery(query);
        ec.ShowEntertainments();
        Connection_db.closeWindow(actionEvent);

        ObservableList<Entertainments> list = Entertainment_controller.getEntertainments("SELECT * FROM Entertainment");

        if(Add_Update) {

            for( Categories c : CategoriesList) {
                for (String s : list_cat) {
                    if (s.equals(c.getName_category())) {
                        String query_2 = "INSERT INTO Category_Entertainment VALUES ( " + list.get(list.size() - 1).getID_Entertainment() + ", " +
                                c.getID_category() + ")";
                        Connection_db.executeQuery(query_2);
                    }
                }
            }
        } else {
            String query_3 = "DELETE FROM Category_Entertainment WHERE ID_entertainment =  " + entertainment.getID_Entertainment();
            Connection_db.executeQuery(query_3);
            for( Categories c : CategoriesList) {
                for (String s : list_cat) {
                    if (s.equals(c.getName_category())) {
                        String query_2 = " INSERT INTO Category_Entertainment VALUES ( " + list.get(list.size() - 1).getID_Entertainment() + ", " +
                                c.getID_category() + ")";

                        Connection_db.executeQuery(query_2);
                    }
                }
            }
        }
    }

    public void setCategory() {
        CategoriesList = Connection_db.getCategories("SELECT * FROM Category_E");
        ObservableList<String> category_List = FXCollections.observableArrayList();

        for(Categories e : CategoriesList) {
            category_List.add(e.getName_category());
        }
        category.setItems(category_List);
        category.setValue(category_List.get(category_List.size()-1));
    }

    public void GetAllCategories_of_enter() {
        String query = "SELECT ch1.ID_category, ch1.Name_category " +
                "FROM Category_E ch1 " +
                "JOIN Category_Entertainment ch ON ch.ID_category = ch1.ID_category " +
                "JOIN Entertainment h ON ch.ID_entertainment = h.ID_Entertainment " +
                "WHERE h.ID_Entertainment = " + entertainment.getID_Entertainment();
        CategoriesList_Update = Connection_db.getCategories(query);
        StringBuilder s = new StringBuilder("");
        for(Categories c : CategoriesList_Update)  {
            s.append(c.getName_category()).append(" ");
        }
        Category_tooltip.setText(String.valueOf(s));
        CategoriesList  = Connection_db.getCategories("SELECT * FROM Category_E");
        ObservableList<String> category_str = FXCollections.observableArrayList();

        for(Categories e : CategoriesList) {
            category_str.add(e.getName_category());
        }
        category.setItems(category_str);
        category.setValue(category_str.get(category_str.size()-1));
    }

    private  StringBuilder string = new StringBuilder(" ");
    public void add_category(ActionEvent actionEvent) {
        list_cat.add(category.getValue());
        string.append(category.getValue()).append(" ");
        Category_tooltip.setText(String.valueOf(string));
    }
}
