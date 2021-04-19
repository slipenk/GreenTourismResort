package sample.db_classes;

public class Resources {
    private int ID_Resources;
    private String Name_Resources;
    private short Count_resources;
    private float Price_one;
    private String Entertainment;

    public Resources(int ID_Resources, String name_Resources, short count_resources, float price_one, String Entertainment) {
        this.ID_Resources = ID_Resources;
        Name_Resources = name_Resources;
        Count_resources = count_resources;
        Price_one = price_one;
        this.Entertainment = Entertainment;
    }

    public int getID_Resources() {
        return ID_Resources;
    }

    public void setID_Resources(int ID_Resources) {
        this.ID_Resources = ID_Resources;
    }

    public String getName_Resources() {
        return Name_Resources;
    }

    public void setName_Resources(String name_Resources) {
        Name_Resources = name_Resources;
    }

    public short getCount_resources() {
        return Count_resources;
    }

    public void setCount_resources(short count_resources) {
        Count_resources = count_resources;
    }

    public float getPrice_one() {
        return Price_one;
    }

    public void setPrice_one(float price_one) {
        Price_one = price_one;
    }

    public String getEntertainment() {
        return Entertainment;
    }

    public void setEntertainment(String ID_entertainment) {
        this.Entertainment = ID_entertainment;
    }
}
