package Nodes.Events;

import Nodes.NodeItemStack;
import com.sun.istack.internal.Nullable;

import java.io.Serializable;

public enum DefaultEvents implements Serializable,IEvent {
    RIGHT_CLICK("RIGHT_CLICK", null),
    LEFT_CLICK("LEFT_CLICK",null),
    SHIFT_RIGHT_CLICK("SHIFT_RIGHT_CLICK",null) ,
    SHIFT_LEFT_CLICK("SHIFT_LEFT_CLICK",null)
    ;

    private String key;
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
