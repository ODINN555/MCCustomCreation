package Nodes.Primitives;

import GUI.ChooseGUIs.GUI_ChooseGUI;
import Nodes.TruePrimitive;
import Utility.ItemStackUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class TPri_Boolean extends TruePrimitive<Boolean> {

    public TPri_Boolean() {
        super();
    }


    @Override
    public String getKey() {
        return "TestBoolean";
    }

    @Override
    public void onChosen(GUI_ChooseGUI gui) {
        gui.close();
    }

    @Override
    public ItemStack getDefaultDisplayItem(){
        return ItemStackUtil.newItemStack(Material.RED_STAINED_GLASS_PANE,"Raw Boolean");
    }


    @Override
    public Class getReturnType() {
        return Boolean.class;
    }
}
