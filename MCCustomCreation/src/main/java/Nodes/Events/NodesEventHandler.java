package Nodes.Events;

import me.ODINN.MCCustomCreation.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * nodes events handler
 */
public class NodesEventHandler implements Listener {
    /**
     * Singleton implementation
     */
    public static final NodesEventHandler INSTANCE = new NodesEventHandler();
    private NodesEventHandler(){
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }
    //TODO functionality

    @EventHandler
    public void onClick(PlayerInteractEvent event){
        /**ItemStack item = event.getItem();
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
            if(event.getPlayer().isSneaking()) {
                if (PDCUtil.has(item, DefaultEvents.SHIFT_RIGHT_CLICK.getKey(), String.class))
                    PDCUtil.get(item, DefaultEvents.SHIFT_RIGHT_CLICK.getKey(), String.class).execute(event.getPlayer(), event.getItem());
            }else if(PDCUtil.has(item, DefaultEvents.RIGHT_CLICK.getKey(), String.class))
                            PDCUtil.get(item, DefaultEvents.RIGHT_CLICK.getKey(), String.class).execute(event.getPlayer(),event.getItem());

        if(event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK))
            if(event.getPlayer().isSneaking()) {
                if (PDCUtil.has(item, DefaultEvents.SHIFT_LEFT_CLICK.getKey(), String.class))
                    PDCUtil.get(item, DefaultEvents.SHIFT_LEFT_CLICK.getKey(), String.class).execute(event.getPlayer(), event.getItem());
            }else if(PDCUtil.has(item, DefaultEvents.LEFT_CLICK.getKey(), String.class))
                PDCUtil.get(item, DefaultEvents.LEFT_CLICK.getKey(), String.class).execute(event.getPlayer(),event.getItem());
            **/
         }
}
