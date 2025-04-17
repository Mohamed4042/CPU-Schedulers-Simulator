package FCAI;

import Main.Process;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class FCAI {
    private ArrayList<Process> processes; // List of all processes
    private Queue<Process> readyQueue; // Ready queue for process execution
    private ArrayList<String> executionLog; // Log for execution history
    private int totalTime; // Total elapsed time
    private int contextSwitching; // Context switching time
    private float v1, v2; // Factors for FCAI calculation

    // Constructor
    public FCAI(int contextSwitching) {
        this.processes = new ArrayList<>();
        this.readyQueue = new LinkedList<>();
        this.executionLog = new ArrayList<>();
        this.totalTime = 0;
        this.contextSwitching = contextSwitching;
    }

    // Add processes to the process list
    public void addProcesses(ArrayList<Process> processes) {
        this.processes.addAll(processes);
    }

    // Calculate V1 and V2 factors
    private void calculateFactors() {
        int lastArrivalTime = processes.stream()
                .mapToInt(Process::getArrivalTime)
                .max()
                .orElse(0);
        int maxBurstTime = processes.stream()
                .mapToInt(Process::getBurstTime)
                .max()
                .orElse(0);
        this.v1 = (float) Math.ceil(lastArrivalTime / 10.0);
        this.v2 = (float) Math.ceil(maxBurstTime / 10.0);
    }

    // Schedule processes using FCAI Scheduling
    public void schedule() {
        calculateFactors();

        // Process scheduling
        while (processes.stream().anyMatch(p -> p.getRemainingBurstTime() > 0)) {
            // Add processes to the ready queue based on arrival time
            for (Process process : processes) {
                if (process.getArrivalTime() <= totalTime && process.getRemainingBurstTime() > 0 && !readyQueue.contains(process)) {
                    readyQueue.add(process);
                }
            }

            // Sort ready queue by FCA Factor
            ArrayList<Process> sortedQueue = new ArrayList<>(readyQueue);
            sortedQueue.sort(Comparator.comparingDouble(Process::getFCAIFactor));
            readyQueue = new LinkedList<>(sortedQueue);

            if (!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.poll();
                int maxExecutionTime = (int) Math.ceil(currentProcess.getQuantum() * 0.4); // Max execution for first 40% of quantum
                int executionTime = Math.min(maxExecutionTime, currentProcess.getRemainingBurstTime());

                // Update execution log
                executionLog.add("Time " + totalTime + "-" + (totalTime + executionTime) + ": " + currentProcess.getName() + " executed");

                // Update total time and process burst time
                totalTime += executionTime + contextSwitching;
                currentProcess.reduceBurstTime(executionTime);

                // Update quantum based on execution
                if (currentProcess.getRemainingBurstTime() > 0) {
                    if (executionTime == currentProcess.getQuantum()) {
                        currentProcess.setQuantum(currentProcess.getQuantum() + 2); // Increase quantum if completed quantum
                    } else {
                        currentProcess.setQuantum(currentProcess.getQuantum() + executionTime); // Add unused quantum back
                    }
                    readyQueue.add(currentProcess); // Re-add non-completed process
                } else {
                    currentProcess.setTurnaroundTime(totalTime - currentProcess.getArrivalTime());
                    currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getBurstTime());
                }
            } else {
                totalTime++; // Increment time if no process is ready
            }
        }
    }

    // Print scheduling results
    public void printResults() {
        System.out.println("Execution Log:");
        executionLog.forEach(System.out::println);

        int totalWaitingTime = 0, totalTurnaroundTime = 0;

        System.out.println("\nProcess Details:");
        for (Process process : processes) {
            System.out.println(process.getName() + ": Waiting Time = " + process.getWaitingTime() +
                    ", Turnaround Time = " + process.getTurnaroundTime());
            totalWaitingTime += process.getWaitingTime();
            totalTurnaroundTime += process.getTurnaroundTime();
        }

        System.out.println("\nAverage Waiting Time: " + (float) totalWaitingTime / processes.size());
        System.out.println("Average Turnaround Time: " + (float) totalTurnaroundTime / processes.size());
    }
}