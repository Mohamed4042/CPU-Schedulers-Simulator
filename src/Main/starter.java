package Main;

import PriorityScheduling.PriorityScheduling;
import SRTF.SRTF;
import shortestJobFirst.shortestJobFirst;

import javax.swing.*;
import java.util.ArrayList;

public class starter {

    public static ArrayList<Process> processes = new ArrayList<>();

    public static void run() {
        PlotWindow Window;
        ArrayList<Process> processes1 = new ArrayList<>();

        for (Process p : processes) {
            processes1.add(p.clone());
        }

        if (GUI.shortestJobFirst.isSelected()) {
            Window = new PlotWindow(
                    "shortestJobFirst",
                    new shortestJobFirst().start(processes1, Integer.parseInt(GUI.Age.getText()))
            );
        } else if (GUI.PriorityScheduling.isSelected()) {
            // Updated PriorityScheduling call to remove the 'age' parameter
            Window = new PlotWindow(
                    "PriorityScheduling",
                    new PriorityScheduling().start(processes1, Integer.parseInt(GUI.Contix.getText()))
            );
        } else if (GUI.SRTF.isSelected()) {
            Window = new PlotWindow(
                    "SRTF",
                    new SRTF().start(
                            processes1,
                            Integer.parseInt(GUI.Contix.getText()),
                            Integer.parseInt(GUI.Age.getText())
                    )
            );
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Please choose a Scheduler.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Window.setVisible(true);
        Window.pack();
        Window.setLocationRelativeTo(null);

        Info info = new Info(Window.type, Window.message);
        info.setVisible(true);
        info.pack();
        info.setLocationRelativeTo(null);
    }
}
//4
//        P1 0 17 4 4
//        P2 3 6 9 3
//        P3 4 10 3 5
//        P4 29 4 8 2
