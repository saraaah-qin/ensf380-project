package edu.ucalgary.oop;

import java.util.TreeSet;

public class ScheduleItems {
    public int spillover;
    public String Starttime;
    public TreeSet<Task> task;

    ScheduleItems() {
        spillover = 0;
        Starttime = "";
        task = null;
    }
    ScheduleItems(int spillover, String Starttime, TreeSet< Task> task) {
        this.spillover = spillover;
        this.Starttime = Starttime;
        this.task = task;
    }
    

    
}
