package Nodes.Primitives.TruePrimitives;

import Nodes.TruePrimitive;
import Utility.ItemStackUtil;
import Utility.Logging.Logging;
import Utility.Logging.LoggingOptions;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class TPri_Double extends SignInputtedPrimitive {
    @Override
    public String getKey() {
        return "RAW_DOUBLE";
    }

    @Override
    public Class getReturnType() {
        return Double.class;
    }

    @Override
    protected void onInput(String[] lines) {
        double num = 0;
        try {
            String result = lines[0];
            num = Double.parseDouble(result);
        }catch (NumberFormatException | NullPointerException e){
            Logging.message(super.gui.getOwner(),"You must enter a Double! (Double is a large precision decimal number (decimal number examples: 0.5, 3.14..), it is almost the same as float but one thing- it can have more numbers after the decimal point)", LoggingOptions.ERROR);
        }
        setValue(num);
        super.gui.prev();
    }

    @Override
    public ItemStack getDefaultDisplayItem() {
        return ItemStackUtil.newItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE, ChatColor.AQUA+getKeyAsDisplay());
    }

    @Override
    public TruePrimitive clone() {
        return new TPri_Double();
    }
}
