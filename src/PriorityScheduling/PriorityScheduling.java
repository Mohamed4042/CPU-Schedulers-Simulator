package PriorityScheduling;

import Main.Process;
import Main.duration;

import java.util.ArrayList;
import java.util.Comparator;

public class PriorityScheduling {


    public ArrayList<duration> start(ArrayList<Process> processes, int contextSwitching) {
        ArrayList<duration> durations = new ArrayList<>();
        processes.sort(new PComparator()); // Sort by arrival time and priority
        int time = 0;  // Start from time 0

        while (!processes.isEmpty()) {
            int selectedIndex = -1;

            // Find the process with the highest priority (lowest priority number)
            for (int j = 0; j < processes.size(); j++) {
                Process p = processes.get(j);

                // Ensure that the process has arrived
                if (p.arrivalTime <= time) {
                    if (selectedIndex == -1 || p.priorityNumber < processes.get(selectedIndex).priorityNumber) {
                        selectedIndex = j;
                    }
                }
            }

            // If no process is eligible, increment time and check again
            if (selectedIndex == -1) {
                time++; // Increment time when no process is ready to run
            } else {
                // Execute the selected process
                Process selectedProcess = processes.get(selectedIndex);

                // Add process execution duration
                durations.add(new duration(
                        selectedProcess.Name,
                        time,
                        time + selectedProcess.burstTime,
                        selectedProcess.getPID(),
                        "Done",
                        selectedProcess.burstTime,
                        selectedProcess.arrivalTime
                ));

                // Update the current time to reflect the process burst time
                time += selectedProcess.burstTime;

                // Add context switching time after each process execution
                time += contextSwitching;

                // Remove the process from the list after execution
                processes.remove(selectedIndex);
            }
        }

        return durations;
    }

    public static class PComparator implements Comparator<Process> {
        @Override
        public int compare(Process o1, Process o2) {
            // Sort by arrival time first
            if (o1.arrivalTime != o2.arrivalTime) {
                return o1.arrivalTime - o2.arrivalTime;
            }
            // If arrival times are equal, sort by priority (lower priorityNumber means higher priority)
            return o1.priorityNumber - o2.priorityNumber;
        }
    }
}
