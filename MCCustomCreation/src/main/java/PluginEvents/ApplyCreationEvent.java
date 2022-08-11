package PluginEvents;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class ApplyCreationEvent extends Event implements Cancellable {

    /**
     * The event's handlers
     */
    private static final HandlerList HANDLERS = new HandlerList();

    /**
     * If the event is cancelled
     */
    private boolean isCancelled;

    /**
     * The creation being applied
     */
    private String creation;

    /**
     * The item which the creation is being applied to
     */
    private ItemStack item;

    /**
     *
     * @param creation a given creation
     * @param item a given item
     */
    public ApplyCreationEvent(String creation, ItemStack item) {
        this.creation = creation;
        this.item = item;
    }


    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCancelled = b;
    }

    public String getCreation() {
        return creation;
    }

    public void setCreation(String creation) {
        this.creation = creation;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }
}
