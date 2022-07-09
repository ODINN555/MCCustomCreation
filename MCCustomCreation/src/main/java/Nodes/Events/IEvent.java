package Nodes.Events;

import Nodes.INode;
import Nodes.NodeItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Locale;

/**
 * An interface for event nodes
 */
public interface IEvent extends INode {
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
}
