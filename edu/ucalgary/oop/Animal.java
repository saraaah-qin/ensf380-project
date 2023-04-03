package edu.ucalgary.oop;

public class Animal {
    private String animalNickname;
    private String animalSpecies;
    private int animalID;

    public Animal(int AnimalID, String AnimalNickname, String AnimalSpecies) {
        this.animalNickname = AnimalNickname;
        this.animalSpecies = AnimalSpecies;
        this.animalID = AnimalID;
    }

    public void setAnimalID(int id) {
        this.animalID = id;
    }

    public void setAnimalNickname(String name) {
        this.animalNickname = name;
    }

    public void setAnimalSpecies(String species) {
        this.animalSpecies = species;
    }

    public int getAnimalID() {
        return this.animalID;
    }

    public String getAnimalNickname() {
        return this.animalNickname;
    }

    public String getAnimalSpecies() {
        return this.animalSpecies;
    }

}