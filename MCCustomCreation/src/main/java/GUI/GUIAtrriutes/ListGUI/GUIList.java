package GUI.GUIAtrriutes.ListGUI;

import GUI.Layout.LayoutOption;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public interface GUIList {


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
                inv.setItem(slots[j] + 9 * i + prefix,items.get(j));
        }

        for (int i = 0; i < inv.getSize(); i++)
            if(inv.getItem(i) == null || inv.getItem(i).getType() == Material.AIR)
                inv.setItem(i,getBlankSlotItem(getBlankSlotMaterial()));

            return inv;
    }
    default Inventory getGUIList(InventoryHolder owner,String title,List<ItemStack> items,int amountInRow,int prefix){
        int rows = items.size() / amountInRow;
        int size = 9 + (rows) * 9;
        size = size >= 54 ? 54 : size;
        Inventory i = Bukkit.createInventory(owner,size,title);
        return getGUIList(items,i,amountInRow,prefix);
    }

    default ItemStack getBlankSlotItem(Material mat){
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);
        return item;
    }

    default Material getBlankSlotMaterial(){
        return Material.BLACK_STAINED_GLASS_PANE;
    }
}
