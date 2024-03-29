/**
 * @author David Rodriguez Barrios
 * @author Sarah Qin
 * @version 14.0
 * @since 1.0
 */

/**
 * This class is used to create the schedule of the animal tasks.
 * It also contains the methods to generate the schedule, get the schedule, and get the left over tasks.
 * Uses the MySQL database to get the animal tasks. Additionally uses List, ArrayList, and HashMap to organize the animal tasks.
 */

package edu.ucalgary.oop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Schedule {
    private boolean initialized = true;
    private ArrayList<AnimalTask> animalTasks = new ArrayList<AnimalTask>();
    private List<AnimalTask> scheduleAnimalTasks = new ArrayList<>(); // primary scheduled list
    private List<AnimalTask> scheduleAnimalTasks2 = new ArrayList<>(); // secondary scheduled list for the
                                                                       // assistant
    private List<AnimalTask> leftOverTasks = new ArrayList<>(); // Tertiary scheduled list for the animal tasks
                                                                // that are not able to be scheduled

    /**
     * This is the generateSchedule method is called to generate the schedule.
     * Constructs the schedule.
     */
    public Schedule() {
        generateSchedule();
    }

    /*
     * This method is used to generate the schedule of the animal tasks. First
     * creates a hashmap used as an animalFeedingPrepTable.
     * Then it creates a connection to the MySQL database. Creates statements to
     * extract the animalQuery. Then it creates a hashmap for the animal objects.
     * Uses the animalQuery to construct the animal objects and add them to the
     * hashmap. Then it creates a statement to extract the tasksQuery. Uses the
     * tasksQuery
     * to construct the animal tasks. It then sorts the animal tasks by max window
     * and closes the connection to the database. Then it calls the sortAnimalTasks
     * method which sorts by max window.
     * It then iterates through the AnimalTask list and checks if the animal task
     * can be scheduled with the given times. Finally adds all results to
     * an array list of strings called scheduleOutput while also incrementing hours.
     */
    public void generateSchedule() {
        Map<Integer, boolean[]> animalFeedingPrepTable = new HashMap<>();
        for (int i = 0; i < 24; i++) {
            animalFeedingPrepTable.put(i, new boolean[] { false, false });
        }

        try {
            if (true) {

                Class.forName("com.mysql.cj.jdbc.Driver");

                Connection myConnect = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/EWR", "oop",
                        "password");
                Statement statement = myConnect.createStatement();
                ResultSet animalQuery = statement.executeQuery("SELECT * FROM animals");

                // create a hashmap for animal objects so that we can easily access them by
                // animalID

                Map<Integer, Animal> animals = new HashMap<>();

                while (animalQuery.next()) {
                    Animal animal = new Animal(animalQuery.getInt("AnimalID"), animalQuery.getString("AnimalNickname"),
                            animalQuery.getString("AnimalSpecies"));
                    animals.put(animalQuery.getInt("AnimalID"), animal);
                }
                animalQuery.close();

                ResultSet tasksQuery = statement.executeQuery(
                        "SELECT TREATMENTS.AnimalID, TREATMENTS.TaskID, TREATMENTS.StartHour, TASKS.MaxWindow, TASKS.Description, TASKS.Duration, TREATMENTS.TreatmentID "
                                +
                                "FROM TREATMENTS " +
                                "JOIN TASKS ON TREATMENTS.TaskID = TASKS.TaskID " +
                                "JOIN ANIMALS ON TREATMENTS.AnimalID = ANIMALS.AnimalID " +
                                "ORDER BY (TASKS.MaxWindow) ASC,  TASKS.Duration DESC, TREATMENTS.StartHour ASC;");

                while (tasksQuery.next()) {
                    AnimalTask temporaryAdding = new AnimalTask(animals.get(tasksQuery.getInt("AnimalID")),
                            tasksQuery.getString("Description"),
                            tasksQuery.getInt("StartHour"), tasksQuery.getInt("TaskID"), tasksQuery.getInt("MaxWindow"),
                            tasksQuery.getInt("Duration"));
                    temporaryAdding.setTreatmentID(tasksQuery.getInt("TreatmentID"));
                    System.out.println(tasksQuery.getInt("TreatmentID"));
                    animalTasks.add(temporaryAdding);

                }

                tasksQuery.close();
                int uniqueTaskID = 0;
                for (Animal animalFeedingTaskCreation : animals.values()) {
                    animalTasks.add(new AnimalTask(animalFeedingTaskCreation, "FEEDING ANIMALS",
                            animalFeedingTaskCreation.getAnimalSpecies().getAnimalType().getFeedingTimes()[0],
                            11111 + uniqueTaskID, 3,
                            animalFeedingTaskCreation.getAnimalSpecies().getFeedingTime()));
                    uniqueTaskID += 1;

                }
                // first sort the animal tasks by max window
                int japan = 1;
                for (Map.Entry<Integer, Animal> entry : animals.entrySet()) {
                    Animal animal = entry.getValue();
                    AnimalSpecies animalSpecies = animal.getAnimalSpecies();
                    int duration = animalSpecies.getCleanTime();
                    animalTasks.add(new AnimalTask(animal, "Cage Cleaning", 0, -999999 + japan, 24, duration));
                    japan += 1;
                }

                initialized = false;
                myConnect.close();
            }

            sortAnimalTasks(); // sorts based off MW
            int hour = 0;
            int time = 0;

            while (hour < 24) {
                Iterator<AnimalTask> iterator = animalTasks.iterator();

                while (iterator.hasNext()) {
                    AnimalTask task = iterator.next();
                    int expectedTime = 0;

                    if (time >= 60) {
                        break;
                    }
                    int indexForSearch = -1;
                    if (task.getDescription().equals("FEEDING ANIMALS")) {
                        if (task.getAnimal().getAnimalSpecies().getAnimalSpeciesString().equals("coyote")) {
                            indexForSearch = 0;
                            if (animalFeedingPrepTable.get(hour)[indexForSearch] == false) {

                                expectedTime = task.getAnimal().getAnimalSpecies().getPrepTime();
                            }
                        } else if (task.getAnimal().getAnimalSpecies().getAnimalSpeciesString().equals("fox")) {
                            indexForSearch = 1;
                            if (animalFeedingPrepTable.get(hour)[indexForSearch] == false) {

                                expectedTime = task.getAnimal().getAnimalSpecies().getPrepTime();
                            }
                        }

                    }

                    if ((task.getStartHour() <= hour) && (time + task.getDuration() + expectedTime <= 60) && (hour
                            + (task.getDuration() + expectedTime) / 60 < task.getMaxWindow() + task.getStartHour())) {
                        task.setStartTime(LocalTime.of(hour, time % 60));
                        task.setEndTime(LocalTime.of((hour + (time + task.getDuration() + expectedTime) / 60) % 24,
                                (time + task.getDuration() + expectedTime) % 60));
                        time += task.getDuration() + expectedTime;
                        scheduleAnimalTasks.add(task);
                        if (task.getDescription().equals("FEEDING ANIMALS") && (task.getAnimal().getAnimalSpecies()
                                .getAnimalSpeciesString().equals("coyote")
                                || task.getAnimal().getAnimalSpecies().getAnimalSpeciesString().equals("fox"))) {

                            animalFeedingPrepTable.get(hour)[indexForSearch] = true;
                        }
                        iterator.remove();

                    }
                }
                time = 0;
                hour += 1;
            }

            if (animalTasks.size() > 0) {

                hour = 0;

                time = 0;

                while (hour < 24) {
                    Iterator<AnimalTask> iterator2 = animalTasks.iterator();

                    while (iterator2.hasNext()) {
                        AnimalTask task = iterator2.next();
                        int expectedTime = 0;

                        if (time >= 60) {
                            break;
                        }
                        int indexForSearch = -1;
                        if (task.getDescription().equals("FEEDING ANIMALS")) {
                            if (task.getAnimal().getAnimalSpecies().getAnimalSpeciesString().equals("coyote")) {
                                indexForSearch = 0;
                                if (animalFeedingPrepTable.get(hour)[indexForSearch] == false) {

                                    expectedTime = task.getAnimal().getAnimalSpecies().getPrepTime();
                                }
                            } else if (task.getAnimal().getAnimalSpecies().getAnimalSpeciesString().equals("fox")) {
                                indexForSearch = 1;
                                if (animalFeedingPrepTable.get(hour)[indexForSearch] == false) {

                                    expectedTime = task.getAnimal().getAnimalSpecies().getPrepTime();
                                }
                            }
                        }
                        if ((task.getStartHour() <= hour) && (time + task.getDuration() + expectedTime <= 60)
                                && (hour + (task.getDuration() + expectedTime) / 60 < task.getMaxWindow()
                                        + task.getStartHour())) {
                            task.setStartTime(LocalTime.of(hour, time % 60));
                            task.setEndTime((LocalTime.of((hour + (time + task.getDuration() + expectedTime) / 60) % 24,
                                    (time + task.getDuration() + expectedTime) % 60)));
                            time += task.getDuration() + expectedTime;
                            scheduleAnimalTasks2.add(task);
                            if (task.getDescription().equals("FEEDING ANIMALS") && (task.getAnimal().getAnimalSpecies()
                                    .getAnimalSpeciesString().equals("coyote")
                                    || task.getAnimal().getAnimalSpecies().getAnimalSpeciesString().equals("fox"))) {

                                animalFeedingPrepTable.get(hour)[indexForSearch] = true;
                            }
                            iterator2.remove();

                        }
                    }
                    time = 0;
                    hour += 1;
                }
            }

            List<String> scheduleOutput = new ArrayList<String>();

            if (animalTasks.size() > 0) {
                System.out.println("No schedule possible");
                System.out.println("There is no space for the volunteer and Sara to do the following task(s):");
                for (AnimalTask task : animalTasks) {
                    leftOverTasks.add(task);
                    scheduleOutput.add("* " + task.getStartTime() + " " + task.getDescription() + "("
                            + task.getAnimal().getAnimalNickname() + ")\n");
                }

            }

            int lastOneDone = -5;
            int currentHour = 0;
            boolean help = false;
            while (currentHour <= 23) {
                help = false;
                for (AnimalTask task : scheduleAnimalTasks2) {
                    if (task.getStartTime().getHour() == currentHour) {
                        help = true;
                    }
                }
                for (AnimalTask task : scheduleAnimalTasks) {
                    if (task.getStartTime().getHour() == currentHour) {
                        if (currentHour != lastOneDone) {
                            if (help) {
                                scheduleOutput.add(currentHour + ":00 [+ back up volunteer]\n");

                            } else {
                                scheduleOutput.add(currentHour + ":00\n");
                            }
                            lastOneDone = currentHour;
                        }

                        scheduleOutput.add("* " + task.getStartTime() + " " + task.getDescription() + "("
                                + task.getAnimal().getAnimalNickname()
                                + ")\n");
                    }
                }

                for (AnimalTask task : scheduleAnimalTasks2) {
                    if (task.getStartTime().getHour() == currentHour) {

                        scheduleOutput.add("* " + task.getStartTime() + " " + task.getDescription() + "("
                                + task.getAnimal().getAnimalNickname()
                                + ")\n");
                    }
                }
                currentHour += 1;
            }

        } catch (Exception e) {
            List<String> error = new ArrayList<String>();
            error.add(e.getMessage());
        }
    }

    /**
     * This method sorts the animal tasks by the max window. Overrides the compare
     * method.
     */
    public void sortAnimalTasks() {
        Collections.sort(this.animalTasks, new Comparator<AnimalTask>() {
            @Override
            public int compare(AnimalTask thing1, AnimalTask thing2) {
                return thing1.getMaxWindow() - thing2.getMaxWindow();
            }

        });
    }

    /**
     * This method returns a boolean value to indicate initialization status.
     * 
     * @return boolean
     */
    public boolean isInitialized() {
        return initialized;
    }

    /**
     * This method gets an ArrayList of AnimalTasks.
     */
    public ArrayList<AnimalTask> getAnimalTasks() {
        return animalTasks;
    }

    /**
     * This method gets an List of AnimalTasks.
     */
    public List<AnimalTask> getScheduleAnimalTasks() {
        return scheduleAnimalTasks;
    }

    /**
     * This method gets an List of AnimalTasks2.
     */
    public List<AnimalTask> getScheduleAnimalTasks2() {
        return scheduleAnimalTasks2;
    }

    /**
     * This method gets a List of left over AnimalTasks.
     */
    public List<AnimalTask> getLeftOverTasks() {
        return leftOverTasks;
    }

    /**
     * This method sets the animalTasks.
     * 
     * @param animalTasks
     */

    public void setAnimalTasks(ArrayList<AnimalTask> animalTasks) {
        this.animalTasks = animalTasks;
    }

    /**
     * This method sets the scheduleanimalTasks.
     * 
     * @param scheduleAnimalTasks
     */
    public void setScheduleAnimalTasks(List<AnimalTask> scheduleAnimalTasks) {
        this.scheduleAnimalTasks = scheduleAnimalTasks;
    }

    /**
     * This method sets the scheduleanimalTasks2.
     * 
     * @param scheduleAnimalTasks2
     */
    public void setScheduleAnimalTasks2(List<AnimalTask> scheduleAnimalTasks2) {
        this.scheduleAnimalTasks2 = scheduleAnimalTasks2;
    }

    /**
     * This method sets the leftOverTasks.
     * 
     * @param leftOverTasks
     */
    public void setLeftOverTasks(List<AnimalTask> leftOverTasks) {
        this.leftOverTasks = leftOverTasks;
    }
}
