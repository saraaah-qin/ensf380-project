package edu.ucalgary.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ScheduleGui extends JFrame implements ActionListener {
    private JLabel label;
    private JButton button;
    private Schedule schedule;
    private JTextArea resultTextArea;
    private JScrollPane scrollPane;

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
        schedule = new Schedule();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            String result = schedule.generateSchedule();
            Scanner search = new Scanner(result);
            String msg = "";
            boolean backup = false;
            while(search.hasNextLine()){
                String line = search.nextLine();
                if(line.contains("[+ backup volunteer]")){
                    backup = true;
                    String[] data = line.split("\\s+");
                    msg += data[0] + " ";
                }
            }
            if(backup){
                String warning = "Confirm backups have been contacted for these times: " + msg;
                JOptionPane.showMessageDialog(this, warning);
            }


            if(result.startsWith("No schedule possible")){

            }

            JOptionPane.showMessageDialog(this, "Schedule generated123");
            resultTextArea.setText(result);

            System.out.println(result);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("schedule.txt"))) {
                writer.write(result);
                writer.newLine();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ScheduleGui gui = new ScheduleGui();
        gui.setVisible(true);
    }

}
