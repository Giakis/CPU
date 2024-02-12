import java.lang.reflect.Array;
import java.util.ArrayList;

public class FCFS extends Scheduler {
    public FCFS() {
        /* TODO: you _may_ need to add some code here */
    }

    public void addProcess(Process p) {
        /* TODO: you need to add some code here */
        processes.add(p);
    }
    
    public Process getNextProcess() {
        /* TODO: you need to add some code here
         * and change the return value */
        for (Process process : processes){
                return process;
        }
        return null;
    }
}
