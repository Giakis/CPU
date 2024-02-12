import java.util.ArrayList;

public class BestFit extends MemoryAllocationAlgorithm {
    public BestFit(int[] availableBlockSizes) {
        super(availableBlockSizes);
    }
    /* TODO: you need to add some code here
     * Hint: this should return the memory address where the process was
     * loaded into if the process fits. In case the process doesn't fit, it
     * should return -1. */
    /**
     * This is a method that returns if the process can fit in a memory slot with algorith BestFit.
     * If can fit returns the address if doesnt fit returns -1.
     * @author annaskar
     * */
    private void bubbleSortArrayList(ArrayList<Integer> list) {
        Integer temp;
        boolean sorted = false;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < list.size()-1; i++) {
                if (list.get(i).compareTo(list.get(i + 1)) > 0) {
                    temp = list.get(i);
                    list.set(i, list.get(i + 1));
                    list.set(i + 1, temp);
                    sorted = false;
                }
            }
        }
    }

    public int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots) {
        boolean fit = false;
        int address = -1;
        ArrayList<Integer> NewMemorySlot = new ArrayList<Integer>(currentlyUsedMemorySlots.size()); //a arraylist that is used for find the right place.
        for (int i = 0 ; i < currentlyUsedMemorySlots.size(); i++) {
            int sizeOfBlock = currentlyUsedMemorySlots.get(i).getEnd() - currentlyUsedMemorySlots.get(i).getStart() + 1 ;
            NewMemorySlot.add(sizeOfBlock);
        }
        bubbleSortArrayList(NewMemorySlot); //increasing
        for(int i = 0 ; i < NewMemorySlot.size(); i++)
        {
            if(NewMemorySlot.get(i)>=p.getMemoryRequirements())
            {
                int NewAddress=NewMemorySlot.get(i);
                for(int j = 0 ; j < NewMemorySlot.size(); j++)
                {
                    int Element=currentlyUsedMemorySlots.get(j).getEnd() - currentlyUsedMemorySlots.get(j).getStart() + 1 ;

                    if( NewAddress == Element)
                    {

                        address = currentlyUsedMemorySlots.get(j).getStart();
                        p.setAddress(address);
                        //System.out.println(address);
                        currentlyUsedMemorySlots.get(j).setStart(currentlyUsedMemorySlots.get(j).getStart()+p.getMemoryRequirements());
                        fit=true;
                        break;
                    }
                }
                break;
            }
        }
        return address;
    }
}