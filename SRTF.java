import java.util.ArrayList;

public class SRTF extends Scheduler {
    public SRTF() {
        /* TODO: you _may_ need to add some code here */
    }

    public void addProcess(Process p){
        /* TODO: you need to add some code here */
        processes.add(p);
    }
    
    public Process getNextProcess(){
        /* TODO: you need to add some code here
         * and change the return value */
        if (processes.size()!=0) {
            int i=0,min=0,burstmin=0;
            min = processes.get(0).getBurstTime();
            burstmin = 0;
            for (i = 1; i < processes.size(); i++) {
                if (processes.get(i).getBurstTime() < min) {
                    min = processes.get(i).getBurstTime();
                    burstmin = i;
                }
            }
            if (burstmin != -1) {
                return processes.get(burstmin);
            }
        }
        return null;
    }
}
