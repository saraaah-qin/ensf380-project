package edu.ucalgary.oop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection myConnect = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/EWR", "oop",
                    "password");
            Statement statement = myConnect.createStatement();

            ResultSet animalQuery = statement.executeQuery("SELECT * FROM animals");

            List<Animal> animals = new ArrayList();
            while (animalQuery.next()) {
                Animal animal = new Animal(animalQuery.getInt("AnimalID"), animalQuery.getString("AnimalNickname"),
                        animalQuery.getString("AnimalSpecies"));
                animals.add(animal);
            }
            animalQuery.close();

            for (Animal animal : animals) {
                System.out.println(animal.getAnimalID() + " " + animal.getAnimalNickname() + " "
                        + animal.getAnimalSpecies());
            }

            ResultSet tasksQuery = statement.executeQuery("SELECT * FROM tasks");
            while (tasksQuery.next()) {
                System.out.println(tasksQuery.getInt("TaskID") + " " + tasksQuery.getString("Description") + " "
                        + tasksQuery.getInt("Duration") + " " + tasksQuery.getInt("MaxWindow"));
            }
            tasksQuery.close();

            ResultSet treatmentsQuery = statement.executeQuery("SELECT * FROM treatments");
            while (treatmentsQuery.next()) {
                System.out.println(treatmentsQuery.getInt("AnimalID") + " " + treatmentsQuery.getInt("TaskID") + " "
                        + treatmentsQuery.getInt("StartHour"));
            }
            treatmentsQuery.close();

            myConnect.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
