package Nodes.Primitives.TruePrimitives;

import Nodes.TruePrimitive;
import Utility.ItemStackUtil;
import Utility.Logging.Logging;
import Utility.Logging.LoggingOptions;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class TPri_Byte extends SignInputtedPrimitive {
    @Override
    public String getKey() {
        return "RAW_BYTE";
    }

    @Override
    public Class getReturnType() {
        return Byte.class;
    }

    @Override
    protected void onInput(String[] lines) {
        byte num = 0;
        try {
            String result = lines[0];
            num = Byte.parseByte(result);
        }catch (NumberFormatException | NullPointerException e){
            Logging.message(super.gui.getOwner(),"You must enter a Byte! (Byte is a very small Integer, its range is from -128 to 127)", LoggingOptions.ERROR);
        }
        setValue(num);
        super.gui.prev();
    }

    @Override
    public ItemStack getDefaultDisplayItem() {
        return ItemStackUtil.newItemStack(Material.GREEN_STAINED_GLASS_PANE, ChatColor.GREEN+getKeyAsDisplay());
    }

    @Override
    public TruePrimitive clone() {
        return new TPri_Byte();
    }
}
