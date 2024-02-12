import java.util.ArrayList;

public class Process {
    private ProcessControlBlock pcb;
    private int arrivalTime;
    private int burstTime;
    private int memoryRequirements;
    private int startTime = -1;

    private int address;

    public Process(int arrivalTime, int burstTime, int memoryRequirements) {
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.memoryRequirements = memoryRequirements;
        this.pcb = new ProcessControlBlock();
    }

    public void setStartTime(int startTime) { this.startTime = startTime; }

    public int getAddress() {
        return this.address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getArrivalTime() {
        return this.arrivalTime;
    }

    public int getBurstTime() {
        return this.burstTime;
    }

    public ProcessControlBlock getPCB() {
        return this.pcb;
    }

    public void run() {
        /* TODO: you need to add some code here
         * Hint: this should run every time a process starts running */
        if (pcb.getState() == ProcessState.READY) {
            pcb.setState(ProcessState.RUNNING, CPU.clock);
        }
        burstTime -- ;
    }

    public void waitInBackground() {
        /* TODO: you need to add some code here
         * Hint: this should run every time a process stops running */
        pcb.setState(ProcessState.READY , CPU.clock);
    }

    public double getWaitingTime() {
        /* TODO: you need to add some code here
         * and change the return value */
        ArrayList<Integer> startTimes = pcb.getStartTimes();
        ArrayList<Integer> stopTimes = pcb.getStopTimes();
        int total = startTimes.get(0) - startTime;
        for (int i = 0; i < startTimes.size()-1; i++) {
            total += startTimes.get(i+1) - stopTimes.get(i);
        }
        return total;
    }

    public double getResponseTime() {
        /* TODO: you need to add some code here
         * and change the return value */
        if (startTime != -1){
            System.out.println(startTime);
            return pcb.getStartTimes().get(0) - startTime;
        }
        return -1;
    }

    public double getTurnAroundTime() {
        /* TODO: you need to add some code here
         * and change the return value */
        ArrayList<Integer> stopTimes = pcb.getStopTimes();
        int lastElement = stopTimes.size()-1;
        return stopTimes.get(lastElement) - startTime;
    }

    public int getMemoryRequirements() {
        return this.memoryRequirements;
    }
}