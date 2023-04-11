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

        JTextArea textArea = new JTextArea("To modify the schedule, enter the new time and the task index\n for each modification into the input box next to the Modify button. \n" +
        "Indexes start from 0 at the top. The format for modifying a task is\nhh:mm <taskindex> \neach modification should be" +
        " in a new line.");
        textArea.setEditable(false);
        JScrollPane txtscrollPane = new JScrollPane(textArea);
        txtscrollPane.setPreferredSize(new Dimension(400, 200));
        add(txtscrollPane);
        setVisible(true);

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
            String thesched = schedule.generateSchedule();
            String[] theschedsplit = thesched.split("\n");
            String modifyText = modifyTextArea.getText();
            String[] mods = modifyText.split("\n");
            for(String mod : mods){
                String[] modsplit = mod.split("\\s+");
                int startHour = Integer.parseInt(modsplit[0].substring(0, modsplit[0].indexOf(":")));
                int startMinute = Integer.parseInt(modsplit[0].substring(modsplit[0].indexOf(":") + 1));
                int taskindex = Integer.parseInt(modsplit[1]);
                theschedsplit[taskindex] = startHour + ":" + startMinute + theschedsplit[taskindex].substring(5);
                int i;
                for(i = 1; i < theschedsplit.length; i++){
                    String[] current = theschedsplit[i].split(":");
                    int curnum = Integer.parseInt(current[0]) * 60 + Integer.parseInt(current[1].substring(0, 2));
                    String[] prev = theschedsplit[i-1].split(":");
                    int prevnum = Integer.parseInt(prev[0]) * 60 + Integer.parseInt(prev[1].substring(0, 2));
                    String[] newtime = theschedsplit[taskindex].split(":");
                    int newnum = Integer.parseInt(newtime[0]) * 60 + Integer.parseInt(newtime[1].substring(0,2));
                    if(newnum > prevnum && newnum < curnum){
                        String[] newschedsplit = new String[theschedsplit.length + 1];
                        System.arraycopy(theschedsplit, 0, newschedsplit, 0, i);
                        newschedsplit[i] = theschedsplit[taskindex];
                        System.arraycopy(theschedsplit, i, newschedsplit, i+1, theschedsplit.length - i);
                        theschedsplit = newschedsplit;
                        break;
                    }
                }
                if(theschedsplit.length == i){
                    String[] newschedsplit = new String[theschedsplit.length + 1];
                    System.arraycopy(theschedsplit, 0, newschedsplit, 0, i);
                    newschedsplit[i] = theschedsplit[taskindex];
                    theschedsplit = newschedsplit;
                }
            }
            String result = String.join("\n", theschedsplit);
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
