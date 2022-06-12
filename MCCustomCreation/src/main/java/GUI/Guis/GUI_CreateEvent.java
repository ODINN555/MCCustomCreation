package GUI.Guis;


import GUI.*;
import GUI.GUIAtrriutes.ChainGUI.ChainHandler;
import GUI.GUIAtrriutes.ChainGUI.IChainable;
import GUI.GUIAtrriutes.ListGUI.ListableGUI;
import Nodes.Events.IEvent;
import Nodes.NodesHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GUI_CreateEvent extends ListableGUI implements IChainable , Listener {

    private List<IEvent> eventList;

    public GUI_CreateEvent() {
        super(new ArrayList<>(NodesHandler.INSTANCE.getEvents().values().stream().map(event -> event.getItemReference().getItemStack()).collect(Collectors.toList())),"Create Event");
    }


    @Override
    public GUI getCurrentGUI() {
        return this;
    }

    protected void onClick(InventoryClickEvent event){
    //TODO
    }
}
