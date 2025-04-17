package shortestJobFirst;

import Main.Process;
import Main.duration;

import java.util.ArrayList;
import java.util.Comparator;

public class shortestJobFirst {

    public ArrayList<duration> start(ArrayList<Process> processes, int age) {

        ArrayList<duration> durations = new ArrayList<>();
        processes.sort(new SJFComparator());  // Sort the processes based on burst time and arrival time.

        int time = processes.get(0).arrivalTime;  // Start time is the arrival time of the first process.
        while (!processes.isEmpty()) {
            int shortest = -1;
            Process selectedProcess = null;

            // Iterate over processes to find the shortest job
            for (int j = 0; j < processes.size(); j++) {
                Process process = processes.get(j);

                // Only consider processes that have arrived by the current time
                if (process.arrivalTime <= time) {
                    if (selectedProcess == null || process.burstTime < selectedProcess.burstTime) {
                        selectedProcess = process;
                        shortest = j;
                    }
                }
            }

            // If a valid process is found
            if (shortest != -1 && selectedProcess != null) {
                // Add the process to the durations list
                durations.add(new duration(
                        selectedProcess.Name,
                        time,
                        time + selectedProcess.burstTime,
                        selectedProcess.getPID(),
                        "Done",
                        selectedProcess.burstTime,
                        selectedProcess.arrivalTime
                ));

                // Update the time after the process has completed
                time += selectedProcess.burstTime;

                // Remove the selected process from the list
                processes.remove(shortest);
            }
        }
        return durations;
    }

    // Comparator to sort by burst time and arrival time
    public static class SJFComparator implements Comparator<Process> {
        @Override
        public int compare(Process o1, Process o2) {
            // Sort by burst time first, then by arrival time if burst times are equal
            if (o1.burstTime != o2.burstTime) {
                return o1.burstTime - o2.burstTime;  // Ascending order
            }
            return o1.arrivalTime - o2.arrivalTime;  // If burst times are the same, sort by arrival time
        }
    }
}
