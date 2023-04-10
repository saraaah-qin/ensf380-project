package edu.ucalgary.oop;

public class Task implements Comparable<Task> {
    private int startHour;
    private int taskID;
    private int maxWindow;

    // Constructor added because it's missing in the UML diagram
    public Task(int taskID, int startHour, int maxWindow) {
        this.startHour = startHour;
        this.taskID = taskID;
        this.maxWindow = maxWindow;
    }

    // Getters and setters
    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public void setMaxWindow(int maxWindow) {
        this.maxWindow = maxWindow;
    }

    public int getStartHour() {
        return this.startHour;
    }

    public int getTaskID() {
        return this.taskID;
    }

    public int getMaxWindow() {
        return this.maxWindow;
    }

    @Override
    public int compareTo(Task other) {
        // TODO Auto-generated method stub
        return Integer.compare(this.maxWindow, other.maxWindow);
    }

}
