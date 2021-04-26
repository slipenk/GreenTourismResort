package sample.db_classes;

import java.sql.Date;

public class Clients {
    private int ID_client;
    private String Surname_client;
    private String Name_client;
    private String Middle_name_client;
    private String Phone_number_client;
    private Date Date_birth_client;
    private String Document_id_client;
    private Date Registration_date_client;

    public Clients(int ID_client, String surname_client, String name_client, String middle_name_client, String phone_number_client, Date date_birth_client, String document_id_client, Date registration_date_client) {
        this.ID_client = ID_client;
        Surname_client = surname_client;
        Name_client = name_client;
        Middle_name_client = middle_name_client;
        Phone_number_client = phone_number_client;
        Date_birth_client = date_birth_client;
        Document_id_client = document_id_client;
        Registration_date_client = registration_date_client;
    }

    public int getID_client() {
        return ID_client;
    }

    public void setID_client(int ID_client) {
        this.ID_client = ID_client;
    }

    public String getSurname_client() {
        return Surname_client;
    }

    public void setSurname_client(String surname_client) {
        Surname_client = surname_client;
    }

    public String getName_client() {
        return Name_client;
    }

    public void setName_client(String name_client) {
        Name_client = name_client;
    }

    public String getMiddle_name_client() {
        return Middle_name_client;
    }

    public void setMiddle_name_client(String middle_name_client) {
        Middle_name_client = middle_name_client;
    }

    public String getPhone_number_client() {
        return Phone_number_client;
    }

    public void setPhone_number_client(String phone_number_client) {
        Phone_number_client = phone_number_client;
    }

    public Date getDate_birth_client() {
        return Date_birth_client;
    }

    public void setDate_birth_client(Date date_birth_client) {
        Date_birth_client = date_birth_client;
    }

    public String getDocument_id_client() {
        return Document_id_client;
    }

    public void setDocument_id_client(String document_id_client) {
        Document_id_client = document_id_client;
    }

    public Date getRegistration_date_client() {
        return Registration_date_client;
    }

    public void setRegistration_date_client(Date registration_date_client) {
        Registration_date_client = registration_date_client;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Clients))
            return false;
        if (obj == this)
            return true;
        return this.getID_client() == ((Clients) obj).getID_client();
    }

    @Override
    public int hashCode() {
        return ID_client;
    }

}
