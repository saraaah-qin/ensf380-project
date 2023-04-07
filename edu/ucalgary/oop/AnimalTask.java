package edu.ucalgary.oop;

import java.time.LocalTime;

public class AnimalTask extends Task {
    private Animal animal;
    private String description;
    private int duration;
    public LocalTime startTime;
    public LocalTime endTime;



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
        this.duration = duration;
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

}
