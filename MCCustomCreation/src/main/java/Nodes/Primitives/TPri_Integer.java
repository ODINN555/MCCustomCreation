package Nodes.Primitives;

import GUI.ChooseGUIs.GUI_ChooseGUI;
import Nodes.TruePrimitive;
import Utility.ItemStackUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class TPri_Integer extends TruePrimitive<Integer> {


    public TPri_Integer() {
        super();
    }


    @Override
    public ItemStack getDefaultDisplayItem() {
        return ItemStackUtil.newItemStack(Material.GREEN_STAINED_GLASS_PANE,"Raw Integer");
    }

    @Override
    public String getKey() {
        return "TestInt";
    }

    @Override
    public void onChosen(GUI_ChooseGUI gui) {
        gui.close();
    }

    @Override
    public Class getReturnType() {
        return Integer.class;
    }
}
