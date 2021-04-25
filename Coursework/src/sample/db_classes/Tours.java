package sample.db_classes;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

import java.sql.Date;

public class Tours {
    private int ID_tours;
    private boolean IsBreakfast_tours;
    private float Cost_tour;
    private Date Date_start_tour;
    private Date Date_end_tour;
    private boolean Is_active_tours;
    private String homestead;
    private ChoiceBox<String> clientsChoiceBox;
    private ChoiceBox<String> entertainmentsChoiceBox;
    private ChoiceBox<String> workersChoiceBox;

    public Tours(int ID_tours, boolean isBreakfast_tours, float cost_tour, Date date_start_tour, Date date_end_tour, boolean is_active_tours, String homestead, ObservableList<String> clients, ObservableList<String> entertainments, ObservableList<String> workers) {
        this.ID_tours = ID_tours;
        IsBreakfast_tours = isBreakfast_tours;
        Cost_tour = cost_tour;
        Date_start_tour = date_start_tour;
        Date_end_tour = date_end_tour;
        Is_active_tours = is_active_tours;
        this.homestead = homestead;
        this.clientsChoiceBox.setItems(clients);
        this.clientsChoiceBox.setValue(clients.get(0));
        this.entertainmentsChoiceBox.setItems(entertainments);
        this.entertainmentsChoiceBox.setValue(entertainments.get(0));
        this.workersChoiceBox.setItems(workers);
        this.workersChoiceBox.setValue(workers.get(0));
    }

    public int getID_tours() {
        return ID_tours;
    }

    public void setID_tours(int ID_tours) {
        this.ID_tours = ID_tours;
    }

    public boolean isBreakfast_tours() {
        return IsBreakfast_tours;
    }

    public void setBreakfast_tours(boolean breakfast_tours) {
        IsBreakfast_tours = breakfast_tours;
    }

    public float getCost_tour() {
        return Cost_tour;
    }

    public void setCost_tour(float cost_tour) {
        Cost_tour = cost_tour;
    }

    public Date getDate_start_tour() {
        return Date_start_tour;
    }

    public void setDate_start_tour(Date date_start_tour) {
        Date_start_tour = date_start_tour;
    }

    public Date getDate_end_tour() {
        return Date_end_tour;
    }

    public void setDate_end_tour(Date date_end_tour) {
        Date_end_tour = date_end_tour;
    }

    public boolean isIs_active_tours() {
        return Is_active_tours;
    }

    public void setIs_active_tours(boolean is_active_tours) {
        Is_active_tours = is_active_tours;
    }

    public String getHomestead() {
        return homestead;
    }

    public void setHomestead(String homestead) {
        this.homestead = homestead;
    }

    public ChoiceBox<String> getClientsChoiceBox() {
        return clientsChoiceBox;
    }

    public void setClientsChoiceBox(ObservableList<String> clients) {
        this.clientsChoiceBox.setItems(clients);
    }

    public ChoiceBox<String> getEntertainmentsChoiceBox() {
        return entertainmentsChoiceBox;
    }

    public void setEntertainmentsChoiceBox(ObservableList<String> entertainments) {
        this.entertainmentsChoiceBox.setItems(entertainments);
    }

    public ChoiceBox<String> getWorkersChoiceBox() {
        return workersChoiceBox;
    }

    public void setWorkersChoiceBox(ObservableList<String> workers) {
        this.workersChoiceBox.setItems(workers);
    }
}
