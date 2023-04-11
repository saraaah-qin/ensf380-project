/**
 * @author Sarah Qin
 * @author David Rodriguez Barrios
 * @version 3.0
 * @since 1.0
 */
/**
 * This class is used as a parent class to the Animal Task class. It contains the task's ID, start hour, and maximum window.
 * It also contains the methods to set and get the task's ID, start hour, and maximum window. Implements the Comparable interface.
 * Overides the compareTo method.
 */

package edu.ucalgary.oop;

public class Task implements Comparable<Task> {
    private int startHour;
    private int taskID;
    private int maxWindow;

    /**
     * This is the constructor for the Task class.
     * 
     * @param taskID    The task's ID.
     * @param startHour The task's start hour.
     * @param maxWindow The task's maximum window.
     */

    public Task(int taskID, int startHour, int maxWindow) {
        this.startHour = startHour;
        this.taskID = taskID;
        this.maxWindow = maxWindow;
        if (startHour < 0 || startHour > 23) {
            throw new IllegalArgumentException("Start hour must be between 0 and 23");
        }
        if (maxWindow <= 0 || maxWindow > 24) {
            throw new IllegalArgumentException("Max window must be between 0 and 24");
        }

    }

    /**
     * This method is used to set the task's start hour.
     * 
     * @param startHour
     */
    public void setStartHour(int startHour) {
        if (startHour < 0 || startHour > 23) {
            throw new IllegalArgumentException("Start hour must be between 0 and 23");
        }
        this.startHour = startHour;
    }

    /**
     * This method is used to set the task's ID.
     * 
     * @param taskID
     */
    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    /**
     * This method is used to set the task's maximum window.
     * 
     * @param maxWindow
     */
    public void setMaxWindow(int maxWindow) {
        if (maxWindow <= 0 || maxWindow > 24) {
            throw new IllegalArgumentException("Max window must be between 0 and 24");
        }

        this.maxWindow = maxWindow;
    }

    /**
     * This method is used to get the task's start hour.
     * 
     * @return startHour
     */
    public int getStartHour() {
        return this.startHour;
    }

    /**
     * This method is used to get the task's ID.
     * 
     * @return taskID
     */
    public int getTaskID() {
        return this.taskID;
    }

    /**
     * This method is used to get the task's maximum window.
     * 
     * @return maxWindow
     */
    public int getMaxWindow() {
        return this.maxWindow;
    }

    /**
     * This method is used to compare the maximum window of two tasks.
     * 
     * @param other
     * @return int
     */
    @Override
    public int compareTo(Task other) {

        return Integer.compare(this.maxWindow, other.maxWindow);
    }

}
