package sample.tours;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import sample.clients.Clients_controller;
import sample.db_classes.*;
import sample.entertainments.Entertainment_controller;
import sample.homesteads.Homesteads_controller;
import sample.workers.Workers_controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Add_update_tours {

    @FXML
    private Label Name_window;
    @FXML
    private Button Add_btn;
    @FXML
    private DatePicker Date_end;
    @FXML
    private TextField Price;
    @FXML
    private ChoiceBox<String> clients_box;
    @FXML
    private ChoiceBox<String> enter_box;
    @FXML
    private ChoiceBox<String> workers_box;
    @FXML
    private Label home_label;
    @FXML
    private CheckBox Is_breakfast;
    @FXML
    private DatePicker Date_start;
    @FXML
    private CheckBox Is_active;

    private float PRICE_BREAKFAST = 50.0f;
    private Set<Clients> clients;
    private Set<Entertainments> entertainments;
    private Set<Workers> workers;
    private ObservableList<String> clients_str;
    private ObservableList<String> entertainments_str;
    private ObservableList<String> workers_str;
    private Homesteads homesteads;
    private Tours_controller tc;
    private boolean Add_Update;
    private Tours tours;
    private boolean b_clients = false;
    private boolean b_enters = false;
    private boolean b_workers = false;

    public void setController(Tours_controller tc) {
        this.tc = tc;
    }
    public void setAdd_Update(boolean b) {
        Add_Update = b;
        if(Add_Update)
            Price.setDisable(true);
    }
    public void setTour(Tours tours) {
        this.tours = tours;
    }

    public void initialize() {
        clients = new HashSet<>();
        entertainments = new HashSet<>();
        workers = new HashSet<>();
        clients_str = FXCollections.observableArrayList();
        entertainments_str = FXCollections.observableArrayList();
        workers_str = FXCollections.observableArrayList();

        Price.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*(\\.\\d*)?")) {
                    Price.setText(oldValue);
                }
            }
        });



        Date_start.setConverter(new StringConverter<LocalDate>() {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                Date_start.setPromptText(pattern.toLowerCase());
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

        Date_end.setConverter(new StringConverter<LocalDate>() {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                Date_end.setPromptText(pattern.toLowerCase());
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

        Date_start.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) < 0 );
            }
        });


    }

    public void setValues() {
        Is_breakfast.setSelected(tours.getIsBreakfast_tours());
        Is_active.setSelected(tours.isIs_active_tours());
        Date_start.setValue(tours.getDate_start_tour().toLocalDate());
        Date_end.setValue(tours.getDate_end_tour().toLocalDate());
        Price.setText(String.valueOf(tours.getCost_tour()));
        home_label.setText(tours.getHomestead());
        clients_box.setItems(tours.getClientsChoiceBox());
        if(tours.getClientsChoiceBox().size() != 0)
        clients_box.setValue(tours.getClientsChoiceBox().get(0));
        workers_box.setItems(tours.getWorkersChoiceBox());
        if(tours.getWorkersChoiceBox().size() != 0)
        workers_box.setValue(tours.getWorkersChoiceBox().get(0));
        enter_box.setItems(tours.getEntertainmentsChoiceBox());
        if(tours.getEntertainmentsChoiceBox().size() != 0)
        enter_box.setValue(tours.getEntertainmentsChoiceBox().get(0));

        if(!Add_Update) {
            Name_window.setText("Оновити тур");
            Add_btn.setText("Оновити");
        }

        ObservableList<Clients> clients_list_obs = Clients_controller.getClients("SELECT * FROM Client c " +
                "JOIN Clients_tours ct ON ct.ID_clients = c.ID_client " +
                "WHERE ct.ID_tours =  " + tours.getID_tours());
        ObservableList<Entertainments> enters_list_obs = Entertainment_controller.getEntertainments("SELECT * FROM Entertainment e " +
                "JOIN Tours_entertainment te ON e.ID_Entertainment = te.ID_entertainments " +
                "WHERE te.ID_tours =  " + tours.getID_tours());
        ObservableList<Workers> workers_list_obs = Workers_controller.getWorkers("SELECT * FROM Worker w " +
                "JOIN Tours_worker tw ON w.ID_workers = tw.ID_workers " +
                "WHERE tw.ID_tours = " + tours.getID_tours());

        clients.addAll(clients_list_obs);
        entertainments.addAll(enters_list_obs);
        workers.addAll(workers_list_obs);
    }



    public Set<Workers> getWorkers() {
        return workers;
    }
    public Set<Clients> getClients() {
        return clients;
    }
    public Set<Entertainments> getEntertainments() {
        return entertainments;
    }
    public Homesteads getHomesteads() {
        return homesteads;
    }

    public void AddClients(Clients c) {
        clients.add(c);
    }
    public void DeleteClients(Clients c) { clients.remove(c); }

    public void AddWorkers(Workers c) {
        workers.add(c);
    }
    public void DeleteWorkers(Workers c) { workers.remove(c); }

    public void AddEntertainments(Entertainments e) {
        entertainments.add(e);
    }
    public void DeleteEntertainments(Entertainments e) { entertainments.remove(e); }

    public void AddHomesteads(Homesteads h) {
        homesteads = h;
    }


    public void SetEntertainments() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/entertainments/Entertainments.fxml"));
        Parent parent = fxmlLoader.load();
        Entertainment_controller entertainment_controller = fxmlLoader.getController();
        entertainment_controller.setTours(this);
        entertainment_controller.SetChoiceBox();

        entertainment_controller.ShowEntertainments();
        Connection_db.Get_Dialog(parent, 1000, 650);
    }


    public void SetClients() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/clients/Clients.fxml"));
        Parent parent = fxmlLoader.load();
        Clients_controller clients_controller = fxmlLoader.getController();
        clients_controller.setTours(this);
        clients_controller.SetChoiceBox();
        Connection_db.Get_Dialog(parent, 1000, 650);
    }

    public void SetHomestead() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/homesteads/homesteads.fxml"));
        Parent parent = fxmlLoader.load();
        Homesteads_controller homesteads_controller = fxmlLoader.getController();
        homesteads_controller.setTours(this);
        homesteads_controller.SetLabel();
        homesteads_controller.ShowHomesteads();
        Connection_db.Get_Dialog(parent, 1000, 650);
    }

    public void SetWorkers() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/workers/Workers.fxml"));
        Parent parent = fxmlLoader.load();
        Workers_controller workers_controller = fxmlLoader.getController();
        workers_controller.setTours(this);
        workers_controller.SetChoiceBox();
        Connection_db.Get_Dialog(parent, 1000, 650);
    }


    public void add_clients(ActionEvent actionEvent) throws IOException {
        b_clients = true;
        SetClients();
        clients_str.clear();
        for(Clients c: clients) {
            clients_str.add(c.getSurname_client() + " " + c.getName_client() + " " + c.getPhone_number_client());
        }
        clients_box.setItems(clients_str);
        if(clients_str.size() != 0 )
        clients_box.setValue(clients_str.get(0));
    }

    public void add_enter(ActionEvent actionEvent) throws IOException  {
        b_enters = true;
        SetEntertainments();
        entertainments_str.clear();
        for(Entertainments e: entertainments) {
            entertainments_str.add(e.getName_entertainment());
        }
        enter_box.setItems(entertainments_str);
        if(entertainments_str.size() != 0 )
        enter_box.setValue(entertainments_str.get(0));
    }

    public void add_workers(ActionEvent actionEvent) throws IOException  {
        b_workers = true;
        SetWorkers();
        workers_str.clear();
        for(Workers c: workers) {
            workers_str.add(c.getSurname_worker() + " " + c.getName_worker() + " " + c.getPhone_number_worker());
        }
        workers_box.setItems(workers_str);
        if(workers_str.size() != 0 )
        workers_box.setValue(workers_str.get(0));
    }

    public void add_home(ActionEvent actionEvent) throws IOException  {
        SetHomestead();
        home_label.setText(homesteads.getName_homestead());
    }

    public void Add_method(ActionEvent actionEvent) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            format.parse(Date_start.getValue().toString());
            format.parse(Date_end.getValue().toString());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Правильний формат дати \"yyyy-MM-dd\" ", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                return;
            }
        }

        String query;
        if(Add_Update) {
            float price = 0;
            long days = getDifferenceDays(Date.valueOf(Date_start.getValue()), Date.valueOf(Date_end.getValue()));
            if (Is_breakfast.isSelected()) {
                price += days * PRICE_BREAKFAST + homesteads.getPrice_homestead();
            } else {
                price += homesteads.getPrice_homestead();
            }

            query = "INSERT INTO Tour VALUES ( " +
                    (Is_breakfast.isSelected() ? 1 : 0) + ", " +
                    price + ", '" +
                    Date.valueOf(Date_start.getValue()) + "', '" +
                    Date.valueOf(Date_end.getValue()) + "', " +
                    homesteads.getID_homestead() + ", " +
                    (Is_active.isSelected() ? 1 : 0) + ")";
        } else {
            query = "d";

            int id_homestead = -1;
            if(homesteads != null) {
                id_homestead = homesteads.getID_homestead();
            } else {
                id_homestead = getID_homestead();
            }

            query = "UPDATE Tour SET IsBreakfast_tours = " + (Is_breakfast.isSelected() ? 1 : 0)  + ", Cost_tour = " +
                    Float.parseFloat(Price.getText()) + ", Date_start_tour = '" + Date.valueOf(Date_start.getValue()) + "', Date_end_tour = '" +
                    Date.valueOf(Date_end.getValue()) + "', ID_homestead = " + id_homestead + ", Is_active_tours = " +
                    (Is_active.isSelected() ? 1 : 0) + " WHERE ID_tours = " +
                    tours.getID_tours();
        }

        Connection_db.executeQuery(query);
        AddOtherData();
        tc.ShowTours();
        Connection_db.closeWindow(actionEvent);
    }

    private void AddOtherData() {
        int id_tour = getID_tour();
        if(b_clients) {
            if(!Add_Update) {
                Connection_db.executeQuery("DELETE FROM Clients_tours WHERE ID_tours = " + tours.getID_tours());
                for (Clients c : clients) {
                    Connection_db.executeQuery("INSERT INTO Clients_tours VALUES ( " + tours.getID_tours() + ", " +
                            c.getID_client() + " )");
                }
            } else {
                for (Clients c : clients) {
                    Connection_db.executeQuery("INSERT INTO Clients_tours VALUES ( " + id_tour + ", " +
                            c.getID_client() + " )");
                }
            }
        }
        if(b_enters) {
            if(!Add_Update) {
                Connection_db.executeQuery("DELETE FROM Tours_entertainment WHERE ID_tours = " + tours.getID_tours());
                for (Entertainments e : entertainments) {
                    Connection_db.executeQuery("INSERT INTO Tours_entertainment VALUES ( " + tours.getID_tours() + ", " +
                            e.getID_Entertainment() + " )");
                }
            } else {
                for (Entertainments e : entertainments) {
                    Connection_db.executeQuery("INSERT INTO Tours_entertainment VALUES ( " + id_tour + ", " +
                            e.getID_Entertainment() + " )");
                }
            }
        }
        if(b_workers) {
            if(!Add_Update) {
                Connection_db.executeQuery("DELETE FROM Tours_worker WHERE ID_tours = " + tours.getID_tours());
                for (Workers w : workers) {
                    Connection_db.executeQuery("INSERT INTO Tours_worker VALUES ( " + tours.getID_tours() + ", " +
                            w.getID_workers() + " )");
                }
            } else {
                for (Workers w : workers) {
                    Connection_db.executeQuery("INSERT INTO Tours_worker VALUES ( " + id_tour + ", " +
                            w.getID_workers() + " )");
                }
            }
        }
        b_clients = false;
        b_enters = false;
        b_workers = false;
    }

    private int getID_tour() {
        Connection conn = Connection_db.GetConnection();
        String query = "SELECT ID_tours FROM Tour WHERE ID_tours = (SELECT MAX(ID_tours) FROM Tour)";
        Statement st;
        ResultSet rs;
        int id_tour = -1;
        try {
            if(conn != null) {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                while (rs.next()) {
                    id_tour = rs.getInt("ID_tours");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id_tour;
    }

    private int getID_homestead() {
        Connection conn = Connection_db.GetConnection();
        String query = "SELECT ID_homestead FROM Tour WHERE ID_tours = " + tours.getID_tours();
        Statement st;
        ResultSet rs;
        int ID_homestead = -1;
        try {
            if(conn != null) {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                while (rs.next()) {
                    ID_homestead = rs.getInt("ID_homestead");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ID_homestead;
    }

    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }


}
