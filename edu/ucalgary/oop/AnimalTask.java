package edu.ucalgary.oop;

import java.time.LocalTime;

public class AnimalTask extends Task {
    private Animal animal;
    private String description;
    private int duration;
    private LocalTime startTime;
    private LocalTime endTime;
    private int treatmentID;

    // Constructor
    public AnimalTask(Animal animal, String description, int startHour, int taskID, int maxWindow, int duration) {
        super(taskID, startHour, maxWindow);
        setAnimal(animal);
        setDescription(description);
        setDuration(duration);
    }

    // Getters and setters
    public void setAnimal(Animal animal) {
        this.animal = animal;

    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDuration(int duration) {
        if (duration < 0 || duration > 60)
            throw new IllegalArgumentException("Duration must be greater than 0 and less than 60");
        this.duration = duration;
    }

    public void setTreatmentID(int treatmentID) {
        this.treatmentID = treatmentID;
    }

    public int getTreatmentID() {
        return this.treatmentID;
    }

    public Animal getAnimal() {
        return this.animal;
    }

    public String getDescription() {
        return this.description;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getMaxWindow() {
        return super.getMaxWindow();
    }

    public void setStartTime(LocalTime startTime) {

        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public LocalTime getEndTime() {
        return this.endTime;
    }

    public int getTaskID() {
        return super.getTaskID();
    }

}
