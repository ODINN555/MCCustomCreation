package Nodes.Events;
import Nodes.SerializedEvent;
import Utility.PDCUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class NodesEventHandler implements Listener {



    @EventHandler
    public void onClick(PlayerInteractEvent event){
        ItemStack item = event.getItem();
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
            if(event.getPlayer().isSneaking()) {
                if (PDCUtil.has(item, DefaultEvents.SHIFT_RIGHT_CLICK.getKey(), SerializedEvent.class))
                    PDCUtil.get(item, DefaultEvents.SHIFT_RIGHT_CLICK.getKey(), SerializedEvent.class).execute(event.getPlayer(), event.getItem());
            }else if(PDCUtil.has(item, DefaultEvents.RIGHT_CLICK.getKey(), SerializedEvent.class))
                            PDCUtil.get(item, DefaultEvents.RIGHT_CLICK.getKey(), SerializedEvent.class).execute(event.getPlayer(),event.getItem());

        if(event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK))
            if(event.getPlayer().isSneaking()) {
                if (PDCUtil.has(item, DefaultEvents.SHIFT_LEFT_CLICK.getKey(), SerializedEvent.class))
                    PDCUtil.get(item, DefaultEvents.SHIFT_LEFT_CLICK.getKey(), SerializedEvent.class).execute(event.getPlayer(), event.getItem());
            }else if(PDCUtil.has(item, DefaultEvents.LEFT_CLICK.getKey(), SerializedEvent.class))
                PDCUtil.get(item, DefaultEvents.LEFT_CLICK.getKey(), SerializedEvent.class).execute(event.getPlayer(),event.getItem());
    }
}
