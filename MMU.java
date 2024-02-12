import java.util.ArrayList;

public class MMU {

    private final int[] availableBlockSizes;
    private MemoryAllocationAlgorithm algorithm;
    private ArrayList<MemorySlot> currentlyUsedMemorySlots;

    public MMU(int[] availableBlockSizes, MemoryAllocationAlgorithm algorithm) {
        this.availableBlockSizes = availableBlockSizes;
        this.algorithm = algorithm;
        this.currentlyUsedMemorySlots = new ArrayList<MemorySlot>();
    }

    public boolean loadProcessIntoRAM(Process p) {
        boolean fit = false;
        /* TODO: you need to add some code here (DONE by gravalos)
         * Hint: this should return true if the process was able to fit into memory
         * and false if not */

        if (p.getPCB().getState() != ProcessState.TERMINATED) {
            if (currentlyUsedMemorySlots.isEmpty()) {
                int i = 0;
                int tempStart = -1;
                int tempEnd;
                for (i = 0; i < availableBlockSizes.length; i++) {
                    tempEnd = tempStart + availableBlockSizes[i];
                    currentlyUsedMemorySlots.add(new MemorySlot(tempStart + 1, tempEnd, tempStart + 1, tempEnd));
                    tempStart += availableBlockSizes[i];
                }
            }
            if (algorithm.fitProcess(p, currentlyUsedMemorySlots) != -1) {
                fit = true;
            }
            return fit;
        }else {
            removeProcessFromRam(p);
            return true;
        }
    }

    private void removeProcessFromRam(Process p) {
        int address = p.getAddress();
        for (int i = 0; i < currentlyUsedMemorySlots.size(); i++) {
            if ( currentlyUsedMemorySlots.get(i).getBlockStart() <= address && currentlyUsedMemorySlots.get(i).getBlockEnd() >= address ) {
                currentlyUsedMemorySlots.get(i).setStart(currentlyUsedMemorySlots.get(i).getStart() - p.getMemoryRequirements());
            }
        }
    }

    public int[] getAvailableBlockSizes() {
        return availableBlockSizes;
    }

}
