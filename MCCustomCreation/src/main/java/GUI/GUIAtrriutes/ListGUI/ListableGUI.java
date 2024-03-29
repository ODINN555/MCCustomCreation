package GUI.GUIAtrriutes.ListGUI;

import GUI.GUIAtrriutes.PageableGUI;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * A listable GUI, an implementation of IGUIList
 */
public class ListableGUI extends PageableGUI implements IGUIList {
    /**
     * The item list
     */
    protected List<ItemStack> itemsList;


    /**
     * The gui's item amount in a row
     */
    private int amountInRow;

    /**
     * The gui's prefix
     */
    private int prefix;

    /**
     * The gui's top rows prefix
     */
    private int topPrefix;

    /**
     * the gui's bottom rows prefix
     */
    private int bottomPrefix;

    /**
     *
     * @param list a given item list
     * @param name a given name
     * @param amountInRow a given amount in a row
     * @param prefix a given prefix
     */
    public ListableGUI(List<ItemStack> list,String name,int amountInRow,int prefix,int topPrefix,int bottomPrefix) {
        super(name,getMaxPageCountNeeded(list,amountInRow,topPrefix,bottomPrefix), getNextPageSlot(list,amountInRow), getPrevPageSlot(list,amountInRow));
        this.itemsList = list;
        this.prefix = prefix;
        this.amountInRow = amountInRow;
        this.topPrefix = topPrefix;
        this.bottomPrefix = bottomPrefix;
        if(topPrefix < 0)
            throw new IllegalArgumentException("Top prefix cannot be negative.");
        if(bottomPrefix < 0)
            throw new IllegalArgumentException("Bottom prefix cannot be negative.");
        if(bottomPrefix + topPrefix >= 6)
            throw new IllegalArgumentException("The sum of top and bottom prefixes cannot be equal or greater than max inventory rows.");
    }

    public ListableGUI(List<ItemStack> list,String name,int amountInRow,int prefix) {
    this(list,name,amountInRow,prefix,0,0);
    }

        /**
         *
         * @param list a given list
         * @param amountInRow a given amount of items which can be at each row
         * @return the max page count needed for this GUI
         */
    private static final int getMaxPageCountNeeded(List list,int amountInRow,int topPrefix,int bottomPrefix){
        if(list == null)
            return 1;
        int listSize = list.size();
        int size = 9 + (listSize / amountInRow) * 9 ;

        return size == 54 ? 1 : 1 + size / ((6- topPrefix - bottomPrefix) * 9);
    }

    /**
     *
     * @param list a given list
     * @param amountInRow a given amount of items which can be at each row
     * @return the next page item slot
     */
    private static final int getNextPageSlot(List list,int amountInRow) {
        if(list == null)
            return 8;
        if(amountInRow == 0)
            return 8;
        int listSize = list.size();
        int size = 9 + (listSize / amountInRow) * 9 ;
        int currentSize = size <= 54 ? size : 54;
        return currentSize - 1;
    }

    /**
     *
     * @param list a given list
     * @param amountInRow a given amount of items which can be at each row
     * @return the previous page item slot
     */
    private static final int getPrevPageSlot(List list,int amountInRow){
        return getNextPageSlot(list,amountInRow) -8;
    }

        /**
         *
         * @param list a given item list
         * @param name a given name
         */
    public ListableGUI(List<ItemStack> list,String name){
        this(list,name,7,0);


    }

    @Override
    protected Inventory initInventory() {
        Inventory inv = getGUIList(null, getTitle(),getItemsList(),getAmountInRow(),getPrefix(),getTopPrefix(),getBottomPrefix());
        return inv;
    }


    @Override
    protected void onPageChange(int from, int to) {


        if(to > maxPageCount)
            to = maxPageCount;

        if(from == to)
            return;

        int amountPerPage = 42; // index starts from 0 so 41 items in a page is 42
        List<ItemStack> nextList;

        if(to == maxPageCount)
            nextList = itemsList.subList(amountPerPage * from , itemsList.size() );
        else nextList = from < to ? itemsList.subList(amountPerPage * from -1 , amountPerPage * to -1) : itemsList.subList( amountPerPage * (to-1), amountPerPage * to );

        getGUIList(nextList,getInventory(),getAmountInRow(),getPrefix(),getTopPrefix(),getBottomPrefix());

        updateInventory();
    }

    public List<ItemStack> getItemsList() {
        return this.itemsList;
    }

    public int getAmountInRow() {
        return amountInRow;
    }

    public int getPrefix() {
        return prefix;
    }

    public int getTopPrefix() {
        return topPrefix;
    }

    public int getBottomPrefix() {
        return bottomPrefix;
    }

    @Override
    protected void onOpening() {
        super.onOpening();
        getGUIList(getItemsList(),getInventory(),getAmountInRow(),getPrefix(),getTopPrefix(),getBottomPrefix());
    }
}
