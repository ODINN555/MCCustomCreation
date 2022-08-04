package Nodes.Events;

import Nodes.FunctionTree;
import Nodes.INode;
import Nodes.NodeItemStack;
import Utility.Logging.Logging;
import Utility.Logging.LoggingOptions;
import me.ODINN.MCCustomCreation.CreationsUtil;
import me.ODINN.MCCustomCreation.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Locale;

/**
 * An interface for event nodes
 */
public interface IEvent extends INode, Listener {

    /**
     * The default event node item material
     */
    Material DEFAULT_EVENT_MATERIAL = Material.RED_STAINED_GLASS_PANE;

    /**
     * The default event node item name color
     */
    ChatColor DEFAULT_NAME_COLOR = ChatColor.GOLD;

    /**
     *
     * @return the default node item of this event
     */
    default NodeItemStack getDefaultNodeItem(){
        String display = getKey().toLowerCase(Locale.ROOT);
        String[] arr = display.split("_");
        display = "";
        for (String s : arr)
            display += s.substring(0,1).toUpperCase() + s.substring(1) +" ";

        return new NodeItemStack(DEFAULT_EVENT_MATERIAL,DEFAULT_NAME_COLOR+display,null,1,this);
    }

    /**
     * executes the event
     * @param item a given item
     * @param executor a given executor entity
     */
    default void executeEvent(ItemStack item, LivingEntity executor, Event listenedEvent){
        if(CreationsUtil.isCreation(item)){
            String creationName = CreationsUtil.getCreationFromItem(item);
            if(!Main.getCreationsManager().isValid(creationName)) {
                Logging.log("Tried to execute a creation which is not valid. creation: "+creationName, LoggingOptions.ERROR);
                return;
            }
            List<FunctionTree> events = Main.getCreationsManager().getEventFromCreation(creationName,this);
            if(events != null)
            {
                NodeExecutionEvent event = new NodeExecutionEvent(this,listenedEvent,creationName);
                Bukkit.getPluginManager().callEvent(event);
                if(!event.isCancelled())
                for (FunctionTree functionTree : events) // execute all actions!
                        FunctionTree.executeFunction(functionTree,executor,item);
            }

        }
    }

}
