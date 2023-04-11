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
     * @param taskID The task's ID.
     * @param startHour The task's start hour.
     * @param maxWindow The task's maximum window.
     * @return Task object.
     */
    public Task(int taskID, int startHour, int maxWindow) {
        this.startHour = startHour;
        this.taskID = taskID;
        this.maxWindow = maxWindow;
    }

    /**
     * This method is used to set the task's start hour.
     * @param startHour
     * @return void
     */
    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    /**
     * This method is used to set the task's ID.
     * @param taskID
     * @return void
     */
    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    /**
     * This method is used to set the task's maximum window.
     * @param maxWindow
     * @return void
     */
    public void setMaxWindow(int maxWindow) {
        this.maxWindow = maxWindow;
    }

    /**
     * This method is used to get the task's start hour.
     * @param void
     * @return int
     */
    public int getStartHour() {
        return this.startHour;
    }

    /**
     * This method is used to get the task's ID.
     * @param void
     * @return int
     */
    public int getTaskID() {
        return this.taskID;
    }

    /**
     * This method is used to get the task's maximum window.
     * @param void
     * @return int
     */
    public int getMaxWindow() {
        return this.maxWindow;
    }

    /**
     * This method is used to compare the maximum window of two tasks.
     * @param other The other task to compare to.
     * @return int
     */
    @Override
    public int compareTo(Task other) {
        // TODO Auto-generated method stub
        return Integer.compare(this.maxWindow, other.maxWindow);
    }

}
