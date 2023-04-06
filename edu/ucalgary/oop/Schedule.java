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
                                task.startTime = String.valueOf(hour) + ":" + String.valueOf(time % 60);
                                task.endTime = String.valueOf(hour + task.getDuration() / 60) + ":" + String.valueOf(time % 60 + task.getDuration());
                                time += task.getDuration();
                                scheduleAnimalTasks.add(task);
                                iterator.remove(); // remove task from animalTasks
                                spillOver = 0;
                            }
                        } else if(hour + task.getDuration() / 60 < task.getMaxWindow() + task.getStartHour()) {
                            task.startTime = String.valueOf(hour) + ":" + String.valueOf(time % 60);
                            hour += task.getDuration() / 60;
                            task.endTime = String.valueOf(hour) + ":" + String.valueOf((time % 60 + task.getDuration()) % 60);
                            time += task.getDuration();
                            scheduleAnimalTasks.add(task);
                            iterator.remove(); // remove task from animalTasks
                            spillOver = time - 60;
                        }
                    }
                }
            
                time = spillOver;
                hour += 1;
            }
            

            
    

            if( animalTasks.size()>0){
                leftover=0;
                hour =0;
                spillOver=0;
                time=0;
                while(hour<=max){
                    for (AnimalTask task : animalTasks) {
                        if(time>=60){
                            break;
                        }
                        if (task.getStartHour()<=hour){
                            if(time+task.getDuration()<=60){
                                if(hour+task.getDuration()/60 <task.getMaxWindow()+task.getStartHour()){
                                    task.startTime= String.valueOf(hour) + ":" + String.valueOf(time%60);
                                    task.endTime= String.valueOf(hour+ task.getDuration()/60) + ":" + String.valueOf((time+task.getDuration())%60);
                                    time+=task.getDuration();
                                    scheduleAnimalTasks2.add(task);
                                    animalTasks.remove(task);
                                    spillOver=0;
                                }
                            }
                        }
                        else if(time+task.getDuration()>60){
                            if(hour+task.getDuration()/60 <task.getMaxWindow()+task.getStartHour()){
                                task.startTime= String.valueOf(hour) + ":" + String.valueOf(time%60);
                                hour+= task.getDuration()/60;
                                task.endTime= String.valueOf(hour) + ":" + String.valueOf((time+task.getDuration())%60);
                                time+=task.getDuration();
                                scheduleAnimalTasks2.add(task);
                                animalTasks.remove(task);
                                spillOver= time-60;
                            }
                        }
                    }
                    time=spillOver;
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






//             Map<Integer, Integer> Sce = new HashMap();
//             int maxWindow = 1;
//             int startHour = 0;
//             for (AnimalTask task : animalTasks) {
//                 if (task.getMaxWindow() == maxWindow) {
//                     if (task.getStartHour() == startHour) {
//                         if (startFirst.containsKey(task.getStartHour())) {
//                             int time = startFirst.get(startHour);
//                             if (time + task.getDuration() <= 60) {
//                                 System.out.println(startHour + ": " + time);
//                                 time += task.getDuration();
//                                 startFirst.put(startHour, time);
//                             } else {
//                                 boolean nextAvailable = false;
//                                 while (nextAvailable) {
//                                     int newHour = startHour + 1;
//                                     if (startFirst.containsKey(newHour)) {
//                                         time = startFirst.get(newHour);
//                                         if (time + task.getDuration() <= 60) {
//                                             System.out.println(newHour + ": " + time);
//                                             time += task.getDuration();
//                                             startFirst.put(newHour, time);
//                                             nextAvailable = true;
//                                         }
//                                     }
//                                 }
//                             }
//                         } else {
//                             int time = 0;
//                             System.out.println(startHour + ": " + time);
//                             time += task.getDuration();
//                             startFirst.put(startHour, time);
//                         }
//                     } else {
//                         startHour = task.getStartHour();
//                         if (startFirst.containsKey(task.getStartHour())) {
//                             int time = startFirst.get(startHour);
//                             if (time + task.getDuration() <= 60) {
//                                 System.out.println(startHour + ": " + time);
//                                 time += task.getDuration();
//                                 startFirst.put(startHour, time);
//                             } else {
//                                 boolean nextAvailable = false;
//                                 while (nextAvailable) {
//                                     int newHour = startHour + 1;
//                                     if (startFirst.containsKey(newHour)) {
//                                         time = startFirst.get(newHour);
//                                         if (time + task.getDuration() <= 60) {
//                                             System.out.println(newHour + ": " + time);
//                                             time += task.getDuration();
//                                             startFirst.put(newHour, time);
//                                             nextAvailable = true;
//                                         }
//                                     }
//                                 }
//                             }
//                         } else {
//                             int time = 0;
//                             System.out.println(startHour + ": " + time);
//                             time += task.getDuration();
//                             startFirst.put(startHour, time);
//                         }
//                     }

//                 } else {
//                     maxWindow = task.getMaxWindow();
//                     startHour = task.getStartHour();
//                     if (task.getStartHour() == startHour) {
//                         if (startFirst.containsKey(task.getStartHour())) {
//                             int time = startFirst.get(startHour);
//                             if (time + task.getDuration() <= 60) {
//                                 System.out.println(startHour + ": " + time);
//                                 time += task.getDuration();
//                                 startFirst.put(startHour, time);
//                             } else {
//                                 boolean nextAvailable = false;
//                                 while (nextAvailable) {
//                                     int newHour = startHour + 1;
//                                     if (startFirst.containsKey(newHour)) {
//                                         time = startFirst.get(newHour);
//                                         if (time + task.getDuration() <= 60) {
//                                             System.out.println(newHour + ": " + time);
//                                             time += task.getDuration();
//                                             startFirst.put(newHour, time);
//                                             nextAvailable = true;
//                                         }
//                                     }
//                                 }
//                             }
//                         } else {
//                             int time = 0;
//                             System.out.println(startHour + ": " + time);
//                             time += task.getDuration();
//                             startFirst.put(startHour, time);
//                         }
//                     } else {
//                         startHour = task.getStartHour();
//                         if (startFirst.containsKey(task.getStartHour())) {
//                             int time = startFirst.get(startHour);
//                             if (time + task.getDuration() <= 60) {
//                                 System.out.println(startHour + ": " + time);
//                                 time += task.getDuration();
//                                 startFirst.put(startHour, time);
//                             } else {
//                                 boolean nextAvailable = false;
//                                 while (nextAvailable) {
//                                     int newHour = startHour + 1;
//                                     if (startFirst.containsKey(newHour)) {
//                                         time = startFirst.get(newHour);
//                                         if (time + task.getDuration() <= 60) {
//                                             System.out.println(newHour + ": " + time);
//                                             time += task.getDuration();
//                                             startFirst.put(newHour, time);
//                                             nextAvailable = true;
//                                         }
//                                     }
//                                 }
//                             }
//                         } else {
//                             int time = 0;
//                             System.out.println(startHour + ": " + time);
//                             time += task.getDuration();
//                             startFirst.put(startHour, time);
//                         }
//                     }

//                 }

//             }

//             // print out the startFirst map
//             System.out.println("StartHour Duration");
//             for (Map.Entry<Integer, Integer> entry : startFirst.entrySet()) {
//                 System.out.println(entry.getKey() + " " + entry.getValue());
//             }

//             myConnect.close();

//             /*
//              * Cages
//              * Orphaned animals of the same litter share a cage and
//              * are listed as a single animal in the database.
//              * Coyote - 5 min/cage
//              * Porcupines - 10 min/cage
//              * Foxes - 5 min/cage
//              * Raccoons - 5 min/cage
//              * Beavers - 5 min/cage
//              * Feeding
//              * Noturnal animals fed in 3 hr window starting at midnight
//              * - Foxes(5min + 5min prep /each)
//              * - Beavers(5min/each)
//              * Diurnal animals fed in 3 hr window starting at 8 AM
//              * - Raccoons(5min/each)
//              * Crepuscular animals fed in 3 hr window starting at 7 PM
//              * - Coyotes(5min + 10min prep /each)
//              * - Porcupines(5min/each)
//              */

//         } catch (

//         Exception e) {
//             System.out.println(e);
//         }
//     }
// }



            // while(hour<=max){
                 
            //     for (AnimalTask task : animalTasks) {
            //         if(time>=60){
            //             break;

            //         }


            //         if (task.getStartHour()<=hour){ // redundant if statement but just double checks that the task ha

            //             if(time+task.getDuration()<=60){ // if there is no overflow into the other hour then...
            //                 if(hour+task.getDuration()/60 <task.getMaxWindow()+task.getStartHour()){ // checks to make sure you can finish in time
                        
                            

            //                 task.startTime= String.valueOf(hour) + ":" + String.valueOf(time%60); // sets the start time
            //                 task.endTime= String.valueOf(hour+ task.getDuration()%60) + ":" + String.valueOf(time%60+task.getDuration()); // sets the end time
            //                 time+=task.getDuration(); // increments the minutes of the current time by the duration of the task

            //                 scheduleAnimalTasks.add(task); // adds the scheduled task into the list of scheduled tasks

            //                 animalTasks.remove(task);  // removes the task from the list of tasks that need to be scheduled
            //                 spillOver=0;
            //             }}
                        
            //         }

            //         else if(time+task.getDuration()>60){ // if there is overflow into the next hour
            //             if(hour+task.getDuration()/60 <task.getMaxWindow()+task.getStartHour()){ // if it can still finish in time
                            
            //             task.startTime= String.valueOf(hour) + ":" + String.valueOf(time%60); // start time is the current hour and the current time
            //             hour+= task.getDuration()/60; // increments the hour by the number of hours the task takes
            
            //             task.endTime= String.valueOf(hour) + ":" + String.valueOf((time%60+task.getDuration())%60); // increments the time by the duration of the task
                       
            //             time+=task.getDuration(); // increments the time by the duration of the task

            //             scheduleAnimalTasks.add(task); // adds the schedule removes from treeset of event that need to be scheduled
            //             animalTasks.remove(task);
            //             spillOver= time-60;
                           
            //             }}
                    
            //     }

            // time=spillOver;
            // hour+=1;

                
            // }
            // if( animalTasks.size()>0){
            // leftover=0;
            // hour =0;
            // spillOver=0;
            // time=0;

    
            // while(hour<=max){
                 
            //     for (AnimalTask task : animalTasks) {
            //         if(time>=60){
            //             break;

            //         }


            //         if (task.getStartHour()<=hour){ // redundant if statement but just double checks that the task ha

            //             if(time+task.getDuration()<=60){ // if there is no overflow into the other hour then...
            //                 if(hour+task.getDuration()/60 <task.getMaxWindow()+task.getStartHour()){ // checks to make sure you can finish in time
                        
                            

            //                 task.startTime= String.valueOf(hour) + ":" + String.valueOf(time%60); // sets the start time
            //                 task.endTime= String.valueOf(hour+ task.getDuration()%60) + ":" + String.valueOf(time%60+task.getDuration()); // sets the end time
            //                 time+=task.getDuration(); // increments the minutes of the current time by the duration of the task

            //                 scheduleAnimalTasks2.add(task); // adds the scheduled task into the list of scheduled tasks

            //                 animalTasks.remove(task);  // removes the task from the list of tasks that need to be scheduled
            //                 spillOver=0;
            //             }}
                        
            //         }

            //         else if(time+task.getDuration()>60){ // if there is overflow into the next hour
            //             if(hour+task.getDuration()/60 <task.getMaxWindow()+task.getStartHour()){ // if it can still finish in time
                            
            //             task.startTime= String.valueOf(hour) + ":" + String.valueOf(time%60); // start time is the current hour and the current time
            //             hour+= task.getDuration()/60; // increments the hour by the number of hours the task takes
            
            //             task.endTime= String.valueOf(hour) + ":" + String.valueOf((time%60+task.getDuration())%60); // increments the time by the duration of the task
                       
            //             time+=task.getDuration(); // increments the time by the duration of the task

            //             scheduleAnimalTasks2.add(task); // adds the schedule removes from treeset of event that need to be scheduled
            //             animalTasks.remove(task);
            //             spillOver= time-60;
                           
            //             }}
                    
            //     }

            // time=spillOver;
            // hour+=1;

                
            // }}





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