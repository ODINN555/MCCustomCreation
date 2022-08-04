package Nodes.Primitives.TruePrimitives.Boolean;

import GUI.ChooseGUIs.GUI_ChooseGUI;
import Nodes.TruePrimitive;
import Utility.ItemStackUtil;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class TPri_Boolean extends TruePrimitive<Boolean> implements Listener {


    public TPri_Boolean() {
        super(false);
    }


    @Override
    public String getKey() {
        return "RAW_BOOLEAN";
    }

    @Override
    public void onChosen(GUI_ChooseGUI gui) {
        if(getValue() == null)
            setValue(false);
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

    /**
     * handles a choose of value for this node
     * @param value a given chosen value
     */
    public void onValueChosen(boolean value){
        setValue(value);
    }

    @Override
    public TruePrimitive clone() {
        return new TPri_Boolean();
    }
}
