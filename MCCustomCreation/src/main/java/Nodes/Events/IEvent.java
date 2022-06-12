package Nodes.Events;

import Nodes.NodeItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Locale;

public interface IEvent {
    String getKey();
    NodeItemStack getItemReference();

    Material DEFAULT_EVENT_MATERIAL = Material.RED_STAINED_GLASS_PANE;
    ChatColor DEFAULT_NAME_COLOR = ChatColor.GOLD;
    ChatColor DEFAULT_DESCRIPTION_COLOR = ChatColor.GRAY;


    default NodeItemStack getDefault(){
        String display = getKey().toLowerCase(Locale.ROOT);
        String[] arr = display.split("_");
        display = "";
        for (String s : arr)
            display += s.substring(0,1).toUpperCase() + s.substring(1) +" ";

        return new NodeItemStack(DEFAULT_EVENT_MATERIAL,DEFAULT_NAME_COLOR+display,null,1,IEvent.class);
    }
}
