package sample.db_classes;

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
    private ChoiceBox<Clients> clientsChoiceBox;
    private ChoiceBox<Entertainments> entertainmentsChoiceBox;
    private String worker;

    public Tours(int ID_tours, boolean isBreakfast_tours, float cost_tour, Date date_start_tour, Date date_end_tour, boolean is_active_tours, String homestead, ChoiceBox<Clients> clientsChoiceBox, ChoiceBox<Entertainments> entertainmentsChoiceBox, String worker) {
        this.ID_tours = ID_tours;
        IsBreakfast_tours = isBreakfast_tours;
        Cost_tour = cost_tour;
        Date_start_tour = date_start_tour;
        Date_end_tour = date_end_tour;
        Is_active_tours = is_active_tours;
        this.homestead = homestead;
        this.clientsChoiceBox = clientsChoiceBox;
        this.entertainmentsChoiceBox = entertainmentsChoiceBox;
        this.worker = worker;
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

    public ChoiceBox<Clients> getClientsChoiceBox() {
        return clientsChoiceBox;
    }

    public void setClientsChoiceBox(ChoiceBox<Clients> clientsChoiceBox) {
        this.clientsChoiceBox = clientsChoiceBox;
    }

    public ChoiceBox<Entertainments> getEntertainmentsChoiceBox() {
        return entertainmentsChoiceBox;
    }

    public void setEntertainmentsChoiceBox(ChoiceBox<Entertainments> entertainmentsChoiceBox) {
        this.entertainmentsChoiceBox = entertainmentsChoiceBox;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }
}
