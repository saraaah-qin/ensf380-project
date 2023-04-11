package edu.ucalgary.oop;

public class AnimalSpecies {
private int prepTime;
private int cleanTime;
private AnimalType animalType;
private int feedingTime;
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

    AnimalSpecies(String AnimalSpecies){
        if(AnimalSpecies.equals("coyote")){
            this.prepTime=10;
            this.cleanTime=5;
            this.animalType= new AnimalType("crepuscular");
            this.feedingTime=5;
            this.animalSpeciesString=AnimalSpecies;
        }
        else if(AnimalSpecies.equals("porcupine")){
            this.prepTime=0;
            this.cleanTime=10;
            this.feedingTime=5;
            this.animalType=new AnimalType("crepuscular");
            this.animalSpeciesString=AnimalSpecies;

        }
        else if(AnimalSpecies.equals("fox")){
            this.prepTime=5;
            this.cleanTime=5;
            this.feedingTime=5;
            this.animalType=new AnimalType("nocturnal");
            this.animalSpeciesString=AnimalSpecies;

        }


        else if(AnimalSpecies.equals("raccoon")){
            this.prepTime=0;
            this.cleanTime=5;
            this.feedingTime=5;
            this.animalType=new AnimalType("nocturnal");
            this.animalSpeciesString=AnimalSpecies;

        }
        else if(AnimalSpecies.equals("beaver")){
            this.prepTime=0;
            this.cleanTime=5;
            this.feedingTime=5;
            this.animalType=new AnimalType("diurnal");
            this.animalSpeciesString=AnimalSpecies;
        }
        else{ throw new IllegalArgumentException("Invalid Animal Species");}
    }
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
