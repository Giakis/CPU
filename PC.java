
public class PC {

    public static void main(String[] args) {
        /* TODO: You may change this method to perform any tests you like */
        
        final Process[] processes = {
                // Process parameters are: arrivalTime, burstTime, memoryRequirements (kB)
                new Process(0, 5, 15),
                new Process(1, 5, 40),
                new Process(1, 1, 10),
                new Process(1, 1, 20)
        };
        final int[] availableBlockSizes = {15, 40, 10, 20}; // sizes in kB
        MemoryAllocationAlgorithm algorithm = new NextFit( availableBlockSizes);
        MMU mmu = new MMU(availableBlockSizes, algorithm);
//        Scheduler scheduler = new BestFit(MemoryAllocationAlgorithm[40]);
//        Scheduler scheduler = new FirstFit();
//        Scheduler scheduler = new NextFit();
//        Scheduler scheduler = new WorstFit();
//        Scheduler scheduler = new RoundRobin();
//        Scheduler scheduler = new FCFS();
        Scheduler scheduler = new SRTF();
        CPU cpu = new CPU(scheduler, mmu, processes);
        cpu.run();
    }
}
