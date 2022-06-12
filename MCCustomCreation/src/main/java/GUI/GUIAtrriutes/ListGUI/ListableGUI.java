package GUI.GUIAtrriutes.ListGUI;

import GUI.GUIAtrriutes.ListGUI.GUIList;
import GUI.GUIAtrriutes.PageableGUI;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ListableGUI extends PageableGUI implements GUIList {
    private final List<ItemStack> itemsList;
    private final String name;
    public ListableGUI(List<ItemStack> list,String name) {
        super(0, 0, 0);
        this.itemsList = list;
        this.name = name;

        int size = 9 + (itemsList.size() / 7) * 9 ;
        maxPageCount = 1 + size / 54;

        int currentSize = size <= 54 ? 54 : size;
        nextPageSlot = currentSize - 1;
        previousPageSlot = nextPageSlot - 9;
    }

    @Override
    protected Inventory initInventory() {
        Inventory inv = getGUIList(null, name,getItemsList());
        return inv;
    }

    @Override
    protected void onPageChange(int from, int to) {
        if(to > maxPageCount)
            to = maxPageCount;
        int amountPerPage = 42 -1; // index starts from 0 so 41 items in a page is 42
        List<ItemStack> nextList;
        if(to == maxPageCount)
            nextList = itemsList.subList(amountPerPage * from , itemsList.size() -1);
        else nextList = from < to ? itemsList.subList(amountPerPage * from, amountPerPage * to) : itemsList.subList(amountPerPage * to, amountPerPage * from);

        getGUIList(nextList,getInventory());
        updateInventory();
    }

    public List<ItemStack> getItemsList() {
        return itemsList;
    }
}
