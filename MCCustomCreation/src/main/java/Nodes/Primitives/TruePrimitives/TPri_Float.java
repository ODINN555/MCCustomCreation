package Nodes.Primitives.TruePrimitives;

import Nodes.TruePrimitive;
import Utility.ItemStackUtil;
import Utility.Logging.Logging;
import Utility.Logging.LoggingOptions;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class TPri_Float extends SignInputtedPrimitive {
    @Override
    public String getKey() {
        return "RAW_FLOAT";
    }

    @Override
    public Class getReturnType() {
        return Float.class;
    }

    @Override
    protected void onInput(String[] lines) {
        float num = 0;
        try {
            String result = lines[0];
            num = Float.parseFloat(result);
        }catch (NumberFormatException | NullPointerException e){
            Logging.message(super.gui.getOwner(),"You must enter a Float! (Float is a small precision decimal number (decimal number examples: 0.5, 3.14..), it is almost the same as double but one thing- it can have less numbers after the decimal point)", LoggingOptions.ERROR);
        }
        setValue(num);
        super.gui.prev();
    }

    @Override
    public ItemStack getDefaultDisplayItem() {
        return ItemStackUtil.newItemStack(Material.YELLOW_STAINED_GLASS_PANE, ChatColor.YELLOW+ getKeyAsDisplay());
    }

    @Override
    public TruePrimitive clone() {
        return new TPri_Float();
    }
}
