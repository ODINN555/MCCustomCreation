package GUI.DisplayGUI;

import org.bukkit.inventory.ItemStack;

/**
 * An interface for objects which are displayed on a GUI
 */
public interface DisplayType {

    /**
     *
     * @return The displayed item
     */
    ItemStack getDisplayItem();

    Class getType();
}
