package edu.ucalgary.oop;

public class AnimalType {
    private int[] feedingTimes;

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
}
