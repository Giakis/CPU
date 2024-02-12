public class RoundRobin extends Scheduler {

    private int quantum;
    private static int counter = 0;
    private static int currentProcess = 0;
    private static int sizeOfScheduler;

    public RoundRobin() {
        this.quantum = 1; // default quantum
        /* TODO: you may need to add some code here */
    }



    public RoundRobin(int quantum) {
        this();
        this.quantum = quantum;
    }

    public void addProcess(Process p) {
        /* TODO: you need to add some code here */
        processes.add(p);
        //sizeOfScheduler = processes.size();
    }

    public Process getNextProcess() {
        /* TODO: you need to add some code here
         * and change the return value */
        if (sizeOfScheduler != processes.size()) {
            if (sizeOfScheduler < processes.size()) {
                //System.out.println("EEEEEEEE"+currentProcess);
                currentProcess = 0;
            }
            counter = 0;
            sizeOfScheduler = processes.size();
        }
        if (counter == quantum) {
            counter = 0;
            currentProcess += 1;
        }
        if (currentProcess >= processes.size()) {
            //System.out.println("EEEEEEEE"+currentProcess);
            currentProcess = 0;
        }
        if (counter < quantum && !processes.isEmpty()) {
            //currentProcess += 1;
            counter += 1;
            return processes.get(currentProcess);
        }
        return null;
    }
}