package com.revature.BootPie.models;

public class Pie {
    private String pieName;
    private int calories;
    private int slicesAvailable;

    public Pie() {
        // Default constructor
    }

    public Pie(String pieName, int calories, int slicesAvailable) {
        this.pieName = pieName;
        this.calories = calories;
        this.slicesAvailable = slicesAvailable;
    }

    public String getPieName() {
        return pieName;
    }
    
    public void setPieName(String pieName) {
        this.pieName = pieName;
    }
    
    public int getCalories() {
        return calories;
    }
    
    public void setCalories(int calories) {
        this.calories = calories;
    }
    
    public int getSlicesAvailable() {
        return slicesAvailable;
    }
    
    public void setSlicesAvailable(int slicesAvailable) {
        this.slicesAvailable = slicesAvailable;
    }

    @Override
    public String toString() {
        return "PieService{" +
                "pieName='" + pieName + '\'' +
                ", calories=" + calories +
                ", slicesAvailable=" + slicesAvailable +
                '}';
    }
    
}
