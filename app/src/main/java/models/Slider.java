package models;

public class Slider {
    private String path;

    // Default constructor required for calls to DataSnapshot.getValue(Slider.class)
    public Slider() { }

    public Slider(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}