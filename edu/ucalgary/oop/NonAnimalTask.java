package edu.ucalgary.oop;

public class NonAnimalTask extends Task {
    private int cage;
    private int feeding;

    // Constructor
    public NonAnimalTask(int cage, int feeding, int startHour, int taskID, int maxWindow) {
        super(startHour, taskID, maxWindow);
        this.cage = cage;
        this.feeding = feeding;
    }

    // Getters and setters
    public void setCage(int cage) {
        this.cage = cage;
    }

    public void setFeeding(int feeding) {
        this.feeding = feeding;
    }

    public int getCage() {
        return this.cage;
    }

    public int getFeeding() {
        return this.feeding;
    }

}
