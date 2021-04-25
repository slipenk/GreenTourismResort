package sample.db_classes;

import java.sql.Date;

public class Workers {
    private int ID_workers;
    private String Surname_worker;
    private String Name_worker;
    private String Middle_name_worker;
    private String Phone_number_worker;
    private Date Date_birth_worker;
    private String Job_worker;

    public Workers(int ID_workers, String surname_worker, String name_worker, String middle_name_worker, String phone_number_worker, Date date_birth_worker, String job_worker) {
        this.ID_workers = ID_workers;
        Surname_worker = surname_worker;
        Name_worker = name_worker;
        Middle_name_worker = middle_name_worker;
        Phone_number_worker = phone_number_worker;
        Date_birth_worker = date_birth_worker;
        Job_worker = job_worker;
    }

    public int getID_workers() {
        return ID_workers;
    }

    public void setID_workers(int ID_workers) {
        this.ID_workers = ID_workers;
    }

    public String getSurname_worker() {
        return Surname_worker;
    }

    public void setSurname_worker(String surname_worker) {
        Surname_worker = surname_worker;
    }

    public String getName_worker() {
        return Name_worker;
    }

    public void setName_worker(String name_worker) {
        Name_worker = name_worker;
    }

    public String getMiddle_name_worker() {
        return Middle_name_worker;
    }

    public void setMiddle_name_worker(String middle_name_worker) {
        Middle_name_worker = middle_name_worker;
    }

    public String getPhone_number_worker() {
        return Phone_number_worker;
    }

    public void setPhone_number_worker(String phone_number_worker) {
        Phone_number_worker = phone_number_worker;
    }

    public Date getDate_birth_worker() {
        return Date_birth_worker;
    }

    public void setDate_birth_worker(Date date_birth_worker) {
        Date_birth_worker = date_birth_worker;
    }

    public String getJob_worker() {
        return Job_worker;
    }

    public void setJob_worker(String job_worker) {
        Job_worker = job_worker;
    }
}
