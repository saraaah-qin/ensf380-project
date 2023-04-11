package edu.ucalgary.oop;

public class AnimalSpecies {
    private int prepTime;
    private int cleanTime;
    private AnimalType animalType;
    private final int feedingTime = 5;
    private String animalSpeciesString;

    // Constructor
    public AnimalSpecies(String AnimalSpecies) {
        if (AnimalSpecies.equals("coyote")) {
            this.prepTime = 10;
            this.cleanTime = 5;
            this.animalType = new AnimalType("crepuscular");

            this.animalSpeciesString = AnimalSpecies;
        } else if (AnimalSpecies.equals("porcupine")) {
            this.prepTime = 0;
            this.cleanTime = 10;

            this.animalType = new AnimalType("crepuscular");
            this.animalSpeciesString = AnimalSpecies;

        } else if (AnimalSpecies.equals("fox")) {
            this.prepTime = 5;
            this.cleanTime = 5;

            this.animalType = new AnimalType("nocturnal");
            this.animalSpeciesString = AnimalSpecies;

        }

        else if (AnimalSpecies.equals("raccoon")) {
            this.prepTime = 0;
            this.cleanTime = 5;

            this.animalType = new AnimalType("nocturnal");
            this.animalSpeciesString = AnimalSpecies;

        } else if (AnimalSpecies.equals("beaver")) {
            this.prepTime = 0;
            this.cleanTime = 5;

            this.animalType = new AnimalType("diurnal");
            this.animalSpeciesString = AnimalSpecies;
        } else {
            throw new IllegalArgumentException("Invalid Animal Species");
        }
    }

    // Getters and setters
    public int getCleanTime() {
        return cleanTime;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public int getFeedingTime() {
        return feedingTime;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public String getAnimalSpeciesString() {
        return animalSpeciesString;
    }
}
