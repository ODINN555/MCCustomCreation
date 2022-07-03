package GUI.Layout;

/**
 * Layout value contains the value of a layout.
 * The slots which the items should be at and the size of the GUI.
 */
public class LayoutValue {

    /**
     * The slots where the items should be placed at
     */
    public int[] slots;

    /**
     * The size which the GUI should be.
     */
    public int size;

    /**
     *
     * @param size a given gui size
     * @param slots given slots
     */
    public LayoutValue(int size,int[] slots){
        this.size = size;
        this.slots = slots;
    }

}
