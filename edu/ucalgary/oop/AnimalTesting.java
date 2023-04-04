package edu.ucalgary.oop;

import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.Assert;

// import org.junit.jupiter.api.Test;


public class AnimalTesting {
    

    // Testing Animal Class
        @Test
        public void testAnimalInitialization() {
            // Tests 3 Argument Constructor
            Animal animal = new Animal(1, "Fluffy", "Dog");
    
            // Tests getters
            Assert.assertEquals(1, animal.getAnimalID());
            Assert.assertEquals("Fluffy", animal.getAnimalNickname());
            Assert.assertEquals("Dog", animal.getAnimalSpecies());
        }

    
        // Testing Animal Setters
        @Test
        public void testAnimalSetters() {
            Animal animal = new Animal(1, "Fluffy", "Dog");
    
            animal.setAnimalID(2);
            animal.setAnimalNickname("Buddy");
            animal.setAnimalSpecies("Cat");
    
            Assert.assertEquals(2, animal.getAnimalID());
            Assert.assertEquals("Buddy", animal.getAnimalNickname());
            Assert.assertEquals("Cat", animal.getAnimalSpecies());
        }

        // let me know if we need to create test for exceptions







        // Testing Task Class

        // Tests 3 Argument Constructor and getters
    @Test
    public void testTaskInitialization() {
        Task task = new Task(9, 1, 2);

        Assert.assertEquals(9, task.getStartHour());
        Assert.assertEquals(1, task.getTaskID());
        Assert.assertEquals(2, task.getMaxWindow());
    }
// tests 3 argument constructor and setters and getters
    @Test
    public void testTaskSetters() {
        Task task = new Task(9, 1, 2);

        task.setStartHour(10);
        task.setTaskID(2);
        task.setMaxWindow(3);

        Assert.assertEquals(10, task.getStartHour());
        Assert.assertEquals(2, task.getTaskID());
        Assert.assertEquals(3, task.getMaxWindow());
    }


    // let me know if we need to do testing for exceptions or argument checks etc.









    // Testing AnimalTask Class

    @Test
    public void testAnimalTaskInitialization() {
        // tests 3 argument constructor and getters for animal, then 4 argument constructor for AnimalTask
        Animal animal = new Animal(1, "Fluffy", "Dog");
        AnimalTask animalTask = new AnimalTask(animal, "Walk Fluffy", 9, 1, 2);

        // tests getters for AnimalTask
        Assert.assertEquals(animal, animalTask.getAnimal());
        Assert.assertEquals("Walk Fluffy", animalTask.getDescription());
        Assert.assertEquals(9, animalTask.getStartHour());
        Assert.assertEquals(1, animalTask.getTaskID());
        Assert.assertEquals(2, animalTask.getMaxWindow());
    }

    @Test
    public void testAnimalTaskSetters() {
        // tests 3 argument constructor and getters for animal, then 4 argument constructor for AnimalTask and setters
        Animal animal = new Animal(1, "Fluffy", "Dog");
        AnimalTask animalTask = new AnimalTask(animal, "Walk Fluffy", 9, 1, 2);

        
        Animal newAnimal = new Animal(2, "Buddy", "Cat");
        // tests setters for AnimalTask
        animalTask.setAnimal(newAnimal);
        animalTask.setDescription("Feed Buddy");
        animalTask.setStartHour(10);
        animalTask.setTaskID(2);
        animalTask.setMaxWindow(3);

        Assert.assertEquals(newAnimal, animalTask.getAnimal());
        Assert.assertEquals("Feed Buddy", animalTask.getDescription());
        Assert.assertEquals(10, animalTask.getStartHour());
        Assert.assertEquals(2, animalTask.getTaskID());
        Assert.assertEquals(3, animalTask.getMaxWindow());
    }


    // let me know if we need to do testing for exceptions or argument checks etc.


    
    
    // Testing NonAnimalTask Class

        @Test
        public void testNonAnimalTaskInitialization() {
            // tests 5 argument constructor and getters for nonanimaltask
            NonAnimalTask nonAnimalTask = new NonAnimalTask(2, 3, 8, 1, 4);
    
            Assert.assertEquals(2, nonAnimalTask.getCage());
            Assert.assertEquals(3, nonAnimalTask.getFeeding());
            Assert.assertEquals(8, nonAnimalTask.getStartHour());
            Assert.assertEquals(1, nonAnimalTask.getTaskID());
            Assert.assertEquals(4, nonAnimalTask.getMaxWindow());
        }
    
        @Test
        public void testNonAnimalTaskSetters() {
            // tests 5 argument constructor and getters for nonanimaltask and setters then getters
            NonAnimalTask nonAnimalTask = new NonAnimalTask(2, 3, 8, 1, 4);
    
            nonAnimalTask.setCage(3);
            nonAnimalTask.setFeeding(4);
            nonAnimalTask.setStartHour(9);
            nonAnimalTask.setTaskID(2);
            nonAnimalTask.setMaxWindow(5);
    
            Assert.assertEquals(3, nonAnimalTask.getCage());
            Assert.assertEquals(4, nonAnimalTask.getFeeding());
            Assert.assertEquals(9, nonAnimalTask.getStartHour());
            Assert.assertEquals(2, nonAnimalTask.getTaskID());
            Assert.assertEquals(5, nonAnimalTask.getMaxWindow());
        }

        // let me know if we need to do testing for exceptions or argument checks etc.



        // Testing Volunteer Class

    @Test
    public void testVolunteerInitialization() {
        // tests 2 argument constructor and getters for volunteer
        Volunteer volunteer = new Volunteer("John Doe", 10);

        Assert.assertEquals("John Doe", volunteer.getName());
        Assert.assertEquals(10, volunteer.getAvailability());
    }

    @Test
    public void testVolunteerSetters() {
        // tests 2 argument constructor and getters for volunteer and setters then getters
        Volunteer volunteer = new Volunteer("John Doe", 10);

        //no setters?
        // volunteer.setName("Jane Doe");
        // volunteer.setAvailability(5);

        Assert.assertEquals("Jane Doe", volunteer.getName());
        Assert.assertEquals(5, volunteer.getAvailability());
    }
}


    
    





    
    

