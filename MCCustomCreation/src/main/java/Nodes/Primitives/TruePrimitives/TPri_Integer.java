package Nodes.Primitives.TruePrimitives;

import Nodes.TruePrimitive;
import Utility.ItemStackUtil;
import Utility.Logging.Logging;
import Utility.Logging.LoggingOptions;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class TPri_Integer extends SignInputtedPrimitive {


    @Override
    public ItemStack getDefaultDisplayItem() {
        return ItemStackUtil.newItemStack(Material.BLUE_STAINED_GLASS_PANE, ChatColor.BLUE+getKeyAsDisplay());
    }

    @Override
    public String getKey() {
        return "RAW_INTEGER";
    }


    @Override
    protected void onInput(String[] lines) {
        int num = 0;
        try {
            String result = lines[0];
            num = Integer.parseInt(result);
        }catch (NumberFormatException | NullPointerException e){
            Logging.message(super.gui.getOwner(),"You must enter an integer! (integer is a non decimal number (only 1,5,10000... not 0.1,1.5 or 0.147389 for example), its limit is about 2.1B)",LoggingOptions.ERROR);
        }
        setValue(num);
        super.gui.prev();
    }

    @Override
    public Class getReturnType() {
        return Integer.class;
    }

    @Override
    public TruePrimitive clone() {
        return new TPri_Integer();
    }

}
