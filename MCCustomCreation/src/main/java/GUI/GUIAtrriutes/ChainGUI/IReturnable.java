package GUI.GUIAtrriutes.ChainGUI;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * An interface for returnable GUIs
 */
public interface IReturnable extends IChainable{

    /**
     * handles a click on the return button
     */
    default void onReturnClicked(){
        prev();
    }

    /**
     *
     * @return the default return button ItemStack instance
     */
    default ItemStack getDefaultReturnItemStack(){

        Material DEFAULT_MATERIAL = Material.HOPPER;
        ChatColor NAME_COLOR = ChatColor.GOLD;
        String NAME = "Return";

        ItemStack itemStack = new ItemStack(DEFAULT_MATERIAL);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(NAME_COLOR+NAME);
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    /**
     * initializes the returnItem ItemStack instance in the inventory
     */
    void initReturnItemInInventory();
}
