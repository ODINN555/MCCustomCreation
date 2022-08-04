package Nodes.Primitives.TruePrimitives.EnumPrimitives;

import GUI.GUIAtrriutes.ChainGUI.IChainable;
import GUI.GUIAtrriutes.ListGUI.ListableGUI;
import Utility.PDCUtil;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EnumChooseGUI extends ListableGUI implements IChainable {

    /**
     * The persistent data container key of items for this gui
     */
    private static final String PDC_KEY = "CHOOSE_ENUM_GUI";

    /**
     * the gui's enum primitive instance
     */
    private EnumPrimitive primitive;

    /**
     * a map containing the constants and their string value
     */
    private Map<String,Object> constants;

    /**
     *
     * @param primitive the gui's enum primitive instance
     */
    public EnumChooseGUI(EnumPrimitive primitive) {
        super((List<ItemStack>) Arrays.stream(primitive.getConstants()).map(constant -> {
            ItemStack item = primitive.getDisplayOfConstant(Arrays.asList(primitive.getConstants()).indexOf(constant));
            PDCUtil.set(item,PDC_KEY,constant.toString());
            return item;
        }).collect(Collectors.toList()), "Choose A "+primitive.getEnumClass().getSimpleName());
        this.primitive = primitive;
        this.constants = convertToConstantsMap(primitive.getConstants());
    }




    @Override
    protected void onClick(InventoryClickEvent event) {
        super.onClick(event);
        event.setCancelled(true);
        ItemStack item = event.getCurrentItem();

        if(item == null || item.getType().equals(Material.AIR))
            return;


        if(PDCUtil.has(item,PDC_KEY))
            onEnumClicked(PDCUtil.get(item,PDC_KEY));


    }

    /**
     * handles a click of a constant
     * @param e a given constant
     */
    private void onEnumClicked(String e){
        primitive.setValue(constants.get(e));
        this.prev();
    }

    @Override
    public void onClosing() {
        IChainable.super.onClosing();
    }

    /**
     *
     * @param constants given constants list
     * @return a map containing the constants and their string values
     */
    private Map<String,Object> convertToConstantsMap(Object[] constants){
        Map<String,Object> map = new HashMap<>();
        for (Object constant : constants)
            map.put(constant.toString(),constant);
        return map;
    }
}
