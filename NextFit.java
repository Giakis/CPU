import java.util.ArrayList;

public class NextFit extends MemoryAllocationAlgorithm {

    public NextFit(int[] availableBlockSizes) {
        super(availableBlockSizes);
    }
    private static int lastAddress = 0;

    public int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots) {
        boolean fit = false;
        int address = -1;
        /* TODO: you need to add some code here
         * Hint: this should return the memory address where the process was
         * loaded into if the process fits. In case the process doesn't fit, it
         * should return -1. */
        int i = 0;
        for (i = 0 ; i < availableBlockSizes.length ; i++) {
            int sizeOfBlock = currentlyUsedMemorySlots.get(lastAddress).getEnd() - currentlyUsedMemorySlots.get(lastAddress).getStart() + 1 ;
            if ( sizeOfBlock >= p.getMemoryRequirements() ) {
                address = currentlyUsedMemorySlots.get(lastAddress).getStart();//lastAddress = helpingCounter;
                p.setAddress(address);
                //System.out.println(address);
                currentlyUsedMemorySlots.get(lastAddress).setStart(currentlyUsedMemorySlots.get(lastAddress).getStart()+p.getMemoryRequirements());
                fit = true; // required if we used while(fit == false) instead of for.
                break;
            }else {
                lastAddress +=1;
            }
            if (lastAddress == availableBlockSizes.length) {
                lastAddress = 0;
            }
        }
        return address;
    }
}