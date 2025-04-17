
package Main;
import java.util.ArrayList;
import java.util.Scanner;

import FCAI.FCAI;
import SRTF.SRTF;
import shortestJobFirst.shortestJobFirst;// Ensure this import is added
import PriorityScheduling.PriorityScheduling;


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read number of processes
        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();

        // List to store processes
        ArrayList<Process> processes = new ArrayList<>();

        // Read process details
        for (int i = 0; i < numProcesses; i++) {
            System.out.println("Enter details for Process " + (i + 1));

            System.out.print("Process Name: ");
            String name = scanner.next();
            System.out.print("Process Color (Graphical Representation): ");
            String color = scanner.next();

            System.out.print("Arrival Time: ");
            int arrivalTime = scanner.nextInt();

            System.out.print("Burst Time: ");
            int burstTime = scanner.nextInt();

            System.out.print("Priority Number (lower number = higher priority): ");
            int priorityNumber = scanner.nextInt();

            Process process = new Process(name, color, arrivalTime, burstTime, priorityNumber, 0, i + 1); // Time quantum set to 0 for SJF
            processes.add(process);

        }


        boolean flag = true;
        while(flag) {
            // Scheduling Menu
            System.out.println("\nChoose a scheduling algorithm:");
            System.out.println("1. Shortest Job First (SJF)");
            System.out.println("2. Shortest Remaining Time First (SRTF)");
            System.out.println("3. Priority Scheduling");
            System.out.println("4. FCAI Scheduling");
            // Add options for other algorithms here (e.g., Round Robin)
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    shortestJobFirst sjf = new shortestJobFirst();  // No need to use 'shortestJobFirst.shortestJobFirst'
                    System.out.println("\nShortest Job First Scheduling:");
                    ArrayList<duration> sjfDurations = sjf.start(new ArrayList<>(processes), 0); // 0 context switching for simplicity
                    printResults(sjfDurations);

                    // Perform Priority Scheduling

                    break;
                case 2:
                    SRTF srtf = new SRTF();
                    System.out.println("\nSRTF Scheduling:");

                    // Call the SRTF algorithm, passing the processes list
                    ArrayList<duration> srtfDurations = srtf.start(new ArrayList<>(processes), 0, 0); // contextSwitching=2, Age=0 (optional)
                    if (srtfDurations.isEmpty()) {
                        System.out.println("No processes were executed in SRTF scheduling.");
                    } else {
                        printResults(srtfDurations);
                    }

                    // Close the scanner
                    scanner.close();
                    break;
                case 3:
                    PriorityScheduling priority = new PriorityScheduling();
                    System.out.println("\nPriority Scheduling:");
                    ArrayList<duration> priorityDurations = priority.start(new ArrayList<>(processes), 0); // contextSwitching=2
                    printResults(priorityDurations);
                    break;
                case 4:  // Case for FCAI Scheduling
                    System.out.println("\nFCAI Scheduling:");
                    FCAI fcai = new FCAI(0); // Set context switching time (adjust as needed)

                    // Assign unique quantum times to each process
                    for (Process process : processes) {
                        System.out.print("Enter initial quantum for process " + process.getName() + ": ");
                        int quantum = scanner.nextInt();
                        process.setQuantum(quantum);
                    }

                    fcai.addProcesses(processes);
                    fcai.schedule();
                    fcai.printResults();
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }

        scanner.close();
    }

    // Method to print the scheduling results
    public static void printResults(ArrayList<duration> durations) {
        int totalWaitTime = 0;
        int totalTurnaroundTime = 0;

        System.out.println("\nProcess Execution Order:");
        for (duration dur : durations) {
            int waitTime = dur.start - dur.arrivalTime;
            int turnaroundTime = dur.end - dur.arrivalTime;

            System.out.println(dur.name + " | Start: " + dur.start + " | End: " + dur.end + " | Waiting Time: " + waitTime + " | Turnaround Time: " + turnaroundTime);

            totalWaitTime += waitTime;
            totalTurnaroundTime += turnaroundTime;
        }

        System.out.println("\nAverage Waiting Time: " + (totalWaitTime / (double) durations.size()));
        System.out.println("Average Turnaround Time: " + (totalTurnaroundTime / (double) durations.size()));
    }
}

