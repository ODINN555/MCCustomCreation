package GUI.GUIAtrriutes.ListGUI;

import GUI.Layout.LayoutOption;
import com.sun.istack.internal.NotNull;
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
    default Inventory getGUIList(List<ItemStack> items,Inventory inv,int amountInRow,int prefix){

        if(prefix+ amountInRow > 9)
            throw new IllegalArgumentException("Illegal Arguments, amountInRow and prefix can't be greater than 9. amountInRow: "+amountInRow+" prefix: "+prefix);
        int rows = items.size() / amountInRow;
        int size = 9 + (rows) * 9;
        size = size >= 54 ? 54 : size;
        int lastRow = rows > 6? amountInRow : items.size() % amountInRow;

        for(int i = 1; i <= size / 9;i++) {
            int[] slots = LayoutOption.CENTERED.getSlotsByLayout(i == (size - 1) / 9 ? lastRow : amountInRow).slots;
            for (int j = 0; j < slots.length; j++)
                inv.setItem(slots[j] * i + prefix, items.size() > (j * i) ? items.get(j * i) : getBlankSlotItem(getBlankSlotMaterial()));
        }

        for (int i = 0; i < inv.getSize(); i++)
            if(inv.getItem(i) == null || inv.getItem(i).getType() == Material.AIR)
                inv.setItem(i,getBlankSlotItem(getBlankSlotMaterial()));

            return inv;
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
    default Inventory getGUIList(InventoryHolder owner, String title, @NotNull List<ItemStack> items, int amountInRow, int prefix){
        if(items == null)
            items = new ArrayList<>();
        if(amountInRow <= 0)
            amountInRow =1;
        int rows = items.size() / amountInRow;
        int size = 9 + (rows) * 9;
        size = size >= 54 ? 54 : size;
        Inventory i = Bukkit.createInventory(owner,size,title);
        return getGUIList(items,i,amountInRow,prefix);
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
