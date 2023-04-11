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
        if (startHour < 0 || startHour > 23) {
            throw new IllegalArgumentException("Start hour must be between 0 and 23");
        }
        if (maxWindow <= 0 || maxWindow > 24) {
            throw new IllegalArgumentException("Max window must be between 0 and 24");
        }

    }

    // Getters and setters
    public void setStartHour(int startHour) {
        if (startHour < 0 || startHour > 23) {
            throw new IllegalArgumentException("Start hour must be between 0 and 23");
        }
        this.startHour = startHour;

    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public void setMaxWindow(int maxWindow) {
        if (maxWindow <= 0 || maxWindow > 24) {
            throw new IllegalArgumentException("Max window must be between 0 and 24");
        }

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

        return Integer.compare(this.maxWindow, other.maxWindow);
    }

}
