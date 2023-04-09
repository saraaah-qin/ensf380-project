package edu.ucalgary.oop;

public class AnimalSpecies {
private int prepTime;
private int cleanTime;
private AnimalType animalType;

private int feedingTime;

    AnimalSpecies(String AnimalSpecies){
        if(AnimalSpecies.equals("coyote")){
            this.prepTime=10;
            this.cleanTime=5;
            this.animalType= new AnimalType("crepuscular");
            this.feedingTime=5;
        }
        else if(AnimalSpecies.equals("porcupine")){
            this.prepTime=0;
            this.cleanTime=10;
            this.feedingTime=5;
            this.animalType=new AnimalType("crepuscular");

        }
        else if(AnimalSpecies.equals("fox")){
            this.prepTime=5;
            this.cleanTime=5;
            this.feedingTime=5;
            this.animalType=new AnimalType("nocturnal");

        }


        else if(AnimalSpecies.equals("raccoon")){
            this.prepTime=0;
            this.cleanTime=5;
            this.feedingTime=5;
            this.animalType=new AnimalType("nocturnal");

        }
        else if(AnimalSpecies.equals("beaver")){
            this.prepTime=0;
            this.cleanTime=5;
            this.feedingTime=5;
            this.animalType=new AnimalType("diurnal");
        }
        else{ throw new IllegalArgumentException("Invalid Animal Species");}
    }
}
