package edu.ucalgary.oop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Schedule {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection myConnect = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/EWR", "oop",
                    "password");
            Statement statement = myConnect.createStatement();

            ResultSet animalQuery = statement.executeQuery("SELECT * FROM animals");

            // create a hashmap for animal objects so that we can easily access them by
            // animalID
            Map<Integer, Animal> animals = new HashMap();
            while (animalQuery.next()) {
                Animal animal = new Animal(animalQuery.getInt("AnimalID"), animalQuery.getString("AnimalNickname"),
                        animalQuery.getString("AnimalSpecies"));
                animals.put(animalQuery.getInt("AnimalID"), animal);
            }
            animalQuery.close();

            // print out the animals
            for (Map.Entry<Integer, Animal> entry : animals.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue().getAnimalNickname() + " "
                        + entry.getValue().getAnimalSpecies());
            }

            // task 1 is always fixed - kit feed
            // ResultSet tasksQuery = statement.executeQuery("SELECT * FROM tasks");
            // while (tasksQuery.next()) {
            // System.out.println(tasksQuery.getInt("TaskID") + " " +
            // tasksQuery.getString("Description") + " "
            // + tasksQuery.getInt("Duration") + " " + tasksQuery.getInt("MaxWindow"));
            // }
            // tasksQuery.close();

            // ResultSet treatmentsQuery = statement.executeQuery("SELECT * FROM
            // treatments");
            // while (treatmentsQuery.next()) {
            // System.out.println(treatmentsQuery.getInt("AnimalID") + " " +
            // treatmentsQuery.getInt("TaskID") + " "
            // + treatmentsQuery.getInt("StartHour"));
            // }
            // treatmentsQuery.close();
            List<AnimalTask> animalTasks = new ArrayList();
            ResultSet tasksQuery = statement.executeQuery(
                    "SELECT TREATMENTS.AnimalID, TREATMENTS.TaskID, TREATMENTS.StartHour, TASKS.MaxWindow, TASKS.Description "
                            +
                            "FROM TREATMENTS " +
                            "JOIN TASKS ON TREATMENTS.TaskID = TASKS.TaskID " +
                            "JOIN ANIMALS ON TREATMENTS.AnimalID = ANIMALS.AnimalID");
            while (tasksQuery.next()) {
                animalTasks.add(new AnimalTask(animals.get(tasksQuery.getInt("AnimalID")),
                        tasksQuery.getString("Description"),
                        tasksQuery.getInt("StartHour"), tasksQuery.getInt("TaskID"), tasksQuery.getInt("MaxWindow")));

            }

            // print out the animal tasks
            System.out.println("AnimalID AnimalSpecies Description TaskID StartHour MaxWindow");
            for (AnimalTask task : animalTasks) {
                System.out
                        .println(task.getAnimal().getAnimalID() + " " + task.getAnimal().getAnimalSpecies() + " "
                                + task.getDescription() + " " + task.getStartHour() + " " + task.getTaskID() + " "
                                + task.getMaxWindow());
            }

            tasksQuery.close();

            myConnect.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
