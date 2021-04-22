package sample.db_classes;


import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connection_db {
    static public Connection GetConnection() {
        String connectionUrl =
                "jdbc:sqlserver://localhost:1433;database=Green_resort_Coursework;sendStringParametersAsUnicode=true";
        String name = "sa";
        String password = "Mysql1892";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(connectionUrl, name, password);
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    static public void executeQuery(String query) {
        Connection conn = Connection_db.GetConnection();
        Statement st;
        try {
            assert conn != null;
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static public void Get_Dialog(Parent parent) throws IOException {
        Scene scene = new Scene(parent, 490, 628);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    static public void closeWindow(ActionEvent actionEvent) {
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    static public void Cancel_Dialog(String query)
    {
        ButtonType OK = new ButtonType("Видалити", ButtonBar.ButtonData.OK_DONE);
        ButtonType CANCEL = new ButtonType("Скасувати", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.NONE, "Ви впевнені, що хочете видалити?", OK, CANCEL);
        alert.showAndWait();
        if (alert.getResult() == OK) {
            executeQuery(query);
        }
    }
}
