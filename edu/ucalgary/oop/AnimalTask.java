/**
 * @author David Rodriguez Barrios
 * @author Sarah Qin
 * @version 7.0
 * @since 1.0
 */
 
/**
 * This class is used to create an AnimalTask object. This class inherits from the Task class.
 * It contains the animal's species, description, duration, start time, and end time.
 */

package edu.ucalgary.oop;

import java.time.LocalTime;

public class AnimalTask extends Task {
    private Animal animal;
    private String description;
    private int duration;
    private LocalTime startTime;
    private LocalTime endTime;

    /**
     * This is the constructor for the AnimalTask class. Uses the Task class constructor.
     * @param animal The animal's species.
     * @param description The task's description.
     * @param startHour The task's start hour.
     * @param taskID The task's ID.
     * @param maxWindow The task's maximum window.
     * @param duration The task's duration.
     */
    public AnimalTask(Animal animal, String description, int startHour, int taskID, int maxWindow, int duration) {
        super(taskID, startHour, maxWindow);
        setAnimal(animal);
        setDescription(description);
        setDuration(duration);
    }

    /**
     * This method is used to set the animal's species.
     * @param animal
     */
    public void setAnimal(Animal animal) {
        this.animal = animal;
    }
    /**
     * This method is used to set the task's description.
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * This method is used to set the task's duration.
     * @param duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }
    /**
     * This method is used to get the animal's species.
     */
    public Animal getAnimal() {
        return this.animal;
    }
    /**
     * This method is used to get the task's description.
     * @return String
     */
    public String getDescription() {
        return this.description;
    }
    /**
     * This method is used to get the task's duration.
     * @return int
     */
    public int getDuration() {
        return this.duration;
    }
    /**
     * This method is used to get the task's start time.
     * @return int
     */
    public int getMaxWindow() {
        return super.getMaxWindow();
    }
    /**
     * This method is used to get the task's start time.
     * @param startTime
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    /**
     * This method is used to get the task's end time.
     * @param endTime
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    /**
     * This method is used to get the task's start time.
     * @return LocalTime
     */
    public LocalTime getStartTime() {
        return this.startTime;
    }
    /**
     * This method is used to get the task's end time.
     * @return LocalTime
     */
    public LocalTime getEndTime() {
        return this.endTime;
    }
    /**
     * This method is used to get the task's ID.
     * @return int
     */
    public int getTaskID() {
        return super.getTaskID();
    }

}
