package edu.ucalgary.oop;

import java.util.ArrayList;

public class Scheduler {
    private ArrayList<Volunteer> volunteerList;
    private ArrayList<AnimalTask> animalTaskList;
    private ArrayList<NonAnimalTask> nonAnimalTaskList;
    private String arguments; // what is this for?

    public void addVolunteer(Volunteer volunteer) {
        volunteerList.add(volunteer);
    }

    public void addAnimalTask(AnimalTask animalTask) {
        animalTaskList.add(animalTask);
    }

    public void addNonAnimalTask(NonAnimalTask nonAnimalTask) {
        nonAnimalTaskList.add(nonAnimalTask);
    }

    // ARE YOU SURE WE WANT TO RETURN A STRING HERE?
    public String getAnimalTaskList() {
        return this.animalTaskList;
    }

    // ARE YOU SURE WE WANT TO RETURN A STRING HERE?
    public String getNonAnimalTaskList() {
        return this.nonAnimalTaskList;
    }

    // ARE YOU SURE WE WANT TO RETURN A STRING HERE?
    public String getVolunteerList() {
        return this.volunteerList;
    }

    public void clearVolunteerList() {
        volunteerList.clear();
    }

    public void clearAnimalTaskList() {
        animalTaskList.clear();
    }

    public void clearNonAnimalTaskList() {
        nonAnimalTaskList.clear();
    }
}
