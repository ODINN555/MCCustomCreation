package GUI.DisplayGUI;

import Utility.ItemStackUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Set;

public class DisplayTypesHandler {
    
    public static final DisplayTypesHandler INSTANCE = new DisplayTypesHandler();
    private DisplayTypesHandler(){
        // default registry
        register(DefaultDisplayTypes.values());
    }

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


    private static Set<DisplayType> DISPLAYS;
    
    public void register(DisplayType... types){
        DISPLAYS.addAll(Arrays.asList(types));
    }
    
    public DisplayType getByType(Class type){
        for (DisplayType display : DISPLAYS) {
            if(display.getType().equals(type));
                return display;
        }

        return getCustom(type);
    }


}
