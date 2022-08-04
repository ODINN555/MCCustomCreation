package Nodes.Events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


/**
 * An event which is called when an event node is being executed
 */
public class NodeExecutionEvent extends Event implements Cancellable {

    /**
     * The event's handlers
     */
    private static final HandlerList HANDLERS = new HandlerList();

    /**
     * If the event is cancelled
     */
    private boolean isCancelled;

    /**
     * The event node executed
     */
    private IEvent event;

    /**
     * The listened event of the event node
     */
    private Event listenedEvent;

    /**
     * The creation which executes the event node
     */
    private String creation;

    /**
     *
     * @param event The event's event node
     * @param listenedEvent The event's event node listened event
     * @param creation The event's creation executor
     */
    public NodeExecutionEvent(IEvent event,Event listenedEvent,String creation){
        this.isCancelled = false;
        this.event = event;
        this.listenedEvent = listenedEvent;
        this.creation = creation;
    }


    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCancelled = b;
    }

    public IEvent getEvent() {
        return event;
    }

    public Event getListenedEvent() {
        return listenedEvent;
    }

    public String getCreation() {
        return creation;
    }
}
