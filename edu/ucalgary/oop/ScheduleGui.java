/**
 * @author Sarah Qin
 * @author Aiden Wong
 * @author David Rodriguez Barrios
 * @version 3.0
 * @since 1.0
 */
/**
 * This file acts as the java GUI for the schedule and contains the main that runs the program by calling the ScheduleGui.
 * It contains the ScheduleGui class that extends JFrame and implements ActionListener. Sets the layout and buttons along with the 
 * action listeners for the buttons. Uses text areas to display the results. Overides the actionPerformed method. Gets the schedule output and 
 * writes it to a text file. Also uses string builder to make the results a string.
 */

package edu.ucalgary.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class ScheduleGui extends JFrame implements ActionListener {
    private JLabel label;
    private JButton button;

    private JTextArea resultTextArea;
    private JScrollPane scrollPane;
    private Schedule schedule = new Schedule();
    private List<String> results = new ArrayList<>();
    List<AnimalTask> animalTasks = schedule.getAnimalTasks();

    List<AnimalTask> scheduleAnimalTasks = schedule.getScheduleAnimalTasks();
    List<AnimalTask> scheduledAnimalTasks2 = schedule.getScheduleAnimalTasks2();

    List<AnimalTask> leftOver = schedule.getLeftOverTasks();

    /**
     * This is the constructor for the ScheduleGui class. Sets the title, size,
     * default close operation, and location of the GUI.
     * Sets the layout and adds the label, button, and scroll pane to the GUI.
     */
    public ScheduleGui() {

        setTitle("Generate Schedule");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        label = new JLabel("Generate Schedule");
        button = new JButton("Generate Schedule");
        resultTextArea = new JTextArea(50, 50);
        scrollPane = new JScrollPane(resultTextArea);

        setLayout(new FlowLayout());

        add(label);
        add(button);
        add(scrollPane);

        button.addActionListener(this);

    }

    /**
     * This method is used to get the schedule output and write it to a text file.
     * Overides the actionPerformed method.
     * 
     * @param e which is an ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == button) {

            Map<Integer, Integer> timeLeft = new HashMap<>();

            for (int i = 0; i <= 23; i++) {
                timeLeft.put(i, 0);
            }
            for (AnimalTask task : scheduleAnimalTasks) {
                timeLeft.put(task.getStartTime().getHour(),
                        timeLeft.get(task.getStartTime().getHour()) + task.getDuration());
            }

            Map<Integer, Integer> timeLeft1 = new HashMap<>();
            for (int i = 0; i <= 23; i++) {
                timeLeft1.put(i, 0);
            }

            if (scheduledAnimalTasks2.size() > 0) {
                for (AnimalTask task : scheduledAnimalTasks2) {
                    timeLeft1.put(task.getStartTime().getHour(),
                            timeLeft1.get(task.getStartTime().getHour()) + task.getDuration());
                }
            }

            if (scheduledAnimalTasks2.size() > 0) { // if you will require a volunteer/ rescheduling

                JFrame frame = new JFrame("Backup Prompt");

                int choice = JOptionPane.showOptionDialog(frame,
                        "A backup volunteer is neeed, do you want to call a backup?",
                        "Backup Prompt",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new String[] { "Yes", "No" },
                        "Yes");

                // if they are ok with having a volunteer

                if (choice == JOptionPane.YES_OPTION) { // if they allow a volunteer check only if there is stuff
                                                        // leftOver even after volunteer
                    for (AnimalTask task : leftOver) {
                        List<Integer> validHours = new ArrayList<>();
                        StringBuilder sb = new StringBuilder();

                        for (Integer hour : timeLeft.keySet()) { // give list of hours
                            if (timeLeft.get(hour) + task.getDuration() <= 60) {
                                validHours.add(hour);

                            }
                        }

                        for (Integer hour : timeLeft1.keySet()) {
                            if (timeLeft1.get(hour) + task.getDuration() <= 60) {
                                validHours.add(hour);
                            }
                        }

                        for (Integer hour : validHours) {
                            sb.append(hour);
                            sb.append(", ");
                        }
                        String hours = sb.toString();
                        String newChange = JOptionPane
                                .showInputDialog("Please change the start time of the task for "
                                        + task.getAnimal().getAnimalNickname() + task.getDescription()
                                        + "that originally start at " + task.getStartHour()
                                        + " and had a MaxWindow of " + task.getMaxWindow()
                                        + ". We suggest this time: " + hours);
                        int hour = Integer.parseInt(newChange);
                        if (validHours.contains(hour) == false) {
                            JOptionPane.showMessageDialog(null, "Please enter a valid hour");
                            throw new IllegalArgumentException("Please enter a valid hour");
                        }
                        if (timeLeft.get(hour) + task.getDuration() <= 60) {
                            timeLeft.put(hour, timeLeft.get(hour) + task.getDuration());
                            task.setStartHour(hour);
                            setValue(task.getTreatmentID(), hour);
                            task.setStartTime(LocalTime.of(hour, (timeLeft.get(hour) - task.getDuration()) % 60));
                            task.setEndTime(LocalTime.of(hour, timeLeft.get(hour) % 60));
                            scheduleAnimalTasks.add(task);
                            ;
                        }

                        else {

                            timeLeft1.put(hour, timeLeft1.get(hour) + task.getDuration());
                            task.setStartHour(hour);
                            setValue(task.getTreatmentID(), hour);
                            task.setStartTime(LocalTime.of(hour, (timeLeft1.get(hour) - task.getDuration()) % 60));
                            task.setEndTime(LocalTime.of(hour + (timeLeft1.get(hour)) / 60, timeLeft1.get(hour) % 60));
                            scheduledAnimalTasks2.add(task);
                        }

                        // update time left in timeLeft
                        // put the task in scheduledAnimalTasks2 or scheduleAnimalTasks
                    }
                    List<String> scheduleOutput = new ArrayList<>();
                    int lastOneDone = -5;
                    int currentHour = 0;
                    boolean help = false;
                    while (currentHour <= 23) {
                        help = false;
                        for (AnimalTask task : scheduledAnimalTasks2) {
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

                                scheduleOutput
                                        .add("* " + task.getStartTime() + " " + task.getDescription() + "("
                                                + task.getAnimal().getAnimalNickname()
                                                + ")\n");
                            }
                        }

                        for (AnimalTask task : scheduledAnimalTasks2) {
                            if (task.getStartTime().getHour() == currentHour) {

                                scheduleOutput
                                        .add("* " + task.getStartTime() + " " + task.getDescription() + "("
                                                + task.getAnimal().getAnimalNickname()
                                                + ")\n");
                            }
                        }
                        currentHour += 1;
                    }
                    results = scheduleOutput;

                } else {

                    // if they don't want a volunteer then this is what will prompt since they will
                    // have to reschedule Schedule2 and LeftOver

                    for (AnimalTask task : leftOver) {
                        List<Integer> validHours = new ArrayList<>();
                        StringBuilder sb = new StringBuilder();
                        for (Integer hour : timeLeft.keySet()) { // give list of hours
                            if (timeLeft.get(hour) + task.getDuration() <= 60) {
                                validHours.add(hour);
                            }
                        }

                        for (Integer hour : validHours) {
                            sb.append(hour);
                            sb.append(", ");
                        }

                        String hours = sb.toString();
                        String newChange = JOptionPane
                                .showInputDialog("Please change the start time of the task for "
                                        + task.getAnimal().getAnimalNickname() + task.getDescription()
                                        + "that originally start at " + task.getStartHour()
                                        + " and had a MaxWindow of " + task.getMaxWindow()
                                        + ". We suggest this time: " + hours);
                        int hour = Integer.parseInt(newChange);
                        if (validHours.contains(hour) == false) {
                            JOptionPane.showMessageDialog(null, "Please enter a valid hour");
                            throw new IllegalArgumentException("Please enter a valid hour");
                        }
                        timeLeft.put(hour, timeLeft.get(hour) + task.getDuration());
                        task.setStartHour(hour);
                        setValue(task.getTreatmentID(), hour);
                        task.setStartTime(LocalTime.of(hour, (timeLeft.get(hour) - task.getDuration()) % 60));
                        task.setEndTime(LocalTime.of(hour + (timeLeft.get(hour) / 60), timeLeft.get(hour) % 60));
                        scheduleAnimalTasks.add(task);

                        // update time left in timeLeft
                        // put the task in scheduledAnimalTasks2 or scheduleAnimalTasks

                    }
                    // scheduledAnimalTasks2.remove(task);
                    List<AnimalTask> temp = new ArrayList<>();
                    for (AnimalTask task : scheduledAnimalTasks2) {
                        List<Integer> validHours = new ArrayList<>();
                        StringBuilder sb = new StringBuilder();
                        for (Integer hour : timeLeft.keySet()) { // give list of hours
                            if (timeLeft.get(hour) + task.getDuration() <= 60) {
                                validHours.add(hour);
                            }
                        }
                        for (Integer hour : validHours) {
                            sb.append(hour);
                            sb.append(", ");
                        }
                        String hours = sb.toString();
                        String newChange = JOptionPane
                                .showInputDialog("Please change the start time of the task for "
                                        + task.getAnimal().getAnimalNickname() + task.getDescription()
                                        + "that originally start at " + task.getStartHour()
                                        + " and had a MaxWindow of " + task.getMaxWindow()
                                        + ". We suggest this time: " + hours);
                        int hour = Integer.parseInt(newChange);
                        if (validHours.contains(hour) == false) {
                            JOptionPane.showMessageDialog(null, "Please enter a valid hour");
                            throw new IllegalArgumentException("Please enter a valid hour");
                        }
                        timeLeft.put(hour, timeLeft.get(hour) + task.getDuration());
                        task.setStartHour(hour);
                        setValue(task.getTreatmentID(), hour);
                        task.setStartTime(LocalTime.of(hour, (timeLeft.get(hour) - task.getDuration() % 60)));
                        task.setEndTime(LocalTime.of(hour + (timeLeft.get(hour) / 60), timeLeft.get(hour) % 60));
                        scheduleAnimalTasks.add(task);
                        temp.add(task);

                        // update time left in timeLeft
                        // put the task in scheduledAnimalTasks2 or scheduleAnimalTasks
                    }
                    for (AnimalTask task : temp) {
                        scheduledAnimalTasks2.remove(task);
                    }

                }

            }
        }

        // THIS IS IF EVERYTHING IS FINE THEN IT PRINTS THE SCHEDULE
        // JUST PRINTS
        JOptionPane.showMessageDialog(this, "Schedule generated");
        int lastOneDone = -5;
        int currentHour = 0;
        boolean help = false;
        List<String> scheduleOutput = new ArrayList<>();
        while (currentHour <= 23) {
            help = false;
            for (AnimalTask task : scheduledAnimalTasks2) {
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

            for (AnimalTask task : scheduledAnimalTasks2) {
                if (task.getStartTime().getHour() == currentHour) {

                    scheduleOutput.add("* " + task.getStartTime() + " " + task.getDescription() + "("
                            + task.getAnimal().getAnimalNickname()
                            + ")\n");
                }
            }
            currentHour += 1;
        }
        results = scheduleOutput;
        StringBuilder sb = new StringBuilder();
        for (String s : results) {
            sb.append(s);
        }
        String result = sb.toString();
        resultTextArea.setText(result);
        System.out.println(result);
        try (
                BufferedWriter writer = new BufferedWriter(new FileWriter("schedule.txt"))) {
            writer.write(result);
            writer.newLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * This method is used to update the value of a column in the database.
     * 
     * @param treatmentID the treatmentID of the treatment
     * @param column      the column to get the value from
     * @return int the value of the column
     */

    public void setValue(int treatmentID, int newStartHour) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection myConnect = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/EWR", "oop",
                    "password");
            Statement statement = myConnect.createStatement();
            statement.executeUpdate("UPDATE TREATMENTS SET StartHour = " + newStartHour + " WHERE TreatmentID = "
                    + treatmentID + ";");

            myConnect.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * This is the main method. Used to create a new ScheduleGui object and set it
     * to visible.
     * 
     * @param args the command line arguments
     * @return void
     */
    public static void main(String[] args) {
        ScheduleGui gui = new ScheduleGui();
        gui.setVisible(true);
    }

}