package sample.db_classes;

public class Homesteads {
    private int ID_homestead;
    private String Name_homestead;
    private byte Number_of_beds_homestead;
    private byte Number_of_rooms_homestead;
    private byte Number_of_floors_homestead;
    private boolean Is_Air_Conditioning;
    private boolean Is_Safe;
    private boolean Is_Wi_Fi;
    private boolean Is_Refrigerator;
    private boolean Is_Clothes_Iron;
    private boolean Is_Hair_Dryer;
    private int Rate_homestead;
    private float Price_homestead;
    private boolean Is_Active;


    public Homesteads(int ID_homestead, String name_homestead, byte number_of_beds_homestead, byte number_of_rooms_homestead, byte number_of_floors_homestead, boolean is_Air_Conditioning, boolean is_Safe, boolean Is_Wi_Fi, boolean is_Refrigerator, boolean is_Clothes_Iron, boolean is_Hair_Dryer, int rate_homestead, float price_homestead, boolean is_Active) {
        this.ID_homestead = ID_homestead;
        Name_homestead = name_homestead;
        Number_of_beds_homestead = number_of_beds_homestead;
        Number_of_rooms_homestead = number_of_rooms_homestead;
        Number_of_floors_homestead = number_of_floors_homestead;
        Is_Air_Conditioning = is_Air_Conditioning;
        Is_Safe = is_Safe;
        this.Is_Wi_Fi = Is_Wi_Fi;
        Is_Refrigerator = is_Refrigerator;
        Is_Clothes_Iron = is_Clothes_Iron;
        Is_Hair_Dryer = is_Hair_Dryer;
        Rate_homestead = rate_homestead;
        Price_homestead = price_homestead;
        Is_Active = is_Active;
    }

    public int getID_homestead() {
        return ID_homestead;
    }

    public void setID_homestead(int ID_homestead) {
        this.ID_homestead = ID_homestead;
    }

    public String getName_homestead() {
        return Name_homestead;
    }

    public void setName_homestead(String name_homestead) {
        Name_homestead = name_homestead;
    }

    public byte getNumber_of_beds_homestead() {
        return Number_of_beds_homestead;
    }

    public void setNumber_of_beds_homestead(byte number_of_beds_homestead) {
        Number_of_beds_homestead = number_of_beds_homestead;
    }

    public byte getNumber_of_rooms_homestead() {
        return Number_of_rooms_homestead;
    }

    public void setNumber_of_rooms_homestead(byte number_of_rooms_homestead) {
        Number_of_rooms_homestead = number_of_rooms_homestead;
    }

    public byte getNumber_of_floors_homestead() {
        return Number_of_floors_homestead;
    }

    public void setNumber_of_floors_homestead(byte number_of_floors_homestead) {
        Number_of_floors_homestead = number_of_floors_homestead;
    }

    public boolean isIs_Air_Conditioning() {
        return Is_Air_Conditioning;
    }

    public void setIs_Air_Conditioning(boolean is_Air_Conditioning) {
        Is_Air_Conditioning = is_Air_Conditioning;
    }

    public boolean isIs_Safe() {
        return Is_Safe;
    }

    public void setIs_Safe(boolean is_Safe) {
        Is_Safe = is_Safe;
    }

    public boolean isIs_Wi_Fi() {
        return Is_Wi_Fi;
    }

    public void setIs_Wi_Fi(boolean is_TV) {
        Is_Wi_Fi = is_TV;
    }

    public boolean isIs_Refrigerator() {
        return Is_Refrigerator;
    }

    public void setIs_Refrigerator(boolean is_Refrigerator) {
        Is_Refrigerator = is_Refrigerator;
    }

    public boolean isIs_Clothes_Iron() {
        return Is_Clothes_Iron;
    }

    public void setIs_Clothes_Iron(boolean is_Clothes_Iron) {
        Is_Clothes_Iron = is_Clothes_Iron;
    }

    public boolean isIs_Hair_Dryer() {
        return Is_Hair_Dryer;
    }

    public void setIs_Hair_Dryer(boolean is_Hair_Dryer) {
        Is_Hair_Dryer = is_Hair_Dryer;
    }

    public int getRate_homestead() {
        return Rate_homestead;
    }

    public void setRate_homestead(int rate_homestead) {
        Rate_homestead = rate_homestead;
    }

    public float getPrice_homestead() {
        return Price_homestead;
    }

    public void setPrice_homestead(float price_homestead) {
        Price_homestead = price_homestead;
    }

    public boolean isIs_Active() {
        return Is_Active;
    }

    public void setIs_Active(boolean is_Active) {
        Is_Active = is_Active;
    }
}
