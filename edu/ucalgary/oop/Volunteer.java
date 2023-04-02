package edu.ucalgary.oop;

public class Volunteer {
    private String name;
    private int availability;

    // Constructor
    public Volunteer(String name, int availability) {
        this.name = name;
        this.availability = availability;
    }

    // Getters
    public String getName() {
        return this.name;
    }

    public int getAvailability() {
        return this.availability;
    }

}
