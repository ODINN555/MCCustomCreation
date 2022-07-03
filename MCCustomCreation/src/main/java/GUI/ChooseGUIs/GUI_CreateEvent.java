package GUI.ChooseGUIs;


import GUI.DisplayGUI.GUI_DisplayEvent;
import GUI.GUIAtrriutes.ChainGUI.IChainable;
import GUI.GUIAtrriutes.ListGUI.ListableGUI;
import Nodes.Events.IEvent;
import Nodes.FunctionTree;
import Nodes.NodeItemStack;
import Nodes.NodesHandler;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A GUI for modifying/creating an event's actions
 */
public class GUI_CreateEvent extends ListableGUI implements IChainable {

    /**
     * a map containing all the actions of each event that are present in the current state*
     *
     * *state - The state of the gui, what the gui is choosing and creating for.
     *          it could be an item or entity or anything else.
     */
    Map<IEvent,List<FunctionTree>> events;

    /**
     *
     * @param events a given current state* map
     */
    public GUI_CreateEvent(Map<IEvent,List<FunctionTree>> events) {
        super(new ArrayList<>(NodesHandler.INSTANCE.getEvents().values()
                .stream()
                .map(event -> event.getItemReference().getItemStack())
                .collect(Collectors.toList())),"Edit Events",7,0);
        if(events == null)
            events = new HashMap<>();
        this.events = events;
    }


    @Override
    public void onClosing() {
        IChainable.super.onClosing();
    }

    @Override
    protected void onClick(InventoryClickEvent event){
        if(event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR))
            return;
        event.setCancelled(true); // no item should be grabbed, then event should always be cancelled

        ItemStack currentItem = event.getCurrentItem();

        if(NodeItemStack.isNodeItemStack(currentItem))
            onEventNodeClicked(NodeItemStack.getNodeFromItem(currentItem));

    }

    /**
     * on event node being clicked in the GUI
     * @param item The clicked item node
     */
    private void onEventNodeClicked(NodeItemStack item){
        if(item == null)
            return;
        IEvent event = (IEvent) item.getClassRef();
        GUI_DisplayEvent gui;
        if(containsEvent(event.getKey()))
            gui = new GUI_DisplayEvent(getByName(event.getKey()),event);
        else gui = new GUI_DisplayEvent(event);
        //TODO register current build to chosen event


        this.next(gui,false);
    }
    //TODO move to somewhere else
    private boolean containsEvent(String name){
        if(name == null)
            return false;

        for (IEvent event : this.events.keySet())
            if(event.getKey().equalsIgnoreCase(name))
                return true;

            return false;
    }

    //TODO move to somewhere else
    private List<FunctionTree> getByName(String name){
        for (IEvent iEvent : events.keySet())
            if(iEvent.getKey().equalsIgnoreCase(name))
                return events.get(iEvent);
        return null;
    }




}
