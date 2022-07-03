package Nodes.Events;

import Nodes.NodeItemStack;
import com.sun.istack.internal.Nullable;

import java.io.Serializable;

/**
 * An enum for default events
 */
public enum DefaultEvents implements Serializable,IEvent {
    RIGHT_CLICK("RIGHT_CLICK", null),
    LEFT_CLICK("LEFT_CLICK",null),
    SHIFT_RIGHT_CLICK("SHIFT_RIGHT_CLICK",null) ,
    SHIFT_LEFT_CLICK("SHIFT_LEFT_CLICK",null)
    ;

    /**
     * The event's key
     */
    private String key;

    /**
     * The event's nodeItemStack
     */
    private NodeItemStack nodeItemStack;

    DefaultEvents(String key, @Nullable NodeItemStack nodeItemStack){
        this.key = key;
        this.nodeItemStack = nodeItemStack;
    }

    public String getKey(){
        return this.key;
    }

    @Override
    public NodeItemStack getItemReference(){
        return this.nodeItemStack == null ? getDefault() : this.nodeItemStack;
    }

}
