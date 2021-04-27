package sample.db_classes;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

import java.sql.Date;
import java.sql.Time;

public class Options_enter {
    private int ID_Options;
    private Date Date_options;
    private Time Time_options;
    private byte Count_people_options;
    private int ID_tours_enter;
    private String Enters;
    private ChoiceBox<String> clients;

    public Options_enter(int ID_Options, Date date_options, Time time_options, byte count_people_options, int ID_tours_enter ,String enters, ObservableList<String> clients) {
        this.ID_Options = ID_Options;
        Date_options = date_options;
        Time_options = time_options;
        Count_people_options = count_people_options;
        this.ID_tours_enter = ID_tours_enter;
        Enters = enters;
        this.clients = new ChoiceBox<>();
        this.clients.setItems(clients);
        this.clients.setValue(clients.get(0));
    }

    public int getID_Options() {
        return ID_Options;
    }

    public void setID_Options(int ID_Options) {
        this.ID_Options = ID_Options;
    }

    public Date getDate_options() {
        return Date_options;
    }

    public void setDate_options(Date date_options) {
        Date_options = date_options;
    }

    public Time getTime_options() {
        return Time_options;
    }

    public void setTime_options(Time time_options) {
        Time_options = time_options;
    }

    public byte getCount_people_options() {
        return Count_people_options;
    }

    public void setCount_people_options(byte count_people_options) {
        Count_people_options = count_people_options;
    }

    public String getEnters() {
        return Enters;
    }

    public void setEnters(String enters) {
        Enters = enters;
    }

    public ObservableList<String> getClients() {
        return clients.getItems();
    }

    public void setClients(ObservableList<String> clients) {
        this.clients.setItems(clients);
        this.clients.setValue(clients.get(0));
    }

    public int getID_enter_tours() {
        return ID_tours_enter;
    }

    public void setID_enter_tours(int ID_tours_enter) {
        this.ID_tours_enter = ID_tours_enter;
    }
}
