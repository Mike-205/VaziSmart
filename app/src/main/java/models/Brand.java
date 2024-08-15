package models;

public class Brand {
    private String picUrl;
    private String name;

    //Default Constructor
    public Brand() {}

    public Brand(String picUrl, String name) {this.picUrl = picUrl; this.name = name;}

    public String getPicUrl() {
        return picUrl;
    }

    public String getName() {
        return name;
    }
}