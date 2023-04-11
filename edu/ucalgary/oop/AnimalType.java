/**
 * @author David Rodriguez Barrios
 * @version 2.0
 * @since 1.0
 */
 
/**
 * This class is used to create an AnimalType object that contains the feeding times of the animal
 */

package edu.ucalgary.oop;

public class AnimalType {
    private int[] feedingTimes;
    /**
    * This is the constructor for the AnimalType class.
    * @param animalType
    * @return AnimalType object
    */
    AnimalType (String animalType){
        if(animalType.equals("crepuscular")){
            feedingTimes= new int[]{19,20,21};
        }
        else if(animalType.equals("diurnal")){
            feedingTimes= new int[]{8,9,10};
        }
        else if(animalType.equals("nocturnal")){
            feedingTimes= new int[]{0,1,2};
        }
    }
    /**
     * This method is used to get the feeding times of the animal.
     * @return int[]
     */
    public int[] getFeedingTimes() {
        return feedingTimes;
    }
}
