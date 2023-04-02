package edu.ucalgary.oop;

public class AnimalTask extends Task {
    private Animal animal;
    private String description;

    // Constructor
    public AnimalTask(Animal animal, String description, int startHour, int taskID, int maxWindow) {
        super(startHour, taskID, maxWindow);
        setAnimal(animal);
        setDescription(description);
    }

    // Getters and setters
    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Animal getAnimal() {
        return this.animal;
    }

    public String getDescription() {
        return this.description;
    }

}
