package GUI.Layout;

/**
 * An enum for default LayoutOptions
 */
public enum LayoutOption implements Layout{
    /**
     * This layout tries to make as much spaces as possible between items
     */
    SPACES{
        @Override
        public LayoutValue getSlotsByLayout(int itemAmount) {
            if(itemAmount <= 0)
                return new LayoutValue(9,new int[0]);

            if(itemAmount > 54)
                itemAmount = 54;
            if(itemAmount % 9 == 0)
                return new LayoutValue(itemAmount,getAllSlots(itemAmount / 9));

            int size = (itemAmount / 9 + 1)*9;
            int amountInRow = itemAmount % 9;
            int[] slots = new int[itemAmount];
            for(int i = 0; i < itemAmount - amountInRow;i++)
                slots[i] = i;
            int startPoint = (itemAmount / 9) * 9;
            int counter = startPoint;
            int loopAmount = (int) Math.ceil(amountInRow/2) -1;

            if(itemAmount % 2 == 1) // Odd number, start the layout from the middle
            {

                for(int i = loopAmount; i>=0; i++,counter+=2){
                    slots[i] = counter > 4 + startPoint? counter -4 -1: 4 - counter; // left size
                    slots[amountInRow - i] = counter > 4 + startPoint? 16  - (counter+4 + -1) : 4 + counter; // right side
                }


            }else{ // Even number, start layout from middle with offset of 1
                int offset = 1;
                for(int i = loopAmount; i>=0; i++,counter+=2){
                    slots[i] = counter >= 4 + startPoint? 8- counter - offset - 1 : 4 - counter - offset; // left size
                    slots[amountInRow - i] = counter >= 4 + startPoint ? counter +1 + offset : 4 + counter + offset; // right side
                }

            }
            return new LayoutValue(size,slots);
        }},
    /**
     * Creates as many groups as it can with the given item amount
     */
    GROUPS{
        @Override
        public LayoutValue getSlotsByLayout(int itemAmount) {
            if(itemAmount <= 0)
                return new LayoutValue(9,new int[0]);

            if(itemAmount > 54)
                itemAmount = 54;
            if(itemAmount % 9 == 0)
                return new LayoutValue(itemAmount,getAllSlots(itemAmount / 9));

            if(itemAmount % 2 == 1) // Odd number, result is same as centered
                return CENTERED.getSlotsByLayout(itemAmount);
            else{

                int size = (itemAmount /9 + 1)* 9;
                int[] slots = new int[size];

                int startPoint = (itemAmount / 9)*9;
                for(int i = 0; i < startPoint; i++)
                    slots[i] = i;

                int loopAmount = (itemAmount % 9)/2;

                for(int i = startPoint; i < loopAmount + startPoint; i++) {
                    slots[i] = (4 + startPoint) - i;
                    slots[8-i] = (4+startPoint) + i;
                }
                return new LayoutValue(size,slots);
            }
        }
    },
    /**
     * Centers the items as much as possible
     */
    CENTERED{
        @Override
        public LayoutValue getSlotsByLayout(int itemAmount) {

            if(itemAmount <= 0)
                return new LayoutValue(9,new int[0]);

                if(itemAmount > 54)
                    itemAmount = 54;
            if(itemAmount % 9 == 0)
                return new LayoutValue(itemAmount,getAllSlots(itemAmount / 9));

                int startPoint= itemAmount > 9? itemAmount - itemAmount % 9 : 0;
                int size = (itemAmount / 9 + 1)*9;
                    int[] arr = new int[itemAmount];
                    int space = (9 - (itemAmount%9))/2; // the amount left, half to 2 for the space of each side
                    for(int i = 0; i< startPoint; i++)
                        arr[i] = i;
                    int index = 0;
                    for(int i = space+startPoint; i<space+itemAmount; i++)
                    {
                        arr[index] = i;
                        index++;
                    }


            return new LayoutValue(size,arr);
        }
    }
    ;

    /**
     *
     * @param rows a given row amount
     * @return a list containing all slots numbers of the given rows amount
     */
    private static int[] getAllSlots(int rows){
        int size = rows > 5 ? 54 : rows * 9;
        int[] arr = new int[size];
        for(int i = 0; i < size; i ++)
            arr[i] = i;

        return arr;
    }

    /**
     *
     * @param itemAmount a given itemAmount
     * @param rows given rows amount
     * @return if the itemAmount and the rows are valid
     */
    boolean isValid(int itemAmount, int rows){
        if(rows > 5)
            return false;
        if(itemAmount > rows * 9)
            return false;

        return true;
    }

}
