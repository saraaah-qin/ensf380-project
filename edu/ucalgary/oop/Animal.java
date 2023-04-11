/**
 * @author Sarah Qin
 * @author David Rodriguez Barrios
 * @version 5.0
 * @since 1.0
 */

/**
 * This class is used to create an animal object. It contains the animal's ID, nickname, and species.
 * It also contains the methods to set and get the animal's ID, nickname, and species.
 */

package edu.ucalgary.oop;

public class Animal {
    private String animalNickname;
    private AnimalSpecies animalSpecies;
    private int animalID;

    /**
     * This is the constructor for the Animal class.
     * @param AnimalID The animal's ID.
     * @param AnimalNickname The animal's nickname.
     * @param AnimalSpecies The animal's species.
     * @return Animal object.
     */   

    public Animal(int AnimalID, String AnimalNickname, String AnimalSpecies) {
        this.animalNickname = AnimalNickname;
        this.animalSpecies = new AnimalSpecies(AnimalSpecies);
        this.animalID = AnimalID;}
       
    /**
     * This method is used to set the animal's ID.
     * @param id
     * @return void
     */
    public void setAnimalID(int id) {
        this.animalID = id;
    }
    /**
     * This method is used to set the animal's nickname.
     * @param name
     * @return void
     */
    public void setAnimalNickname(String name) {
        this.animalNickname = name;
    }
    /**
     * This method is used to set the animal's species.
     * @param species
     * @return void
     */
    public void setAnimalSpecies(String species) {
        this.animalSpecies = new AnimalSpecies(species);
    }
    /**
     * This method is used to get the animal's ID.
     * @param void
     * @return int
     */
    public int getAnimalID() {
        return this.animalID;
    }
    /**
     * This method is used to get the animal's nickname.
     * @param void
     * @return String
     */
    public String getAnimalNickname() {
        return this.animalNickname;
    }
    /**
     * This method is used to get the animal's species.
     * @param void
     * @return AnimalSpecies
     */
    public AnimalSpecies getAnimalSpecies() {
        return this.animalSpecies;
    }
}