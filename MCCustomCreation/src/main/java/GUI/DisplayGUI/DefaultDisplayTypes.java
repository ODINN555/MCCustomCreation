package GUI.DisplayGUI;

import Nodes.FunctionTree;
import Utility.ItemStackUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * An enum of default display types
 */
public enum DefaultDisplayTypes implements DisplayType{

    INTEGER(Integer.class, Material.BLUE_STAINED_GLASS_PANE,ChatColor.BLUE),
    FLOAT(Float.class, Material.YELLOW_STAINED_GLASS_PANE,ChatColor.YELLOW),
    STRING(String.class, Material.PINK_STAINED_GLASS_PANE, ChatColor.LIGHT_PURPLE),
    BOOLEAN(Boolean.class, Material.RED_STAINED_GLASS_PANE,ChatColor.RED)
    ;


    /**
     * The display type
     */
    private Class type;
    private Material mat;
    private ChatColor nameColor;
    /**
     * The ItemStack being displayed
     */
    private ItemStack displayItem;


    DefaultDisplayTypes(Class type,Material mat,ChatColor nameColor){
        this.type = type;
        this.mat = mat;
        this.nameColor = nameColor;
    }



    @Override
    public ItemStack getDisplayItem(FunctionTree tree) {
        return DisplayTypesHandler.INSTANCE.createDefaultDisplayType(mat, type.getSimpleName(), nameColor,tree);
    }

    @Override
    public Class getType() {
        return this.type;
    }



}
