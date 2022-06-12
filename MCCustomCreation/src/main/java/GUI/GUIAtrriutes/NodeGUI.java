package GUI.GUIAtrriutes;

import Nodes.NodeItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface NodeGUI extends Listener {

    void handleNodeClick(NodeItemStack node);




}
