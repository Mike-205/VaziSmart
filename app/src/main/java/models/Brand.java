package models;

public class Brand {
    private String path;
    private String name;

    //Default Constructor
    public Brand() {}

    public Brand(String path, String name) {this.path = path; this.name = name;}

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }
}