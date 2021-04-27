package sample.options_enter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import sample.db_classes.Connection_db;
import sample.db_classes.Entertainments;
import sample.db_classes.Options_enter;
import sample.db_classes.Tours;
import sample.tours.Tours_controller;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Add_update_options_enter {
    @FXML
    private Label Name_window;
    @FXML
    private Button Add_btn;
    @FXML
    private ChoiceBox<Byte> Count_box;
    @FXML
    private DatePicker Date_picker;
    @FXML
    private ChoiceBox<LocalTime> Time_box;


    private ObservableList<Entertainments> entertainments;
    private String entertainments_str;
    private Options_enter_controller oc;
    private boolean Add_update;
    private Tours tours;
    private Entertainments enter;
    private Options_enter options_enter;

    public void setOptions_enter(Options_enter options_enter) {
        this.options_enter = options_enter;
    }
    public void setValues() {
        Count_box.setValue(options_enter.getCount_people_options());
        Date_picker.setValue(options_enter.getDate_options().toLocalDate());
        Time_box.setValue(options_enter.getTime_options().toLocalTime());
    }

    public void SetEnters(ObservableList<Entertainments> entertainments, String entertainments_str, Options_enter_controller oc, boolean Add_update, Tours tours) {
        this.entertainments = entertainments;
        this.entertainments_str = entertainments_str;
        this.oc = oc;
        this.Add_update = Add_update;
        this.tours = tours;

        Date_picker.setConverter(new StringConverter<LocalDate>() {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                Date_picker.setPromptText(pattern.toLowerCase());
            }

            @Override public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });


        enter = entertainments.get(0);
        for(Entertainments e : entertainments) {
            if(e.getName_entertainment().equals(entertainments_str)) {
                enter = e;
                break;
            }
        }


        ObservableList<Byte> count_people = FXCollections.observableArrayList();
        for (byte i = enter.getMax_People_entertainment(); i != 0; i--) {
            count_people.add(i);
        }
        Count_box.setItems(count_people);
        if(count_people.size() != 0) {
            Count_box.setValue(count_people.get(0));
        }

        ObservableList<LocalTime> time_set = FXCollections.observableArrayList();

        for(LocalTime t = enter.getTime_start_entertainment().toLocalTime(); t != enter.getTime_end_entertainment().toLocalTime(); t = t.plusHours(enter.getDuration())) {
            time_set.add(t);
        }
        time_set.add(enter.getTime_end_entertainment().toLocalTime());

        Time_box.setItems(time_set);
        if(time_set.size() != 0) {
            Time_box.setValue(time_set.get(0));
        }


        Date_picker.setDayCellFactory(d ->
                new DateCell() {
                    @Override public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isAfter(tours.getDate_end_tour().toLocalDate()) || item.isBefore(tours.getDate_start_tour().toLocalDate()));
                    }});



    }

    public void Add_method(ActionEvent actionEvent) {
        int id_te = GetIdEnter_tours("SELECT te.ID_TEN " +
                "FROM Tours_entertainment te " +
                "WHERE te.ID_entertainments = " + enter.getID_Entertainment() + " AND te.ID_tours = " + tours.getID_tours(), "ID_TEN");
        String query;

        int count = GetIdEnter_tours("SELECT SUM(o.Count_people_options) AS Count_people_options " +
                "FROM [Options] o " +
                "JOIN Tours_entertainment te ON te.ID_TEN = o.ID_tours_enter " +
                "WHERE o.Date_options = '" + Date.valueOf(Date_picker.getValue()) + "' AND o.Time_options = '" + Time.valueOf(Time_box.getValue()) +
                "' AND te.ID_entertainments = " + enter.getID_Entertainment(), "Count_people_options");

        if(count + Count_box.getValue() > enter.getMax_People_entertainment()) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Вибачте, але місць на цю розвагу вже немає. Виберіть інший час або дату. Кількість доступних місць: " + (enter.getMax_People_entertainment() - count), ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                return;
            }
        }

        if(Add_update) {
            query = "INSERT INTO Options VALUES ( '" +
                    Date.valueOf(Date_picker.getValue()) + "' , " +
                    Count_box.getValue() + ", '" +
                    Time.valueOf(Time_box.getValue()) + "', " +
                    id_te + ")";
        } else {
            query = "UPDATE Options SET Date_options = '" + Date.valueOf(Date_picker.getValue())  + "', Count_people_options = " +
                    Count_box.getValue() + ", Time_options = '" + Time.valueOf(Time_box.getValue()) + "' WHERE ID_Options = " +
                    options_enter.getID_Options();
        }
        Connection_db.executeQuery(query);
        oc.ShowOptions();
        Connection_db.closeWindow(actionEvent);
    }

    private int GetIdEnter_tours(String query, String name) {
        Connection conn = Connection_db.GetConnection();
        Statement st;
        ResultSet rs;
        int id = 0;

        try {
            if(conn != null) {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                while (rs.next()) {
                    id = rs.getInt(name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }




}
