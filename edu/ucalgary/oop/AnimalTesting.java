/* @author Sarah Qin
 * @author David Rodriguez Barrios
 * @version 3.0
 * @since 1.0
 */
/**
 * This class is used to test all teh classes.
 */

package edu.ucalgary.oop;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;

public class AnimalTesting {

    /*
     * Testing Animal Class
     */

    @Test

    public void testAnimalInitialization() {
        /*
         * Tests 3 Argument Constructor
         */
        Animal animal = new Animal(1, "Fluffy", "fox");

        /*
         * Tests Getters
         */
        Assert.assertEquals(1, animal.getAnimalID());
        Assert.assertEquals("Fluffy", animal.getAnimalNickname());
        Assert.assertEquals("fox", animal.getAnimalSpecies().getAnimalSpeciesString());
    }

    @Test

    public void testAnimalInitializationIllegalArg() {
        /*
         * Tests 3 Argument Constructor
         */
        try {
            Animal animal = new Animal(1, "Fluffy", "dog");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Invalid Animal Species", e.getMessage());
        }

    }

    /*
     * Tests Animal Setters
     */
    @Test
    public void testAnimalSetters() {
        Animal animal = new Animal(1, "Fluffy", "raccoon");

        animal.setAnimalID(2);
        animal.setAnimalNickname("Buddy");
        animal.setAnimalSpecies("fox");

        Assert.assertEquals(2, animal.getAnimalID());
        Assert.assertEquals("Buddy", animal.getAnimalNickname());
        Assert.assertEquals("fox", animal.getAnimalSpecies().getAnimalSpeciesString());
    }

    /*
     * This test case checks if the animal's name can be set to an empty string
     */
    @Test
    public void testAnimalSetNameEmpty() {
        Animal animal = new Animal(3, "Severus", "fox");
        try {
            animal.setAnimalSpecies("");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Invalid Animal Species", e.getMessage());
        }
        Assert.assertEquals("fox", animal.getAnimalSpecies().getAnimalSpeciesString());
    }

    /* Testing Task Class */

    /*
     * Tests 3 Argument Constructor and getters
     */
    @Test
    public void testTaskInitialization() {
        Task task = new Task(9, 1, 2);

        Assert.assertEquals(1, task.getStartHour());
        Assert.assertEquals(9, task.getTaskID());
        Assert.assertEquals(2, task.getMaxWindow());
    }

    /*
     * Tests 3 Argument Constructor and Setters
     */
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

    /*
     * Tests for legal arguments for setters
     */
    @Test
    public void testTaskSettersIllegal() {
        Task task = new Task(9, 1, 2);

        task.setStartHour(10);
        task.setTaskID(2);
        try {
            task.setMaxWindow(-10);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Max window must be between 0 and 24", e.getMessage());
        }
        Assert.assertEquals(10, task.getStartHour());
        Assert.assertEquals(2, task.getTaskID());
    }

    /*
     * Tests for legal arguments for starthour
     */
    @Test
    public void testTaskSettersIllegalStarHour() {
        Task task = new Task(9, 1, 2);

        try {
            task.setStartHour(-11);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Start hour must be between 0 and 23", e.getMessage());
        }
        task.setTaskID(2);

        Assert.assertEquals(1, task.getStartHour());
        Assert.assertEquals(2, task.getTaskID());

    }

    /*
     * Testing AnimalTask Class
     */

    /*
     * tests 3 argument constructor and getters for animal, then 6 argument
     * constructor for AnimalTask
     */
    @Test
    public void testAnimalTaskInitialization() {

        Animal animal = new Animal(1, "Fluffy", "fox");
        AnimalTask animalTask = new AnimalTask(animal, "Walk Fluffy", 9, 20, 1, 2);

        Assert.assertEquals(animal, animalTask.getAnimal());
        Assert.assertEquals("Walk Fluffy", animalTask.getDescription());
        Assert.assertEquals(9, animalTask.getStartHour());
        Assert.assertEquals(20, animalTask.getTaskID());
        Assert.assertEquals(1, animalTask.getMaxWindow());
        Assert.assertEquals(2, animalTask.getDuration());
    }

    /*
     * tests 3 argument constructor and getters for animal, then 6 argument
     * constructor for AnimalTask and setters
     */
    @Test
    public void testAnimalTaskSetters() {

        Animal animal = new Animal(1, "Fluffy", "fox");
        AnimalTask animalTask = new AnimalTask(animal, "Walk Fluffy", 9, 20, 1, 2);

        Animal newAnimal = new Animal(2, "Buddy", "raccoon");
        // tests setters for AnimalTask
        animalTask.setAnimal(newAnimal);
        animalTask.setDescription("Feed Buddy");
        animalTask.setStartHour(10);
        animalTask.setTaskID(2);
        animalTask.setMaxWindow(3);
        animalTask.setDuration(9);

        Assert.assertEquals(newAnimal, animalTask.getAnimal());
        Assert.assertEquals("Feed Buddy", animalTask.getDescription());
        Assert.assertEquals(10, animalTask.getStartHour());
        Assert.assertEquals(2, animalTask.getTaskID());
        Assert.assertEquals(3, animalTask.getMaxWindow());
        Assert.assertEquals(9, animalTask.getDuration());
    }

    /*
     * Tests for schedule generator method
     */

    @Test
    public void testScheduleGeneration() {
        // Create a mock database connection

        Schedule schedule = new Schedule();

        // Set up test data

        // Call the generateSchedule() method
        schedule.generateSchedule();

        // Assert that the scheduleAnimalTasks and scheduleAnimalTasks2 lists are not
        // empty
        assertFalse(schedule.getScheduleAnimalTasks().isEmpty());
        assertTrue(schedule.getAnimalTasks().isEmpty());

        // Assert that the animal tasks are scheduled correctly

    }

    /*
     * Testing Schedule Class
     */

    @Test
    public void testGenerateSchedule() {

        Schedule schedule = new Schedule();
        ArrayList<AnimalTask> animalTasksTest = new ArrayList<>();
        for (AnimalTask animalTask : schedule.getScheduleAnimalTasks()) {
            animalTasksTest.add(animalTask);
        }
        schedule.setAnimalTasks(animalTasksTest);

        assertEquals(schedule.getAnimalTasks(), animalTasksTest);
        // Assert that the animalTasks list is not empty
        assertFalse(schedule.getAnimalTasks().isEmpty());

        schedule.sortAnimalTasks();
        // Assert that the animal tasks are sorted by max window
        List<AnimalTask> animalTasks = schedule.getAnimalTasks();
        for (int i = 0; i < animalTasks.size() - 1; i++) {
            assertTrue(animalTasks.get(i).getMaxWindow() <= animalTasks.get(i + 1).getMaxWindow());
        }
        // checks to see if the animal tasks are ordered correctly
    }

    /*
     * Testing AnimalType Class
     */

    /*
     * Tests Crepuscular getFeedingTimes method
     */
    @Test
    public void testGetFeedingTimesCrepuscular() {
        AnimalType animal = new AnimalType("crepuscular");
        int[] expectedFeedingTimes = { 19, 20, 21 };
        assertArrayEquals(expectedFeedingTimes, animal.getFeedingTimes());

    }

    /*
     * Tests Diurnal getFeedingTimes method
     */

    @Test
    public void testGetFeedingTimesDiurnal() {
        AnimalType animal = new AnimalType("diurnal");
        int[] expectedFeedingTimes = { 8, 9, 10 };
        assertArrayEquals(expectedFeedingTimes, animal.getFeedingTimes());
    }

    /*
     * Tests Nocturnal getFeedingTimes method
     */
    @Test
    public void testGetFeedingTimesNocturnal() {
        AnimalType animal = new AnimalType("nocturnal");
        int[] expectedFeedingTimes = { 0, 1, 2 };
        assertArrayEquals(expectedFeedingTimes, animal.getFeedingTimes());
    }

    /*
     * Test Invalid Animal Type
     */
    @Test
    public void testGetFeedingTimesInvalidAnimalType() {
        assertThrows(IllegalArgumentException.class, () -> {
            new AnimalType("unknown"); // should throw IllegalArgumentException for invalid animal type
        });
    }

    /*
     * Testing Animal Species Class
     */

    private AnimalSpecies coyote;
    private AnimalSpecies porcupine;
    private AnimalSpecies fox;
    private AnimalSpecies raccoon;
    private AnimalSpecies beaver;

    @Before
    public void setUp2() {
        coyote = new AnimalSpecies("coyote");
        porcupine = new AnimalSpecies("porcupine");
        fox = new AnimalSpecies("fox");
        raccoon = new AnimalSpecies("raccoon");
        beaver = new AnimalSpecies("beaver");
    }

    /* Tests getCleanTime method */
    @Test
    public void testGetCleanTime() {
        assertEquals(5, coyote.getCleanTime());
        assertEquals(10, porcupine.getCleanTime());
        assertEquals(5, fox.getCleanTime());
        assertEquals(5, raccoon.getCleanTime());
        assertEquals(5, beaver.getCleanTime());
    }

    /* Tests getPrepTime method */
    @Test
    public void testGetPrepTime() {
        assertEquals(10, coyote.getPrepTime());
        assertEquals(0, porcupine.getPrepTime());
        assertEquals(5, fox.getPrepTime());
        assertEquals(0, raccoon.getPrepTime());
        assertEquals(0, beaver.getPrepTime());
    }

    /* Tests getFeedingTime method */
    @Test
    public void testGetFeedingTime() {
        assertEquals(5, coyote.getFeedingTime());
        assertEquals(5, porcupine.getFeedingTime());
        assertEquals(5, fox.getFeedingTime());
        assertEquals(5, raccoon.getFeedingTime());
        assertEquals(5, beaver.getFeedingTime());
    }

    /* Tests getAnimalSpeciesString method */
    @Test
    public void testGetAnimalSpeciesString() {
        assertEquals("coyote", coyote.getAnimalSpeciesString());
        assertEquals("porcupine", porcupine.getAnimalSpeciesString());
        assertEquals("fox", fox.getAnimalSpeciesString());
    }

    /*
     * Testing Schedule Class
     */

    /*
     * Tests addAnimalTaskToSchedule method
     */
    @Test
    public void testAddAnimalTaskToSchedule() {
        Animal animal = new Animal(1, "Fluffy", "fox");
        AnimalTask task = new AnimalTask(animal, "Eyedrops", 9, 1, 2, 25);
        Schedule schedule = new Schedule();
        assertTrue(schedule.getAnimalTasks().add(task));
        assertTrue(schedule.getAnimalTasks().contains(task));
    }

    /*
     * Tests getAnimalTasks method
     */

    @Test
    public void testGetAnimalTasks() {
        // Create a sample ArrayList of AnimalTasks
        ArrayList<AnimalTask> animalTasks = new ArrayList<>();
        Animal animal = new Animal(1, "Fluffy", "fox");
        Animal animal2 = new Animal(19, "pogsa", "raccoon");
        animalTasks.add(new AnimalTask(animal, "Eyedrops", 9, 1, 2, 25));
        animalTasks.add(new AnimalTask(animal2, "Inspect broken leg", 10, 6, 2, 5));

        // Set the animalTasks using setAnimalTasks()
        Schedule zooSchedule = new Schedule();
        zooSchedule.setAnimalTasks(animalTasks);

        // Verify that getAnimalTasks() returns the same ArrayList of AnimalTasks
        assertEquals(animalTasks, zooSchedule.getAnimalTasks());
    }

    /*
     * Tests getScheduleAnimalTasks method
     */

    @Test
    public void testGetScheduleAnimalTasks() {
        // Create a sample List of AnimalTasks
        List<AnimalTask> animalTasks = new ArrayList<>();
        Animal animal = new Animal(1, "Fluffy", "fox");
        Animal animal2 = new Animal(19, "pogsa", "raccoon");
        animalTasks.add(new AnimalTask(animal, "Eyedrops", 9, 1, 2, 25));
        animalTasks.add(new AnimalTask(animal2, "Inspect broken leg", 10, 6, 2, 5));

        // Set the scheduleAnimalTasks using setScheduleAnimalTasks()
        Schedule zooSchedule = new Schedule();
        zooSchedule.setScheduleAnimalTasks(animalTasks);

        // Verify that getScheduleAnimalTasks() returns the same List of AnimalTasks
        assertEquals(animalTasks, zooSchedule.getScheduleAnimalTasks());
    }

    /*
     * Tests getScheduleAnimalTasks2 method
     */

    @Test
    public void testGetScheduleAnimalTasks2() {
        // Create a sample List of AnimalTasks
        List<AnimalTask> scheduleAnimalTasks2 = new ArrayList<>();
        Animal animal = new Animal(1, "Fluffy", "fox");
        Animal animal2 = new Animal(19, "pogsa", "raccoon");
        scheduleAnimalTasks2.add(new AnimalTask(animal, "Eyedrops", 9, 1, 2, 25));
        scheduleAnimalTasks2.add(new AnimalTask(animal2, "Inspect broken leg", 10, 6, 2, 5));

        // Set the scheduleAnimalTasks2 using setScheduleAnimalTasks2()
        Schedule zooSchedule = new Schedule();
        zooSchedule.setScheduleAnimalTasks2(scheduleAnimalTasks2);

        // Verify that getScheduleAnimalTasks2() returns the same List of AnimalTasks
        assertEquals(scheduleAnimalTasks2, zooSchedule.getScheduleAnimalTasks2());
    }

    /*
     * Tests getLeftOverTasks method
     */

    @Test
    public void testGetLeftOverTasks() {
        // Create a sample List of AnimalTasks
        List<AnimalTask> leftOverTasks = new ArrayList<>();
        Animal animal = new Animal(1, "Fluffy", "fox");
        Animal animal2 = new Animal(19, "pogsa", "raccoon");
        leftOverTasks.add(new AnimalTask(animal, "Eyedrops", 9, 1, 2, 25));
        leftOverTasks.add(new AnimalTask(animal2, "Inspect broken leg", 10, 6, 2, 5));

        // Set the leftOverTasks using setLeftOverTasks()
        Schedule zooSchedule = new Schedule();
        zooSchedule.setLeftOverTasks(leftOverTasks);

        // Verify that getLeftOverTasks() returns the same List of AnimalTasks
        assertEquals(leftOverTasks, zooSchedule.getLeftOverTasks());
    }

    /*
     * Tests setAnimalTasks method
     */
    @Test
    public void testSetAnimalTasks() {
        // Create a sample ArrayList of AnimalTasks
        ArrayList<AnimalTask> animalTasks = new ArrayList<>();
        Animal animal = new Animal(1, "Fluffy", "fox");
        Animal animal2 = new Animal(19, "pogsa", "raccoon");
        animalTasks.add(new AnimalTask(animal, "Eyedrops", 9, 1, 2, 25));
        animalTasks.add(new AnimalTask(animal2, "Inspect broken leg", 10, 6, 2, 5));

        // Set the animalTasks using setAnimalTasks()
        Schedule zooSchedule = new Schedule();
        zooSchedule.setAnimalTasks(animalTasks);
    }
}
