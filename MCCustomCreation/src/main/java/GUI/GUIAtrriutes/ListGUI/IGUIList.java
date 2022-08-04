package GUI.GUIAtrriutes.ListGUI;

import GUI.Layout.LayoutOption;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * An interface for listable GUIs
 */
public interface IGUIList {


    /**
     * modifies the current inventory or creating a new one,
     * placing the given items as a list with the given settings
     * @param items given item list
     * @param inv given inventory
     * @param amountInRow a given amount of items allowed in a row
     * @param prefix a given prefix for every row. + for prefix and - for suffix
     * @return the modified inventory
     */
    default Inventory getGUIList(List<ItemStack> items,Inventory inv,int amountInRow,int prefix,int topPrefix,int bottomPrefix){

        if(prefix+ amountInRow > 9)
            throw new IllegalArgumentException("Illegal Arguments, amountInRow and prefix can't be greater than 9. amountInRow: "+amountInRow+" prefix: "+prefix);
        if(inv.getSize() == (topPrefix + bottomPrefix)*9)
            throw new IllegalArgumentException("Illegal Arguments, the sum of top and bottom prefixes cannot be equal or greater than the max inventory rows. top: "+topPrefix+" bottom: "+bottomPrefix+" size: "+inv.getSize());

        int rows = items.size() / amountInRow;
        int size = 9 + (rows + topPrefix + bottomPrefix) * 9;
        size = size >= 54 ? 54 : size;
        int lastRow = rows >= 6 ? amountInRow : items.size() % amountInRow;


        // reset items
        int start = topPrefix <=0 ? 0 : topPrefix;
        int end = bottomPrefix <= 0 ? 0 : bottomPrefix;

        int[] slotsToReset = LayoutOption.CENTERED.getSlotsByLayout(amountInRow).slots;
        for(int i = start; i < inv.getSize()/9 - end; i++) // rows
            for(int j = 0; j < slotsToReset.length; j++) // slot each row
                inv.setItem(prefix + slotsToReset[j] + 9 * i,getBlankSlotItem(getBlankSlotMaterial()));

        for(int i = 1 + start; i <= size / 9 - end;i++) { // rows
            int[] slots = LayoutOption.CENTERED.getSlotsByLayout(i == size / 9 - end ? lastRow : amountInRow).slots;
            for (int j = 0; j < slots.length; j++) { // the indexes of the slot in the slots array
                inv.setItem(slots[j] + (9 * (i - 1)) + prefix, items.size() >= (j + amountInRow * (i - 1 -start)) ? items.get(j + amountInRow * (i - 1 -start)) : getBlankSlotItem(getBlankSlotMaterial()));
            }
        }

        for (int i = 0; i < inv.getSize(); i++)
            if(inv.getItem(i) == null || inv.getItem(i).getType() == Material.AIR)
                inv.setItem(i,getBlankSlotItem(getBlankSlotMaterial()));

            return inv;
    }

    default Inventory getGUIList(List<ItemStack> items,Inventory inv,int amountInRow,int prefix) {
        return this.getGUIList(items,inv,amountInRow,prefix,0,0);
    }
        /**
         *
         * @param owner a given holder
         * @param title a given title
         * @param items a given item list
         * @param amountInRow a given
         * @param prefix
         * @return
         */
    default Inventory getGUIList(InventoryHolder owner, String title,  List<ItemStack> items, int amountInRow, int prefix,int topPrefix,int bottomPrefix){
        if(items == null)
            items = new ArrayList<>();
        if(amountInRow <= 0)
            amountInRow =1;
        int rows = items.size() / amountInRow;
        int size =  (1+ rows + topPrefix + bottomPrefix) * 9;
        size = size >= 54 ? 54 : size;
        Inventory i = Bukkit.createInventory(owner,size,title);
        return getGUIList(items,i,amountInRow,prefix,topPrefix,bottomPrefix);
    }

    /**
     *
     * @param mat a given material
     * @return a blank item using the given material
     */
    default ItemStack getBlankSlotItem(Material mat){
        if(mat == null)
            mat = getBlankSlotMaterial();

        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);
        return item;
    }

    /**
     *
     * @return the default blank item material
     */
    default Material getBlankSlotMaterial(){
        return Material.BLACK_STAINED_GLASS_PANE;
    }
}
