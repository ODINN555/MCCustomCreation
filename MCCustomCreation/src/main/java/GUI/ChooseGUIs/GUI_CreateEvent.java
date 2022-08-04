package GUI.ChooseGUIs;


import GUI.DisplayGUI.GUI_DisplayEvent;
import GUI.GUIAtrriutes.ChainGUI.IChainable;
import GUI.GUIAtrriutes.ListGUI.ListableGUI;
import Nodes.Events.EventInstance;
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
     * The creation to create events for
     */
    private String creation;
    /**
     *
     * @param events a given current state* map
     */
    public GUI_CreateEvent(Map<IEvent,List<FunctionTree>> events, String creation) {
        super(new ArrayList<>(NodesHandler.INSTANCE.getEvents().values()
                .stream()
                .map(event -> event.getItemReference().getItemStack())
                .collect(Collectors.toList())),"Edit Events for creation "+creation,7,0);
        if(events == null)
            events = new HashMap<>();
        this.events = events;
        this.creation = creation;
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
     *
     */
    private void onEventNodeClicked(NodeItemStack item){
        if(item == null)
            return;
        IEvent event = (IEvent) item.getClassRef();
        GUI_DisplayEvent gui;

        if(containsEvent(event.getKey()))
            gui = new GUI_DisplayEvent(getByName(event.getKey()),getKeyByName(event.getKey()));
        else{
            List<FunctionTree> list = new ArrayList<>();
            events.put(new EventInstance(event,false,this.creation),list);
            gui = new GUI_DisplayEvent(list,getKeyByName(event.getKey()));
        }


        this.next(gui,false);
    }

    /**
     *
     * @param name a given name
     * @return if the events contain an event with the given name
     */
    private boolean containsEvent(String name){
        if(name == null)
            return false;

        for (IEvent event : this.events.keySet())
            if(event != null && event.getKey().equalsIgnoreCase(name))
                return true;

            return false;
    }

    /**
     *
     * @param name a given name
     * @return the event value of the event with the given name
     */
    private List<FunctionTree> getByName(String name){
        for (IEvent iEvent : events.keySet())
            if(iEvent != null && iEvent.getKey().equalsIgnoreCase(name))
                return events.get(iEvent);
        return null;
    }

    /**
     *
     * @param name a given name
     * @return the key from this gui's events which matches the give name
     */
    private IEvent getKeyByName(String name){
        for(IEvent event : events.keySet())
            if(event != null && event.getKey().equalsIgnoreCase(name))
                return event;

            return null;
    }



}
