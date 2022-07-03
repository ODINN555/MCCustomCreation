package GUI.DisplayGUI;

import Utility.ItemStackUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * An enum of default display types
 */
public enum DefaultDisplayTypes implements DisplayType{

    INTEGER(Integer.class, ItemStackUtil.newItemStack(Material.BLUE_STAINED_GLASS_PANE, ChatColor.BLUE+"Integer")),
    FLOAT(Float.class, ItemStackUtil.newItemStack(Material.YELLOW_STAINED_GLASS_PANE,ChatColor.YELLOW+"Float")),
    STRING(String.class, ItemStackUtil.newItemStack(Material.PINK_STAINED_GLASS_PANE, ChatColor.LIGHT_PURPLE+"String")),
    BOOLEAN(Boolean.class, ItemStackUtil.newItemStack(Material.RED_STAINED_GLASS_PANE,ChatColor.RED+"Boolean"))
    ;


    /**
     * The display type
     */
    private Class type;
    /**
     * The ItemStack being displayed
     */
    private ItemStack displayItem;


    DefaultDisplayTypes(Class type,ItemStack displayItem){
        this.type = type;
        this.displayItem = displayItem;
    }


    @Override
    public ItemStack getDisplayItem() {
        return this.displayItem;
    }

    @Override
    public Class getType() {
        return this.type;
    }
}
