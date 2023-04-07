package edu.ucalgary.oop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

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
            // for (Map.Entry<Integer, Animal> entry : animals.entrySet()) {
            // System.out.println(entry.getKey() + " " +
            // entry.getValue().getAnimalNickname() + " "
            // + entry.getValue().getAnimalSpecies());
            // }

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
                    "SELECT TREATMENTS.AnimalID, TREATMENTS.TaskID, TREATMENTS.StartHour, TASKS.MaxWindow, TASKS.Description, TASKS.Duration "
                            +
                            "FROM TREATMENTS " +
                            "JOIN TASKS ON TREATMENTS.TaskID = TASKS.TaskID " +
                            "JOIN ANIMALS ON TREATMENTS.AnimalID = ANIMALS.AnimalID " +
                            "ORDER BY (TREATMENTS.StartHour + TASKS.MaxWindow) ASC, TREATMENTS.StartHour ASC, TASKS.Duration DESC;");
            while (tasksQuery.next()) {
                animalTasks.add(new AnimalTask(animals.get(tasksQuery.getInt("AnimalID")),
                        tasksQuery.getString("Description"),
                        tasksQuery.getInt("StartHour"), tasksQuery.getInt("TaskID"), tasksQuery.getInt("MaxWindow"),
                        tasksQuery.getInt("Duration")));

            }

            tasksQuery.close();

            // print out the animal tasks
            // System.out.println("AnimalID TaskID MaxWindow StartHour Duration");
            // for (AnimalTask task : animalTasks) {
            // System.out
            // .println(task.getAnimal().getAnimalID() + " " + task.getTaskID() + " "
            // + task.getMaxWindow()
            // + " "
            // + task.getStartHour()
            // + " "
            // + task.getDuration());
            // }

            Map<Integer, Integer> startFirst = new HashMap();
            int maxWindow = 1;
            int startHour = 0;
            for (AnimalTask task : animalTasks) {
                if (task.getMaxWindow() == maxWindow) {
                    if (task.getStartHour() == startHour) {
                        if (startFirst.containsKey(task.getStartHour())) {
                            int time = startFirst.get(startHour);
                            if (time + task.getDuration() <= 60) {
                                System.out.println(startHour + ": " + time);
                                time += task.getDuration();
                                startFirst.put(startHour, time);
                            } else {
                                boolean nextAvailable = false;
                                while (nextAvailable) {
                                    int newHour = startHour + 1;
                                    if (startFirst.containsKey(newHour)) {
                                        time = startFirst.get(newHour);
                                        if (time + task.getDuration() <= 60) {
                                            System.out.println(newHour + ": " + time);
                                            time += task.getDuration();
                                            startFirst.put(newHour, time);
                                            nextAvailable = true;
                                        }
                                    }
                                }
                            }
                        } else {
                            int time = 0;
                            System.out.println(startHour + ": " + time);
                            time += task.getDuration();
                            startFirst.put(startHour, time);
                        }
                    } else {
                        startHour = task.getStartHour();
                        if (startFirst.containsKey(task.getStartHour())) {
                            int time = startFirst.get(startHour);
                            if (time + task.getDuration() <= 60) {
                                System.out.println(startHour + ": " + time);
                                time += task.getDuration();
                                startFirst.put(startHour, time);
                            } else {
                                boolean nextAvailable = false;
                                while (nextAvailable) {
                                    int newHour = startHour + 1;
                                    if (startFirst.containsKey(newHour)) {
                                        time = startFirst.get(newHour);
                                        if (time + task.getDuration() <= 60) {
                                            System.out.println(newHour + ": " + time);
                                            time += task.getDuration();
                                            startFirst.put(newHour, time);
                                            nextAvailable = true;
                                        }
                                    }
                                }
                            }
                        } else {
                            int time = 0;
                            System.out.println(startHour + ": " + time);
                            time += task.getDuration();
                            startFirst.put(startHour, time);
                        }
                    }

                } else {
                    maxWindow = task.getMaxWindow();
                    startHour = task.getStartHour();
                    if (task.getStartHour() == startHour) {
                        if (startFirst.containsKey(task.getStartHour())) {
                            int time = startFirst.get(startHour);
                            if (time + task.getDuration() <= 60) {
                                System.out.println(startHour + ": " + time);
                                time += task.getDuration();
                                startFirst.put(startHour, time);
                            } else {
                                boolean nextAvailable = false;
                                while (nextAvailable) {
                                    int newHour = startHour + 1;
                                    if (startFirst.containsKey(newHour)) {
                                        time = startFirst.get(newHour);
                                        if (time + task.getDuration() <= 60) {
                                            System.out.println(newHour + ": " + time);
                                            time += task.getDuration();
                                            startFirst.put(newHour, time);
                                            nextAvailable = true;
                                        }
                                    }
                                }
                            }
                        } else {
                            int time = 0;
                            System.out.println(startHour + ": " + time);
                            time += task.getDuration();
                            startFirst.put(startHour, time);
                        }
                    } else {
                        startHour = task.getStartHour();
                        if (startFirst.containsKey(task.getStartHour())) {
                            int time = startFirst.get(startHour);
                            if (time + task.getDuration() <= 60) {
                                System.out.println(startHour + ": " + time);
                                time += task.getDuration();
                                startFirst.put(startHour, time);
                            } else {
                                boolean nextAvailable = false;
                                while (nextAvailable) {
                                    int newHour = startHour + 1;
                                    if (startFirst.containsKey(newHour)) {
                                        time = startFirst.get(newHour);
                                        if (time + task.getDuration() <= 60) {
                                            System.out.println(newHour + ": " + time);
                                            time += task.getDuration();
                                            startFirst.put(newHour, time);
                                            nextAvailable = true;
                                        }
                                    }
                                }
                            }
                        } else {
                            int time = 0;
                            System.out.println(startHour + ": " + time);
                            time += task.getDuration();
                            startFirst.put(startHour, time);
                        }
                    }

                }

            }

            // print out the startFirst map
            System.out.println("StartHour Duration");
            for (Map.Entry<Integer, Integer> entry : startFirst.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }

            myConnect.close();

            /*
             * Cages
             * Orphaned animals of the same litter share a cage and
             * are listed as a single animal in the database.
             * Coyote - 5 min/cage
             * Porcupines - 10 min/cage
             * Foxes - 5 min/cage
             * Raccoons - 5 min/cage
             * Beavers - 5 min/cage
             * Feeding
             * Noturnal animals fed in 3 hr window starting at midnight
             * - Foxes(5min + 5min prep /each)
             * - Beavers(5min/each)
             * Diurnal animals fed in 3 hr window starting at 8 AM
             * - Raccoons(5min/each)
             * Crepuscular animals fed in 3 hr window starting at 7 PM
             * - Coyotes(5min + 10min prep /each)
             * - Porcupines(5min/each)
             */

        } catch (

        Exception e) {
            System.out.println(e);
        }
    }
}
