import java.util.ArrayList;

public class CPU {


    public static int clock = 0; // this should be incremented on every CPU cycle
    private ArrayList<Process> loadedOnMMU;

    private Scheduler scheduler;
    private MMU mmu;
    private Process[] processes;
    private ArrayList<Integer> initBurstTimes = new ArrayList<>(); // save at the beginning the burt times of the processes
    private int currentProcess; // the id of the process that is running at a specified clock
    private int processIndex; // the process that is next in priority to run
    private int maxMemorySlot; // the size of block with the bigger size


    public CPU(Scheduler scheduler, MMU mmu, Process[] processes) {
        this.scheduler = scheduler;
        this.mmu = mmu;
        this.processes = processes;
    }

    public void run() {
        /* TODO: you need to add some code here
         * Hint: you need to run tick() in a loop, until there is nothing else to do... */

        for (int i = 0; i < processes.length; i++) {
            initBurstTimes.add(processes[i].getBurstTime());
        }

        clock = 0;
        maxMemorySlot = findMaxBlockOfTheAvailableBlocks();
        currentProcess = -1; //No process into RUNNING situation
        loadedOnMMU = new ArrayList<Process>();
        processIndex = 0;
        Process cp = null;
        while (true) {

            if (scheduler.processes.size() != 0) {
                cp = scheduler.getNextProcess(); // when processes are available in scheduler, scheduler reveal which is the next process for running
            }else {
                while (scheduler.processes.size() == 0) {
                    loadProcessesOnMMU(); // when any process is not available in scheduler we try to put them
                    if (scheduler.processes.size() == 0) {
                        clock += 1; // at this clock are not available processes
                    }else {
                        break; // processes loaded
                    }
                    if (processIndex >= processes.length) {
                        break; // there is no more processes for cpu
                    }
                }
                cp = scheduler.getNextProcess();
            }

            if (cp != null && currentProcess == cp.getPCB().getPid()) { // no switch
                tick();
                cp.run();
            }else if (cp != null && currentProcess != -1) { // switch from ready to running and reverse
                processes[currentProcess].waitInBackground();
                tick();
                tick();
                loadProcessesOnMMU();
                cp.run();
                tick();
                currentProcess = cp.getPCB().getPid();
            }else if (cp != null){ // currentProcess == -1 , switch from ready to running.
                loadProcessesOnMMU();
                tick();
                tick();
                cp.run();
                tick();
                currentProcess = cp.getPCB().getPid();
            }
            if (cp != null) {
                System.out.println("Process " + (cp.getPCB().getPid() + 1) + " have yet to run for " + cp.getBurstTime() + " cpu cycles at clock: " + clock);
            }
            if (cp != null && cp.getBurstTime() == 0) {
                cp.getPCB().setState(ProcessState.TERMINATED , clock);
                scheduler.processes.remove(cp);
                mmu.loadProcessIntoRAM(cp);
                currentProcess = -1;
            }

            if (processIndex == processes.length && scheduler.processes.size() == 0) {
                if (loadedOnMMU.size() != 0) { // for the example when any process fits to RAM
//                    int sumRT = 0, sumTAT = 0, sumWT = 0;
//                    System.out.println("PROCESSES " + "  " + "ArrivalTime " + "   " + "BurstTime" + "   " + "MemoryRequirements" +
//                            "   " + "WaitingTime" + "   " + "ResponseTime" + "   " + "TotalAroundTime");
//                    for (int i = 0; i < loadedOnMMU.size(); i++) {
//                        System.out.println("   " + (processes[loadedOnMMU.get(i).getPCB().getPid()].getPCB().getPid() + 1) + "            " + processes[loadedOnMMU.get(i).getPCB().getPid()].getArrivalTime() +
//                                "              " + initBurstTimes.get(i) + "              " + processes[loadedOnMMU.get(i).getPCB().getPid()].getMemoryRequirements() +
//                                "                " + processes[loadedOnMMU.get(i).getPCB().getPid()].getWaitingTime() + "           " + processes[loadedOnMMU.get(i).getPCB().getPid()].getResponseTime() +
//                                "             " + processes[loadedOnMMU.get(i).getPCB().getPid()].getTurnAroundTime());
//
//                        sumRT += processes[loadedOnMMU.get(i).getPCB().getPid()].getResponseTime();
//                        sumWT += processes[loadedOnMMU.get(i).getPCB().getPid()].getWaitingTime();
//                        sumTAT += processes[loadedOnMMU.get(i).getPCB().getPid()].getTurnAroundTime();
//                    }
//                    System.out.println();
//                    System.out.println("                   ->Response Time :" + sumRT / processes.length);
//                    System.out.println("Total CPU time for:->Waiting Time :" + sumWT / processes.length);
//                    System.out.println("                   ->Total Around Time :" + sumTAT / processes.length);
                }
                break;
            }
        }
    }

    private void loadProcessesOnMMU() {
        while (true) {
            if (processIndex < processes.length && clock >= processes[processIndex].getArrivalTime()) { // when there are existing and other processes for cpu and had arrival time >= clock
                if (processIndex < processes.length && processes[processIndex].getMemoryRequirements() > maxMemorySlot) {
                    // this if except the processes that is not fit in any block goes to the next process
                    processIndex += 1;
                    System.out.println("Procces: " + processIndex + " failed to load into RAM");
                    continue;
                }
                if (processIndex < processes.length && mmu.loadProcessIntoRAM(processes[processIndex])) {//when it can load to RAM
                    scheduler.addProcess(processes[processIndex]);
                    loadedOnMMU.add(processes[processIndex]); // helping variable for counting processes that are in scheduler
                    clock += 1;
                    processes[processIndex].getPCB().setState(ProcessState.READY, clock);
                    processes[processIndex].setStartTime(clock); // help for calculating response time etc.
                    processIndex += 1;
                    System.out.println("Procces: " + processIndex + " loaded into RAM at clock: " + clock);
                } else {
                    break;
                }
            } else {
                break;
            }
        }
    }

    private int findMaxBlockOfTheAvailableBlocks() {
        int[] availableBlockSizes;
        availableBlockSizes = mmu.getAvailableBlockSizes();
        int maxMemorySlot = 0;
        for (int i = 0; i < availableBlockSizes.length; i++) {
            if (availableBlockSizes[i] > maxMemorySlot) {
                maxMemorySlot = availableBlockSizes[i];
            }
        }
        return maxMemorySlot;
    }
    public void tick() {
        /* TODO: you need to add some code here
         * Hint: this method should run once for every CPU cycle */
        clock += 1;
    }

}