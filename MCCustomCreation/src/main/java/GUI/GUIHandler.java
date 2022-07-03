package GUI;

import me.ODINN.MCCustomCreation.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Handles all custom GUIs of this plugin
 */
public class GUIHandler implements Listener {

    /**
     * Singleton implementation
     */
    public static final GUIHandler INSTANCE = new GUIHandler();
    private GUIHandler(){
        this.guis = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(this,Main.getInstance());
    }


    /**
     * GUIs map
     */
    private Map<UUID, AGUI> guis;

    /**
     * opens the given GUI for the given player
     * @param gui a given GUI
     * @param player a given player
     */
    public void openGUI(AGUI gui, Player player){
        this.guis.put(player.getUniqueId(),gui);
        gui.onOpening();
        player.openInventory(gui.getInventory());
    }

    /**
     * closes the GUI for the given player
     * @param player a given player
     */
    public void closeGUI(Player player){
        if(this.guis.containsKey(player.getUniqueId())) {
            this.guis.remove(player.getUniqueId()).onClosing();
            player.closeInventory();
        }
    }

    /**
     * closes the given GUI for it's owner
     * @param gui a given GUI
     */
    public void closeGUI(AGUI gui){
        if(gui.getOwner() == null)
            return;

        closeGUI(gui.getOwner());
    }

    /**
     * handles the given inventory close event
     * @param event a given inventory close event
     */
    @EventHandler
    public void onInvClose(InventoryCloseEvent event){
        if(guis.containsKey(event.getPlayer().getUniqueId()))
            guis.remove(event.getPlayer().getUniqueId()).onClosing();

    }

    /**
     * handles the given click event
     * @param event a given inventory click event
     */
    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(event.getInventory() != null && event.getClickedInventory() != null)
            if(guis.containsKey(event.getWhoClicked().getUniqueId()))
                guis.get(event.getWhoClicked().getUniqueId()).onClick(event);
    }

}
