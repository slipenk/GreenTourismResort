package sample.db_classes;

import java.sql.Time;

public class Entertainments {
    private int ID_Entertainment;
    private String Name_entertainment;
    private byte Max_People_entertainment;
    private Time Time_start_entertainment;
    private Time Time_end_entertainment;
    private int Rate_entertainment;
    private float Price_entertainment;
    private byte Duration;

    public Entertainments(int ID_Entertainment, String name_entertainment, byte max_People_entertainment, Time time_start_entertainment, Time time_end_entertainment, int rate_entertainment, float price_entertainment, byte duration) {
        this.ID_Entertainment = ID_Entertainment;
        Name_entertainment = name_entertainment;
        Max_People_entertainment = max_People_entertainment;
        Time_start_entertainment = time_start_entertainment;
        Time_end_entertainment = time_end_entertainment;
        Rate_entertainment = rate_entertainment;
        Price_entertainment = price_entertainment;
        Duration = duration;
    }

    public int getID_Entertainment() {
        return ID_Entertainment;
    }

    public void setID_Entertainment(int ID_Entertainment) {
        this.ID_Entertainment = ID_Entertainment;
    }

    public String getName_entertainment() {
        return Name_entertainment;
    }

    public void setName_entertainment(String name_entertainment) {
        Name_entertainment = name_entertainment;
    }

    public byte getMax_People_entertainment() {
        return Max_People_entertainment;
    }

    public void setMax_People_entertainment(byte max_People_entertainment) {
        Max_People_entertainment = max_People_entertainment;
    }

    public Time getTime_start_entertainment() {
        return Time_start_entertainment;
    }

    public void setTime_start_entertainment(Time time_start_entertainment) {
        Time_start_entertainment = time_start_entertainment;
    }

    public Time getTime_end_entertainment() {
        return Time_end_entertainment;
    }

    public void setTime_end_entertainment(Time time_end_entertainment) {
        Time_end_entertainment = time_end_entertainment;
    }

    public int getRate_entertainment() {
        return Rate_entertainment;
    }

    public void setRate_entertainment(int rate_entertainment) {
        Rate_entertainment = rate_entertainment;
    }

    public float getPrice_entertainment() {
        return Price_entertainment;
    }

    public void setPrice_entertainment(float price_entertainment) {
        Price_entertainment = price_entertainment;
    }

    public byte getDuration() {
        return Duration;
    }

    public void setDuration(byte duration) {
        Duration = duration;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Entertainments))
            return false;
        if (obj == this)
            return true;
        return this.getID_Entertainment() == ((Entertainments) obj).getID_Entertainment();
    }

    @Override
    public int hashCode() {
        return ID_Entertainment;
    }
}


