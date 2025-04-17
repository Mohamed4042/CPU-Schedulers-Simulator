package Main;

public class Process {
    public String Name;
    private String color; // For graphical representation
    public int arrivalTime;
    public int burstTime;
    private int remainingBurstTime; // Remaining burst time for scheduling
    public int priorityNumber;
    private int quantum;
    private int PID;
    private int waitingTime;
    private int turnaroundTime;
    private float fcaFactor; // FCAI Factor for dynamic scheduling
    public int contextSwitching;
    public int age;

    // Constructor
    public Process(String name, String color, int arrivalTime, int burstTime, int priorityNumber, int quantum, int PID) {
        this.Name = name;
        this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingBurstTime = burstTime; // Initially, remaining burst time equals burst time
        this.priorityNumber = priorityNumber;
        this.quantum = quantum;
        this.PID = PID;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
        this.fcaFactor = 0.0f; // Initially set to 0
        this.contextSwitching=0;
        this.age=0;
    }

    // Getter and Setter for Name
    public String getName() {
        return Name;
    }

    // Getter and Setter for Color
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    // Getter and Setter for Arrival Time
    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    // Getter and Setter for Burst Time
    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
        this.remainingBurstTime = burstTime; // Update remaining burst time
    }

    // Getter and Setter for Remaining Burst Time
    public int getRemainingBurstTime() {
        return remainingBurstTime;
    }

    public void reduceBurstTime(int time) {
        this.remainingBurstTime = Math.max(0, this.remainingBurstTime - time); // Ensure it doesn't go negative
    }

    // Getter and Setter for Priority Number
    public int getPriorityNumber() {
        return priorityNumber;
    }

    public void setPriorityNumber(int priorityNumber) {
        this.priorityNumber = priorityNumber;
    }

    // Getter and Setter for Quantum
    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public void updateQuantumAfterExecution(int usedTime) {
        if (remainingBurstTime > 0) {
            if (usedTime >= quantum * 0.4) {
                this.quantum += 2; // Add 2 if the process completes 40% or more of its quantum
            } else {
                this.quantum += quantum - usedTime; // Add unused quantum
            }
        }
    }

    // Getter and Setter for PID
    public int getPID() {
        return PID;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }

    // Getter and Setter for Waiting Time
    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    // Getter and Setter for Turnaround Time
    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    // Getter and Setter for FCAI Factor
    public float getFCAIFactor() {
        return fcaFactor;
    }

    public void calculateFCAIFactor(float v1, float v2) {
        this.fcaFactor = (10 - this.priorityNumber) + (this.arrivalTime / v1) + (this.remainingBurstTime / v2);
    }

    // Data Representation for Process Table
    public String[] getData() {
        return new String[]{String.valueOf(PID), Name, String.valueOf(arrivalTime), String.valueOf(burstTime), String.valueOf(priorityNumber), String.valueOf(quantum)};
    }

    // Clone Method for Creating a Copy
    public Process clone() {
        return new Process(Name, color, arrivalTime, burstTime, priorityNumber, quantum, PID);
    }
}
