package GUI.DisplayGUI;

import Utility.ItemStackUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * A handler for display types
 */
public class DisplayTypesHandler {

    /**
     * The registered display types
     */
    private static Set<DisplayType> DISPLAYS = new HashSet<>();
    
    /**
     * Singleton implementation
     */
    public static final DisplayTypesHandler INSTANCE = new DisplayTypesHandler();
    private DisplayTypesHandler(){
        // default registry
        register(DefaultDisplayTypes.values());
    }

    /**
     * returns a default DisplayType for custom type which is not registered in this plugin
     * @param type a given type
     * @return a default "custom" DisplayType
     */
    private static DisplayType getCustom(Class type){
        return new DisplayType() {
            @Override
            public ItemStack getDisplayItem() {
                return ItemStackUtil.newItemStack(Material.PURPLE_STAINED_GLASS_PANE,ChatColor.DARK_PURPLE+getType().getSimpleName());
            }

            @Override
            public Class getType() {
                return type;
            }
        };
    }



    /**
     * Registers display types
     * @param types given types to register
     */
    public void register(DisplayType... types){
        DISPLAYS.addAll(Arrays.asList(types));
    }

    /**
     *
     * @param type a given type
     * @return the display type which displays the given class type. custom if doesn't exist.
     */
    public DisplayType getByType(Class type){
        for (DisplayType display : DISPLAYS) {
            if(display.getType().equals(type))
                return display;
        }

        return getCustom(type);
    }


}
