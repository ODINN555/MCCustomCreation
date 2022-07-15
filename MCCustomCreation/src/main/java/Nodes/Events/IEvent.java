package Nodes.Events;

import Nodes.FunctionTree;
import Nodes.INode;
import Nodes.NodeItemStack;
import Utility.Logging.Logging;
import Utility.Logging.LoggingOptions;
import Utility.PDCUtil;
import me.ODINN.MCCustomCreation.CreationsManager;
import me.ODINN.MCCustomCreation.CreationsUtil;
import me.ODINN.MCCustomCreation.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
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
     * The default event node item description color
     */
    ChatColor DEFAULT_DESCRIPTION_COLOR = ChatColor.GRAY;

    /**
     *
     * @return the default node item of this event
     */
    default NodeItemStack getDefault(){
        String display = getKey().toLowerCase(Locale.ROOT);
        String[] arr = display.split("_");
        display = "";
        for (String s : arr)
            display += s.substring(0,1).toUpperCase() + s.substring(1) +" ";

        return new NodeItemStack(DEFAULT_EVENT_MATERIAL,DEFAULT_NAME_COLOR+display,null,1,this);
    }

    default void executeEvent(ItemStack item, LivingEntity executor){
        if(CreationsUtil.isCreation(item)){
            String creationName = CreationsUtil.getCreationFromItem(item);
            if(!Main.getCreationsManager().isValid(creationName)) {
                Logging.log("Tried to execute a creation which is not valid. creation: "+creationName+"\nFunctions: "+Main.getCreationsManager().getCreation(creationName), LoggingOptions.ERROR);
                return;
            }
            List<FunctionTree> event = Main.getCreationsManager().getEventFromCreation(creationName,this);
            if(event != null)
            {

                for (FunctionTree functionTree : event) // execute all actions!
                        FunctionTree.executeFunction(functionTree,executor,item);
            }

        }
    }
}
