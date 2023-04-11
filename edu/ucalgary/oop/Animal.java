package edu.ucalgary.oop;

public class Animal {
    private String animalNickname;
    private AnimalSpecies animalSpecies;
    private int animalID;

    // Constructor
    public Animal(int AnimalID, String AnimalNickname, String AnimalSpecies) {
        this.animalNickname = AnimalNickname;
        this.animalSpecies = new AnimalSpecies(AnimalSpecies);
        this.animalID = AnimalID;
    }

    // Getters and setters
    public void setAnimalID(int id) {
        this.animalID = id;
    }

    public void setAnimalNickname(String name) {
        this.animalNickname = name;
    }

    public void setAnimalSpecies(String species) {
        this.animalSpecies = new AnimalSpecies(species);
    }

    public int getAnimalID() {
        return this.animalID;
    }

    public String getAnimalNickname() {
        return this.animalNickname;
    }

    public AnimalSpecies getAnimalSpecies() {
        return this.animalSpecies;
    }
}