/**
 * @author David Rodriguez Barrios
 * @version 4.0
 * @since 1.0
 */
/**
 * This class is used to create an AnimalSpecies object. 
 * It contains the animal's prep time, clean time, animal type, feeding time, and animal species string.
 */

package edu.ucalgary.oop;

public class AnimalSpecies {
    private int prepTime;
    private int cleanTime;
    private AnimalType animalType;
    private final int feedingTime = 5;
    private String animalSpeciesString;

    /*
     * Cages
     * Orphaned animals of the same litter share a cage and
     * are listed as a single animal in the database.
     * Coyote - 5 min/cage
     * Porcupines - 10 min/cage
     * Foxes - 5 min/cage
     * Raccoons - 5 min/cage
     * Beavers - 5 min/cage
     * Feeding
     * Noturnal animals fed in 3 hr window starting at midnight
     * - Foxes(5min + 5min prep /each)
     * - Beavers(5min/each)
     * Diurnal animals fed in 3 hr window starting at 8 AM
     * - Raccoons(5min/each)
     * Crepuscular animals fed in 3 hr window starting at 7 PM
     * - Coyotes(5min + 10min prep /each)
     * - Porcupines(5min/each)
     */

    /**
     * This is the constructor for the AnimalSpecies class.
     * 
     * @param AnimalSpecies The animal's species.
     * @return AnimalSpecies object.
     */
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

    /**
     * This method is used to get the animal's clean time.
     * 
     * @param void
     * @return int
     */

    public int getCleanTime() {
        return cleanTime;
    }

    /**
     * This method is used to get the animal's prep time.
     * 
     * @param void
     * @return int
     */
    public int getPrepTime() {
        return prepTime;
    }

    /**
     * This method is used to get the animal's feeding time.
     * 
     * @param void
     * @return int
     */
    public int getFeedingTime() {
        return feedingTime;
    }

    /**
     * This method is used to get the animal's type.
     * 
     * @param void
     * @return AnimalType
     */
    public AnimalType getAnimalType() {
        return animalType;
    }

    /**
     * This method is used to get the animal's species string.
     * 
     * @param void
     * @return String
     */
    public String getAnimalSpeciesString() {
        return animalSpeciesString;
    }
}
