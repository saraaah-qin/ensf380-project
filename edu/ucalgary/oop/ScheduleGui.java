package edu.ucalgary.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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

    public static void main(String[] args) {
        ScheduleGui gui = new ScheduleGui();
        gui.setVisible(true);
    }

}