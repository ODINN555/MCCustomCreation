package Nodes.Primitives.TruePrimitives;

import Nodes.TruePrimitive;
import Utility.ItemStackUtil;
import Utility.Logging.Logging;
import Utility.Logging.LoggingOptions;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class TPri_String extends SignInputtedPrimitive {
    @Override
    public String getKey() {
        return "RAW_STRING";
    }

    @Override
    public Class getReturnType() {
        return String.class;
    }

    @Override
    public ItemStack getDefaultDisplayItem() {
        return ItemStackUtil.newItemStack(Material.PINK_STAINED_GLASS_PANE, ChatColor.LIGHT_PURPLE+getKeyAsDisplay());
    }

    @Override
    protected void onInput(String[] lines) {
        String str = "";
        if(lines == null || lines.length == 0)
            Logging.log("You must enter a String! (a String is anything that is placed under two quotes. like 'hello' for example. it could be number,sentence,letter. anything.)", LoggingOptions.ERROR);
        else for (int i = 0; i < lines.length; i++)
            str+=lines[i] == null ? "" : " "+lines[i];
        str = ChatColor.translateAlternateColorCodes('&',str);
        setValue(str);
        super.gui.prev();
    }

    @Override
    public TruePrimitive clone() {
        return new TPri_String();
    }
}
