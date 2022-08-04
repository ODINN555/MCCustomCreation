package Nodes.Events;

import Nodes.NodeItemStack;
import me.ODINN.MCCustomCreation.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.Serializable;

/**
 * this class is an instance for event nodes. it wraps the IEvent node and holds values about it.
 */
public class EventInstance implements Serializable, IEvent , Cloneable, Listener {

    /**
     * The instanced event
     */
    private IEvent event;

    /**
     * If the listened event of the event node should be canceled
     */
    private boolean cancelled;

    /**
     * The creation which executes this instance
     */
    private String creation;

    /**
     *
     * @param event The instanced event
     * @param cancelled Is cancelled
     * @param creation The executor creation
     */
    public EventInstance(IEvent event, boolean cancelled,String creation) {
        this.event = event;
        this.cancelled = cancelled;
        this.creation = creation;
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }

    /**
     *
     * @param event The instanced event
     * @param creation The executor creation
     */
    public EventInstance(IEvent event,String creation) {
        this(event,false,creation);
    }

    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public NodeItemStack getItemReference() {
        return this.event.getItemReference();
    }

    @Override
    public String getKey() {
        return this.event == null ? "EVENT_INSTANCE" : event.getKey();
    }

    @Override
    public String getDescription() {
        return this.event.getDescription();
    }


    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public EventInstance clone()  {
        return new EventInstance(this.event,this.cancelled,this.creation);
    }

    @EventHandler
    public void onNodeExecution(NodeExecutionEvent event){
        if(event.getEvent().equals(this.event) && event.getCreation().equalsIgnoreCase(this.creation))
            if (event.getListenedEvent() instanceof Cancellable)
                ((Cancellable) event.getListenedEvent()).setCancelled(cancelled);
    }
}
