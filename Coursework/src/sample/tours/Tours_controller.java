package sample.tours;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.clients.Add_update_clients;
import sample.clients.Clients_controller;
import sample.db_classes.*;
import sample.entertainments.Entertainment_controller;
import sample.homesteads.Homesteads_controller;
import sample.options_enter.Options_enter_controller;
import sample.other_windows.Categories_controller;
import sample.start_window.Start_window_controller;
import sample.workers.Add_update_workers;
import sample.workers.Workers_controller;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;






public class Tours_controller {


    private final String FONT_NOTEWORTHY = "src/sample/fonts/Noteworthy-Lt.ttf";
    @FXML
    private TextField search_field;
    @FXML
    private ImageView Back_img;
    @FXML
    private AnchorPane root;
    @FXML
    private TableView<Tours> table_tours;
    @FXML
    private TableColumn<Tours, Boolean> Breakfast_col;
    @FXML
    private TableColumn<Tours, Float> Price_col;
    @FXML
    private TableColumn<Tours, Date> Date_start_col;
    @FXML
    private TableColumn<Tours, Date> Date_end_col;
    @FXML
    private TableColumn<Tours, Boolean> Active_col;
    @FXML
    private TableColumn<Tours, String> Homesteads_col;
    @FXML
    private TableColumn<Tours, ChoiceBox<String>> Clients_col;
    @FXML
    private TableColumn<Tours, ChoiceBox<String>> Enter_col;
    @FXML
    private TableColumn<Tours, ChoiceBox<String>> Worker_col;



    private int Category_tours = 0;
    private String worker;
    public void setCategory(int Category, String workers) {
        worker = workers;
        Category_tours = Category;
    }



    public void initialize() {
        Back_img.setPickOnBounds(true);
        ShowTours();
    }

    public void ShowTours() {

        String query = " ";
        if (Category_tours == 2) {
            query = "SELECT * FROM Tour t WHERE t.Is_active_tours = 1";
        } else if (Category_tours == 3) {
            query = " SELECT * FROM Tour t WHERE t.Is_active_tours = 0";
        } else {
            query = "SELECT * FROM Tour";
        }

        ObservableList<Tours> list = getTours(query);

        Breakfast_col.setCellValueFactory(new PropertyValueFactory<>("IsBreakfast_tours"));
        Price_col.setCellValueFactory(new PropertyValueFactory<>("Cost_tour"));
        Date_start_col.setCellValueFactory(new PropertyValueFactory<>("Date_start_tour"));
        Date_end_col.setCellValueFactory(new PropertyValueFactory<>("Date_end_tour"));
        Active_col.setCellValueFactory(new PropertyValueFactory<>("Is_active_tours"));
        Homesteads_col.setCellValueFactory(new PropertyValueFactory<>("homestead"));
        Clients_col.setCellValueFactory(new PropertyValueFactory<>("clientsChoiceBox"));
        Enter_col.setCellValueFactory(new PropertyValueFactory<>("entertainmentsChoiceBox"));
        Worker_col.setCellValueFactory(new PropertyValueFactory<>("workersChoiceBox"));

        FilteredList<Tours> filteredData = new FilteredList<>(list, b -> true);

        search_field.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(tours -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if(tours.getDate_start_tour().toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(tours.getDate_end_tour().toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(tours.getHomestead().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(tours.getWorkersChoiceBox().toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(tours.getClientsChoiceBox().toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(tours.getEntertainmentsChoiceBox().toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Tours> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(table_tours.comparatorProperty());

        table_tours.setItems(sortedData);
        table_tours.refresh();
    }

    private ObservableList<Tours> getTours(String query) {
        ObservableList<Tours> ToursList = FXCollections.observableArrayList();
        Connection conn = Connection_db.GetConnection();
        Statement st;
        ResultSet rs;
        int ID_homestead;
        String homestead;
        ObservableList<String> clients_list = FXCollections.observableArrayList();
        ObservableList<String> entertainments_list = FXCollections.observableArrayList();
        ObservableList<String> workers_list = FXCollections.observableArrayList();

        try {
            if(conn != null) {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                Tours tours;

                while (rs.next()) {
                    ID_homestead = rs.getInt("ID_homestead");
                    if (rs.wasNull()) {
                        ID_homestead = -1;
                    }
                    if(ID_homestead == -1)
                        homestead = " ";
                    homestead = getHomesteadQuery("SELECT Name_homestead FROM Homesteads WHERE ID_homestead = " + ID_homestead);
                    clients_list = getClientsQuery("SELECT Surname_client, Name_client, Phone_number_client " +
                            "FROM Clients_tours ct " +
                            "JOIN Tour t ON ct.ID_tours = t.ID_tours " +
                            "JOIN Client c ON ct.ID_clients = c.ID_client " +
                            "WHERE ct.ID_tours = " + rs.getInt("ID_tours"));
                    workers_list = getWorkersQuery("SELECT Surname_worker, Name_worker, Phone_number_worker " +
                            "FROM Tours_worker tw " +
                            "JOIN Tour t ON t.ID_tours = tw.ID_tours " +
                            "JOIN Worker w ON tw.ID_workers = w.ID_workers " +
                            "WHERE tw.ID_tours = " + rs.getInt("ID_tours"));
                    entertainments_list = getEnterQuery("SELECT Name_entertainment " +
                            "FROM Tours_entertainment te " +
                            "JOIN Tour t ON t.ID_tours = te.ID_tours " +
                            "JOIN Entertainment e ON te.ID_entertainments = e.ID_Entertainment " +
                            "WHERE te.ID_tours = " + rs.getInt("ID_tours"));

                    tours = new Tours(rs.getInt("ID_tours"), rs.getBoolean("IsBreakfast_tours"),
                            rs.getFloat("Cost_tour"), rs.getDate("Date_start_tour"), rs.getDate("Date_end_tour"), rs.getBoolean("Is_active_tours"),
                            homestead, clients_list, entertainments_list, workers_list);
                    ToursList.add(tours);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ToursList;
    }

    private String getHomesteadQuery(String query) {
        Connection conn = Connection_db.GetConnection();
        Statement st;
        ResultSet rs;
        String homesteads = new String(" ");
        try {
            if(conn != null) {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                while (rs.next()) {
                   homesteads = rs.getString("Name_homestead");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return homesteads;
    }

    private ObservableList<String> getWorkersQuery(String query) {
        Connection conn = Connection_db.GetConnection();
        Statement st;
        ResultSet rs;
        String workers = new String(" ");
        ObservableList<String> workers_list = FXCollections.observableArrayList();
        try {
            if(conn != null) {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                while (rs.next()) {
                    workers = rs.getString("Surname_worker") + " " + rs.getString("Name_worker") + " " + rs.getString("Phone_number_worker");
                    workers_list.add(workers);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workers_list;
    }

    public static ObservableList<String> getClientsQuery(String query) {
        Connection conn = Connection_db.GetConnection();
        Statement st;
        ResultSet rs;
        String clients = new String(" ");
        ObservableList<String> clients_list = FXCollections.observableArrayList();
        try {
            if(conn != null) {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                while (rs.next()) {
                    clients = rs.getString("Surname_client") + " " + rs.getString("Name_client") + " " + rs.getString("Phone_number_client");
                    clients_list.add(clients);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clients_list;
    }

    public static ObservableList<String> getEnterQuery(String query) {
        Connection conn = Connection_db.GetConnection();
        Statement st;
        ResultSet rs;
        String enters = new String(" ");
        ObservableList<String> enters_list = FXCollections.observableArrayList();
        try {
            if(conn != null) {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                while (rs.next()) {
                    enters = rs.getString("Name_entertainment");
                    enters_list.add(enters);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enters_list;
    }


    public void Add_method(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Add_update_tours.fxml"));
        Parent parent = fxmlLoader.load();
        Add_update_tours add_update_tours = fxmlLoader.getController();
        add_update_tours.setController(this);
        add_update_tours.setAdd_Update(true);
        Connection_db.Get_Dialog(parent, 490, 680);
    }

    public void Update_method(ActionEvent actionEvent) throws IOException {
        Tours tours = table_tours.getSelectionModel().getSelectedItem();
        if(tours != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Add_update_tours.fxml"));
            Parent parent = fxmlLoader.load();
            Add_update_tours add_update_tours = fxmlLoader.getController();
            add_update_tours.setController(this);
            add_update_tours.setAdd_Update(false);
            add_update_tours.setTour(tours);
            add_update_tours.setValues();
            Connection_db.Get_Dialog(parent, 490, 680);
        }
    }

    public void Delete_method(ActionEvent actionEvent) throws IOException {
        Tours tours = table_tours.getSelectionModel().getSelectedItem();
        if(tours != null) {

            String query = "BEGIN TRY BEGIN TRAN " +
                    "DELETE FROM Clients_tours WHERE ID_tours = " + tours.getID_tours() +
                    " DELETE o FROM [Options] o JOIN Tours_entertainment t ON o.ID_tours_enter = t.ID_TEN WHERE t.ID_tours = " + tours.getID_tours() +
                    " DELETE FROM Tours_entertainment WHERE ID_tours = " + tours.getID_tours() +
                    " DELETE FROM Tours_worker WHERE ID_tours = " + tours.getID_tours() +
                    " DELETE FROM Tour WHERE ID_tours = " + tours.getID_tours() +
                    " COMMIT TRAN END TRY BEGIN CATCH " +
                    "SELECT error_message() AS ErrorMessage " +
                    "ROLLBACK TRAN END CATCH";
            new Connection_db().Cancel_Dialog(query);
            ShowTours();
        }
    }

    public void Options_method(ActionEvent actionEvent) throws IOException {
        Tours tours = table_tours.getSelectionModel().getSelectedItem();
        if(tours != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/options_enter/Options_enter.fxml"));
            Parent parent = fxmlLoader.load();
            Options_enter_controller options_enter_controller = fxmlLoader.getController();
            options_enter_controller.SetTour(tours);
            options_enter_controller.GetEnters();
            options_enter_controller.setFromStartWindow(false, 4, "");
            options_enter_controller.ShowOptions();
            Connection_db.Get_Dialog(parent, 1000, 650);
        }
    }


    public void Go_back(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/other_windows/Categories.fxml"));
        Parent parent = fxmlLoader.load();
        Categories_controller categories_controller = fxmlLoader.getController();
        categories_controller.SetWorker(worker);
        categories_controller.setWindow(1);
        categories_controller.setButtons();
        root.getChildren().setAll(parent);

    }

    public void GeneratePDF() {
        String path = "/Users/yuraslipenkyi/Documents/Java/Green_resort/PDF_doc/ticket.pdf";
        try {
            PdfWriter writer = new PdfWriter(path);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            PdfFont font1250 = PdfFontFactory.createFont(FONT_NOTEWORTHY, "Cp1251", true);

            document.add(new Paragraph().setFont(font1250)
                    .add("Курорт зеленого туризму \"Інтермецо\" ").setTextAlignment(TextAlignment.CENTER));

            Tours tours = table_tours.getSelectionModel().getSelectedItem();
            if(tours != null) {

                String query = "SELECT e.Name_entertainment, o.Date_options, o.Time_options FROM [Options] o JOIN Tours_entertainment te ON te.ID_TEN = o.ID_tours_enter JOIN Entertainment e ON e.ID_Entertainment = te.ID_entertainments WHERE te.ID_tours = " + tours.getID_tours();

                ObservableList<String> options = getOptions(query);

                document.add(new Paragraph().setFont(font1250)
                        .add("Клієнти: " + tours.getClientsChoiceBox().toString() + "\nРозваги: " + options.toString()
                        +"\n Гіди: " + tours.getWorkersChoiceBox().toString()
                                +"\n Садиба: " + tours.getHomestead()
                                +"\n Cніданок: " + tours.getIsBreakfast_tours()
                                +"\n Ціна туру: " + tours.getCost_tour()
                                +"\n Дата початку: " + tours.getDate_start_tour()
                                +"\n Дата кінця: " + tours.getDate_end_tour()));

                document.add(new Paragraph().setFont(font1250)
                        .add("Бажаємо хорошого відпочинку!").setTextAlignment(TextAlignment.CENTER));
            }

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GeneratePDF_method(ActionEvent actionEvent) {
        GeneratePDF();
    }

    public ObservableList<String> getOptions(String query) {
        ObservableList<String> OptionsList = FXCollections.observableArrayList();
        Connection conn = Connection_db.GetConnection();
        Statement st;
        ResultSet rs;

        try {
            if(conn != null) {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                String options_enter;
                while (rs.next()) {
                    options_enter = rs.getString("Name_entertainment") + "  " + rs.getDate("Date_options").toString() + " " + rs.getTime("Time_options").toString();
                    OptionsList.add(options_enter);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OptionsList;
    }

}
