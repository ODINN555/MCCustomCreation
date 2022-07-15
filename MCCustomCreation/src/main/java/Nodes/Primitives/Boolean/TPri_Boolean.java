package Nodes.Primitives.Boolean;

import GUI.ChooseGUIs.GUI_ChooseGUI;
import Nodes.TruePrimitive;
import Utility.ItemStackUtil;
import Utility.Logging.Logging;
import Utility.Logging.LoggingOptions;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TPri_Boolean extends TruePrimitive<Boolean> implements Listener {


    private GUI_ChooseGUI gui;
    public TPri_Boolean() {
        super();
    }


    @Override
    public String getKey() {
        return "TestBoolean";
    }

    @Override
    public void onChosen(GUI_ChooseGUI gui) {
        if(getValue() == null)
            setValue(false);
        this.gui = gui;
        gui.next(new BooleanGUI(this),true);

    }

    @Override
    public ItemStack getDefaultDisplayItem(){
        return ItemStackUtil.newItemStack(Material.RED_STAINED_GLASS_PANE,"Raw Boolean");
    }


    @Override
    public Class getReturnType() {
        return Boolean.class;
    }

    public void onValueChosen(boolean value){
        setValue(value);
    }
}
