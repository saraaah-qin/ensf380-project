package edu.ucalgary.oop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.w3c.dom.ranges.Range;

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


            List<AnimalTask> scheduleAnimalTasks = new ArrayList(); // primary scheduled list
            List<AnimalTask> scheduleAnimalTasks2 = new ArrayList(); // secondary scheduled list for the assistant


            int max=0;

            for (AnimalTask task : animalTasks) {
                if (task.getStartHour()+task.getMaxWindow() > max) {
                    max = task.getStartHour()+task.getDuration();
                }
            }
           
           


            int leftover=0;
            int hour =0;
            int spillOver=0;
            int time=0;
            while(hour <= max) {
                Iterator<AnimalTask> iterator = animalTasks.iterator();
            
                while(iterator.hasNext()) {
                    AnimalTask task = iterator.next();
            
                    if(time >= 60) {
                        break;
                    }
            
                    if(task.getStartHour() <= hour) {
                        if(time + task.getDuration() <= 60) {
                            if(hour + task.getDuration() / 60 < task.getMaxWindow() + task.getStartHour()) {
 
                                if(time % 60<10){
                                    task.startTime = String.valueOf(hour) + ":" + "0"+ String.valueOf(time % 60);
                                }
                                else{
                                    task.startTime = String.valueOf(hour) + ":" + String.valueOf(time % 60);
                                }
                                // task.endTime = String.valueOf(hour + task.getDuration() / 60) + ":" + String.valueOf(time % 60 + task.getDuration());
                                 
                              
                                if((time +task.getDuration())%60 <10){
                                    task.endTime = String.valueOf(hour + (task.getDuration()+time) / 60) + ":" + "0"+ String.valueOf((time +task.getDuration())%60);
                                }
                                else{
                                    task.endTime = String.valueOf(hour + (time+task.getDuration()) / 60) + ":" + String.valueOf((time +task.getDuration())%60);
                                }
                                time += task.getDuration();
                                scheduleAnimalTasks.add(task);
                                iterator.remove(); // remove task from animalTasks
                                
                            }
                     }
                }}
                time = 0;
                hour += 1;
            }
        

            if( animalTasks.size()>0){
                leftover=0;
                hour =0;
                spillOver=0;
                time=0;
                while(hour<=max){
                    Iterator<AnimalTask> iterator2 = animalTasks.iterator();
                    while(iterator2.hasNext()) {
                        AnimalTask task = iterator2.next();
                        if(time>=60){
                            break;
                        }
                        if (task.getStartHour()<=hour){
                            if(time+task.getDuration()<=60){
                                if(hour+task.getDuration()/60 <task.getMaxWindow()+task.getStartHour()){
                                    if(hour + task.getDuration() / 60 < task.getMaxWindow() + task.getStartHour()) {
 
                                        if(time % 60<10){
                                            task.startTime = String.valueOf(hour) + ":" + "0"+ String.valueOf(time % 60);
                                        }
                                        else{
                                            task.startTime = String.valueOf(hour) + ":" + String.valueOf(time % 60);
                                        }
                                        // task.endTime = String.valueOf(hour + task.getDuration() / 60) + ":" + String.valueOf(time % 60 + task.getDuration());
                                         
                                      
                                        if((time +task.getDuration())%60 <10){
                                            task.endTime = String.valueOf(hour + (task.getDuration()+time) / 60) + ":" + "0"+ String.valueOf((time +task.getDuration())%60);
                                        }
                                        else{
                                            task.endTime = String.valueOf(hour + (time+task.getDuration()) / 60) + ":" + String.valueOf((time +task.getDuration())%60);
                                        }
                                        time += task.getDuration();
                                        scheduleAnimalTasks2.add(task);
                                        iterator2.remove(); // remove task from animalTasks
                                        
                                    }
                                }
                            }
                        }
                    }
                    time=0;
                    hour+=1;
                }
            }
        
            if(animalTasks.size()>0){
                System.out.println("No schedule possible");
                System.out.println("There is no space for the volunteer and Sara to do the following task(s):");
                for (AnimalTask task : animalTasks) {
                    System.out.println(task);
                }
                return;
            }


// I NOW NEED TO ORGANIZE ALL THE START DATES

        

            // print out the startFirst map
            System.out.println("First Schedule");

            for (AnimalTask task : scheduleAnimalTasks) {
                
                // if(currentTime!=task.startTime.charAt(0)){
                //    System.out.println(task.startTime+":00");
                //    currentTime=task.startTime.charAt(0);}
               
                System.out.println("Animal ID"+ task.getAnimal().getAnimalID()+" Task ID: "+ task.getTaskID()+" STARTS AT: "+task.startTime +" and ENDS AT: "+ task.endTime + " Duration: "+ task.getDuration());
            }
            System.out.println("Assistant Schedule");
            for(AnimalTask task : scheduleAnimalTasks2){
                System.out.println("ID: "+ task.getTaskID()+" STARTS AT: "+task.startTime +" and ENDS AT: "+ task.endTime);
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

     } catch (Exception e) {
            System.out.println(e);
        }
    }}





