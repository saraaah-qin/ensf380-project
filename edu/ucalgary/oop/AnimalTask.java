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
    private int treatmentID;

    /**
     * This is the constructor for the AnimalTask class. Uses the Task class
     * constructor.
     * 
     * @param animal      The animal's species.
     * @param description The task's description.
     * @param startHour   The task's start hour.
     * @param taskID      The task's ID.
     * @param maxWindow   The task's maximum window.
     * @param duration    The task's duration.
     * @return AnimalTask object.
     */
    public AnimalTask(Animal animal, String description, int startHour, int taskID, int maxWindow, int duration) {
        super(taskID, startHour, maxWindow);
        setAnimal(animal);
        setDescription(description);
        setDuration(duration);
    }

    /**
     * This method is used to set the animal's species.
     * 
     * @param animal
     * @return void
     */
    public void setAnimal(Animal animal) {
        this.animal = animal;

    }

    /**
     * This method is used to set the task's description.
     * 
     * @param description
     * @return void
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method is used to set the task's duration.
     * 
     * @param duration
     * @return void
     */
    public void setDuration(int duration) {
        if (duration < 0 || duration > 60)
            throw new IllegalArgumentException("Duration must be greater than 0 and less than 60");
        this.duration = duration;
    }

    /**
     * This method is used to set the task's treatmentID.
     * 
     * @param treatmentID
     * @return void
     */
    public void setTreatmentID(int treatmentID) {
        this.treatmentID = treatmentID;
    }

    /**
     * This method is used to get the task's treatmentID.
     * 
     * @param void
     * @return Integer
     */
    public int getTreatmentID() {
        return this.treatmentID;
    }

    /**
     * This method is used to get the animal.
     * 
     * @param void
     * @return Animal
     */
    public Animal getAnimal() {
        return this.animal;
    }

    /**
     * This method is used to get the task's description.
     * 
     * @param void
     * @return String
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * This method is used to get the task's duration.
     * 
     * @param void
     * @return Integer
     */
    public int getDuration() {
        return this.duration;
    }

    /**
     * This method is used to get the task's max window.
     * 
     * @param void
     * @return Integer
     */
    public int getMaxWindow() {
        return super.getMaxWindow();
    }

    /**
     * This method is used to get the task's start time.
     * 
     * @param void
     * @return LocalTime
     */
    public void setStartTime(LocalTime startTime) {

        this.startTime = startTime;
    }

    /**
     * This method is used to get the task's end time.
     * 
     * @param void
     * @return LocalTime
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    /**
     * This method is used to get the task's start time.
     * 
     * @param void
     * @return LocalTime
     */
    public LocalTime getStartTime() {
        return this.startTime;
    }

    /**
     * This method is used to get the task's end time.
     * 
     * @param void
     * @return LocalTime
     */
    public LocalTime getEndTime() {
        return this.endTime;
    }

    /**
     * This method is used to get the task's ID.
     * 
     * @param void
     * @return Integer
     */
    public int getTaskID() {
        return super.getTaskID();
    }

}
