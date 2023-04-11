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
    private JButton modifybutton;
    private Schedule schedule;
    private JTextArea resultTextArea;
    private JTextArea modifyTextArea;
    private JScrollPane scrollPane;
    private JScrollPane modscrollPane;

    public ScheduleGui() {
        setTitle("Generate Schedule");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        label = new JLabel("Generate Schedule");
        button = new JButton("Generate Schedule");
        modifybutton = new JButton("Modify Schedule");
        modifyTextArea = new JTextArea(5, 15);
        resultTextArea = new JTextArea(50, 50);
        scrollPane = new JScrollPane(resultTextArea);
        modscrollPane = new JScrollPane(modifyTextArea);

        setLayout(new FlowLayout());

        add(label);
        add(button);
        add(scrollPane);
        add(modscrollPane);
        add(modifybutton);

        button.addActionListener(this);
        modifybutton.addActionListener(this);
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

            // need to implement modifying the start hour of one or more treatments based on advice she receives
            if(result.startsWith("No schedule possible")){
                String modmsg = "Schedule is not possible, contact staff vet";
                JOptionPane.showMessageDialog(this, modmsg);
                resultTextArea.setText(result);
                System.out.println(result);
            }
            else{
                JOptionPane.showMessageDialog(this, "Schedule generated");
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
        else if(e.getSource() == modifybutton){
            String modifyText = modifyTextArea.getText();
            String[] mods = modifyText.split("\n");
            for(String mod : mods){
                String[] modsplit = mod.split("\\s+");
                int startHour = Integer.parseInt(modsplit[0].substring(0, modsplit[0].indexOf(":")));
                int taskindex = Integer.parseInt(modsplit[1]);
                // Need to figure out the method set up in order to change the hour for a given task here using the setters. 
                // Just having trouble figuring out the right way the setters interact between methods
            }
            String result = schedule.generateSchedule();
            resultTextArea.setText(result);
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
