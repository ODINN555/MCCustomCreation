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

public class GUIHandler implements Listener {

    public static final GUIHandler INSTANCE = new GUIHandler();

    private Map<UUID,GUI> guis;

    private GUIHandler(){
        this.guis = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(this,Main.getInstance());
    }

    public void openGUI(GUI gui, Player player){
        this.guis.put(player.getUniqueId(),gui);
        player.openInventory(gui.getInventory());
    }

    public void closeGUI(Player player){
        if(!this.guis.containsKey(player.getUniqueId()))
            return;

        player.closeInventory();
        this.guis.remove(player.getUniqueId());
    }

    public void closeGUI(GUI gui){
        if(gui.getOwner() == null)
            return;

        closeGUI(gui.getOwner());
    }

    @EventHandler
    public void onInvClose(InventoryCloseEvent e){
        if(guis.containsKey(e.getPlayer().getUniqueId()))
            guis.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(event.getInventory() == null || event.getClickedInventory() == null)
            return;

        if(guis.containsKey(event.getWhoClicked().getUniqueId()))
            guis.get(event.getWhoClicked().getUniqueId()).onClick(event);
    }

}
