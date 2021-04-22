package sample.db_classes;

public class Categories {
    private int ID_category;
    private String Name_category;

    public Categories(int ID_category, String name_category) {
        this.ID_category = ID_category;
        Name_category = name_category;
    }

    public int getID_category() {
        return ID_category;
    }

    public void setID_category(int ID_category) {
        this.ID_category = ID_category;
    }

    public String getName_category() {
        return Name_category;
    }

    public void setName_category(String name_category) {
        Name_category = name_category;
    }
}


