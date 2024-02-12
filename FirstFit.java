import java.util.ArrayList;

public class FirstFit extends MemoryAllocationAlgorithm {
    public FirstFit(int[] availableBlockSizes) {
        super(availableBlockSizes);
    }
    public int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots) {
        boolean fit = false;
        int address = -1;
        /* TODO: you need to add some code here
         * Hint: this should return the memory address where the process was
         * loaded into if the process fits. In case the process doesn't fit, it
         * should return -1. */
        int i = 0;
        for (i = 0 ; i < availableBlockSizes.length ; i++) {
            int sizeOfBlock = currentlyUsedMemorySlots.get(i).getEnd() - currentlyUsedMemorySlots.get(i).getStart() + 1 ;
            if ( sizeOfBlock >= p.getMemoryRequirements() ) {
                address = currentlyUsedMemorySlots.get(i).getStart();
                p.setAddress(address);
                //System.out.println(address);
                currentlyUsedMemorySlots.get(i).setStart(currentlyUsedMemorySlots.get(i).getStart()+p.getMemoryRequirements());
                fit = true; // required if we used while(fit == false) instead of for.
                break;
            }
        }
        return address;
    }
}