package edu.ucalgary.oop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.w3c.dom.ranges.Range;

public class Schedule {
    private static List<AnimalTask> scheduleAnimalTasks = new ArrayList(); // primary scheduled list
    private static List<AnimalTask> scheduleAnimalTasks2 = new ArrayList(); // secondary scheduled list for the
                                                                            // assistant
    private static List<AnimalTask> leftOverTasks = new ArrayList(); // Tertiary scheduled list for the animal tasks
                                                                     // that are not able to be scheduled

    public String generateSchedule() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection myConnect = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/EWR", "oop", "password");
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

            List<AnimalTask> animalTasks = new ArrayList();
            ResultSet tasksQuery = statement.executeQuery(
                    "SELECT TREATMENTS.AnimalID, TREATMENTS.TaskID, TREATMENTS.StartHour, TASKS.MaxWindow, TASKS.Description, TASKS.Duration "
                            +
                            "FROM TREATMENTS " +
                            "JOIN TASKS ON TREATMENTS.TaskID = TASKS.TaskID " +
                            "JOIN ANIMALS ON TREATMENTS.AnimalID = ANIMALS.AnimalID " +
                            "ORDER BY (TASKS.MaxWindow) ASC,  TASKS.Duration DESC, TREATMENTS.StartHour ASC;");

            while (tasksQuery.next()) {
                animalTasks.add(new AnimalTask(animals.get(tasksQuery.getInt("AnimalID")),
                        tasksQuery.getString("Description"),
                        tasksQuery.getInt("StartHour"), tasksQuery.getInt("TaskID"), tasksQuery.getInt("MaxWindow"),
                        tasksQuery.getInt("Duration")));

            }

            tasksQuery.close();

            for (Map.Entry<Integer, Animal> entry : animals.entrySet()) {
                Animal animal = entry.getValue();
                AnimalSpecies animalSpecies = animal.getAnimalSpecies();
                int duration = animalSpecies.getCleanTime();
                animalTasks.add(new AnimalTask(animal, "Cage Cleaning", 0, -999999, 24, duration));
            }

            int leftover = 0;
            int hour = 0;
            int spillOver = 0;
            int time = 0;
            while (hour < 24) {
                Iterator<AnimalTask> iterator = animalTasks.iterator();

                while (iterator.hasNext()) {
                    AnimalTask task = iterator.next();

                    if (time >= 60) {
                        break;
                    }

                    if (task.getStartHour() <= hour) {
                        if (time + task.getDuration() <= 60) {
                            if (hour + task.getDuration() / 60 < task.getMaxWindow() + task.getStartHour()) {

                                task.startTime = LocalTime.of(hour, time % 60);
                                task.endTime = LocalTime.of(hour + (time + task.getDuration()) / 60,
                                        (time + task.getDuration()) % 60);
                                time += task.getDuration();
                                scheduleAnimalTasks.add(task);
                                iterator.remove();

                            }
                        }
                    }
                }
                time = 0;
                hour += 1;
            }

            if (animalTasks.size() > 0) {
                leftover = 0;
                hour = 0;
                spillOver = 0;
                time = 0;
                while (hour <= 23) {
                    Iterator<AnimalTask> iterator2 = animalTasks.iterator();
                    while (iterator2.hasNext()) {
                        AnimalTask task = iterator2.next();
                        if (time >= 60) {
                            break;
                        }
                        if (task.getStartHour() <= hour) {
                            if (time + task.getDuration() <= 60) {
                                if (hour + task.getDuration() / 60 < task.getMaxWindow() + task.getStartHour()) {
                                    if (hour + task.getDuration() / 60 < task.getMaxWindow() + task.getStartHour()) {

                                        task.startTime = LocalTime.of(hour, time % 60);
                                        task.endTime = LocalTime.of(hour + (time + task.getDuration()) / 60,
                                                (time + task.getDuration()) % 60);
                                        time += task.getDuration();
                                        scheduleAnimalTasks2.add(task);
                                        iterator2.remove(); // remove task from animalTasks

                                    }
                                }
                            }
                        }
                    }
                    time = 0;
                    hour += 1;
                }
            }

            StringBuilder scheduleOutput = new StringBuilder();

            if (animalTasks.size() > 0) {
                System.out.println("No schedule possible");
                System.out.println("There is no space for the volunteer and Sara to do the following task(s):");
                for (AnimalTask task : animalTasks) {
                    leftOverTasks.add(task);
                    scheduleOutput
                            .append("* " + task.getDescription() + "(" + task.getAnimal().getAnimalNickname() + ")")
                            .append("\n");
                }
                return scheduleOutput.toString();
            }
            int lastOneDone = -5;
            int currentHour = 0;
            boolean help = false;
            while (currentHour < 24) {
                help = false;
                for (AnimalTask task : scheduleAnimalTasks2) {
                    if (task.startTime.getHour() == currentHour) {
                        help = true;
                    }
                }
                for (AnimalTask task : scheduleAnimalTasks) {
                    if (task.startTime.getHour() == currentHour) {
                        if (currentHour != lastOneDone) {
                            if (help) {
                                scheduleOutput.append(currentHour + ":00 [+ back up volunteer]").append("\n");

                            } else {
                                scheduleOutput.append(currentHour + ":00").append("\n");
                            }
                            lastOneDone = currentHour;
                        }
                        scheduleOutput.append(
                                "* " + task.getDescription() + "(" + task.getAnimal().getAnimalNickname() + ")")
                                .append("\n");
                    }
                }
                for (AnimalTask task : scheduleAnimalTasks2) {
                    if (task.startTime.getHour() == currentHour) {

                        scheduleOutput.append(
                                "* " + task.getDescription() + "(" + task.getAnimal().getAnimalNickname() + ")")
                                .append("\n");
                    }
                }
                currentHour += 1;
            }

            // I NOW NEED TO ORGANIZE ALL THE START DATES

            // print out the startFirst map
            // System.out.println("First Schedule");
            // int currentTime= -5;

            // int currentHour=0;

            // while(currentHour<24){

            // for(AnimalTask task : scheduleAnimalTasks.add){
            // if(task.getStartHour()==currentHour){
            // System.out.println("Animal ID: "+ task.getAnimal().getAnimalID()+" Task ID:
            // "+ task.getTaskID()+" STARTS AT: "+task.startTime +" and ENDS AT: "+
            // task.endTime + " Duration: "+ task.getDuration()+ " Start Hour: "+
            // task.getStartHour()+ " Max Window: "+ task.getMaxWindow());
            // }
            // }

            // currentHour+=1;
            // }

            // for (AnimalTask task : scheduleAnimalTasks) {
            // if(currentTime != task.startTime.getHour()){
            // currentTime = task.startTime.getHour();

            // boolean volunteerNeeded = false;
            // for(AnimalTask task2 : scheduleAnimalTasks2){
            // if(task2.startTime.getHour() == currentTime){
            // volunteerNeeded = true;
            // }
            // }
            // if(volunteerNeeded){
            // System.out.println("Time: "+currentTime +":00" + "[+ backup volunteer]");
            // }else{ System.out.println("Time: "+currentTime +":00");
            // }
            // }
            // for(AnimalTask task2 : scheduleAnimalTasks2){
            // if(task2.startTime.getHour() == currentTime){
            // System.out.println("- "+ task2.getDescription()+ " Animal ID: "+
            // task2.getAnimal().getAnimalID()+" Task ID: "+ task2.getTaskID()+" STARTS AT:
            // "+task2.startTime +" and ENDS AT: "+ task2.endTime + " Duration: "+
            // task2.getDuration()+ " Start Hour: "+ task2.getStartHour()+ " Max Window: "+
            // task2.getMaxWindow());
            // }
            // System.out.println( "- "+ task.getDescription() + " Animal ID"+
            // task.getAnimal().getAnimalID()+" Task ID: "+ task.getTaskID()+" STARTS AT:
            // "+task.startTime +" and ENDS AT: "+ task.endTime + " Duration: "+
            // task.getDuration()+ " Start Hour: "+ task.getStartHour()+ " Max Window: "+
            // task.getMaxWindow());
            // }

            // System.out.println("Assistant Schedule");
            // for(AnimalTask task : scheduleAnimalTasks2){
            // System.out.println(" Animal ID: "+ task.getTaskID()+" STARTS AT:
            // "+task.startTime +" and ENDS AT: "+ task.endTime + " Start Hour: "+
            // task.getStartHour() + " Duration: "+ task.getDuration());
            // }

            myConnect.close();
            return scheduleOutput.toString();

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

        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
