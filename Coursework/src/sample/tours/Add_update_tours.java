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

    public void setController(Tours_controller tc) {
        this.tc = tc;
    }
    public void setAdd_Update(boolean b) {
        Add_Update = b;
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

        if(Add_Update)
        Price.setDisable(true);

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
                setDisable(empty || date.compareTo(LocalDate.now()) > 0 );
            }
        });
        Date_end.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) > 0 );
            }
        });

    }

    /*public void setValues() {
        Surname.setText(client.getSurname_client());
        Name.setText(client.getName_client());
        Middle_name.setText(client.getMiddle_name_client());
        Phone_number.setText(client.getPhone_number_client());
        Date_birth.setValue(client.getDate_birth_client().toLocalDate());
        Id_document.setText(client.getDocument_id_client());
        Date_reg.setValue(client.getRegistration_date_client().toLocalDate());
        if(!Add_Update) {
            Name_window.setText("Оновити клієнта");
            Add_btn.setText("Оновити");
        }
    }*/



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
        SetClients();
        clients_str.clear();
        for(Clients c: clients) {
            clients_str.add(c.getSurname_client() + " " + c.getName_client() + " " + c.getPhone_number_client());
        }
        clients_box.setItems(clients_str);
        clients_box.setValue(clients_str.get(0));
    }

    public void add_enter(ActionEvent actionEvent) throws IOException  {
        SetEntertainments();
        entertainments_str.clear();
        for(Entertainments e: entertainments) {
            entertainments_str.add(e.getName_entertainment());
        }
        enter_box.setItems(entertainments_str);
        enter_box.setValue(entertainments_str.get(0));
    }

    public void add_workers(ActionEvent actionEvent) throws IOException  {
        SetWorkers();
        workers_str.clear();
        for(Workers c: workers) {
            workers_str.add(c.getSurname_worker() + " " + c.getName_worker() + " " + c.getPhone_number_worker());
        }
        workers_box.setItems(workers_str);
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
            price += days * PRICE_BREAKFAST + homesteads.getPrice_homestead();

            query = "INSERT INTO Tour VALUES ( " +
                    (Is_breakfast.isSelected() ? 1 : 0) + ", " +
                    price + ", '" +
                    Date.valueOf(Date_start.getValue()) + "', '" +
                    Date.valueOf(Date_end.getValue()) + "', " +
                    homesteads.getID_homestead() + ", " +
                    (Is_active.isSelected() ? 1 : 0) + ")";
        } else {
            query = "d";
            /*query = "UPDATE Client SET Surname_client = N'" + Surname.getText().trim()  + "', Name_client = N'" +
                    Name.getText().trim() + "', Middle_name_client = N'" + Middle_name.getText().trim() + "', Phone_number_client = N'" +
                    Phone_number.getText().trim() + "', Date_birth_client = '" + Date.valueOf(Date_birth.getValue()) + "', Document_id_client = N'" +
                    Id_document.getText().trim() + "', Registration_date_client = '" + Date.valueOf(Date_reg.getValue()) + "' WHERE ID_client = " +
                    client.getID_client();*/
        }

        Connection_db.executeQuery(query);
        AddOtherData();
        tc.ShowTours();
        Connection_db.closeWindow(actionEvent);
    }

    private void AddOtherData() {
        int id_tour = getID_tour();
        for(Clients c : clients) {
            Connection_db.executeQuery("INSERT INTO Clients_tours VALUES ( " + id_tour + ", " +
            c.getID_client() + " )");
        }
        for(Entertainments e : entertainments) {
            Connection_db.executeQuery("INSERT INTO Tours_entertainment VALUES ( " + id_tour + ", " +
                    e.getID_Entertainment() + " )");
        }
        for(Workers w : workers) {
            Connection_db.executeQuery("INSERT INTO Tours_worker VALUES ( " + id_tour + ", " +
                    w.getID_workers() + " )");
        }
    }

    private int getID_tour() {
        Connection conn = Connection_db.GetConnection();
        String query = "SELECT ID_tours FROM Tour WHERE ID_tours = (SELECT MAX(ID_tours) FROM Tour);";
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

    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }


}
