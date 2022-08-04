package Nodes.Primitives.TruePrimitives.Boolean;

import GUI.AGUI;
import GUI.GUIAtrriutes.ChainGUI.IChainable;
import Utility.ItemStackUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BooleanGUI extends AGUI implements IChainable {

    /**
     * the true value item reference
     */
    private final ItemStack trueItem = ItemStackUtil.newItemStack(Material.GREEN_STAINED_GLASS_PANE, ChatColor.GREEN+"True");

    /**
     * the true value item slot
     */
    private final int trueSlot = 12;

    /**
     * the false value item reference
     */
    private final ItemStack falseItem = ItemStackUtil.newItemStack(Material.RED_STAINED_GLASS_PANE, ChatColor.RED+"False");

    /**
     * the false value item slot
     */
    private final int falseSlot = 14;

    /**
     * the boolean true primitive instance
     */
    private TPri_Boolean instance;

    /**
     *
     * @param instance the gui's true primitive instance
     */
    public BooleanGUI(TPri_Boolean instance){
        super();
        this.instance = instance;
    }

    @Override
    protected Inventory initInventory() {
        Inventory inv = Bukkit.createInventory(null,27,"Raw Boolean");
        inv.setItem(trueSlot,trueItem);
        inv.setItem(falseSlot,falseItem);
        return inv;
    }

    @Override
    protected void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        if(event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR))
            return;
        if(event.getSlot() == trueSlot)
            instance.onValueChosen(true);
        if(event.getSlot() == falseSlot)
            instance.onValueChosen(false);
        prev();
    }

    @Override
    public void onClosing() {
        IChainable.super.onClosing();
    }
}
