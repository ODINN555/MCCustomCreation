package Nodes;

import Nodes.Events.IEvent;
import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodesHandler {

    public static final NodesHandler INSTANCE = new NodesHandler();

    private Map<String,IEvent> events;
    private Map<String,IAction> actionMap;
    private Map<String,IParameter> parameterMap;
    private Map<String,IPrimitive> primitiveMap;

    private NodesHandler(){
        this.events = new HashMap<>();
        this.actionMap = new HashMap<>();
        this.parameterMap = new HashMap<>();
        this.primitiveMap = new HashMap<>();
    }

    public void register(Object... nodes){
        for (Object node : nodes)
            register(node);
    }

    public void register(Object obj){
        if(obj instanceof IAction){
            IAction action =  (IAction) obj;
            actionMap.put(action.getClass().getSimpleName(),action);
        }else if(obj instanceof IParameter){
           IParameter param =  (IParameter) obj;
            parameterMap.put(param.getClass().getSimpleName(),param);
        }else if(obj instanceof IPrimitive){
            IPrimitive prim =  (IPrimitive) obj;
            primitiveMap.put(prim.getClass().getSimpleName(),prim);
        }else if(obj instanceof IEvent){
            IEvent event = (IEvent) obj;
            events.put(event.getKey(),event);
        }
    }

    public Map<String, IEvent> getEvents() {
        return new HashMap<>(events);
    }

    public Map<String, IAction> getActionMap() {
        return new HashMap<>(actionMap);
    }

    public Map<String, IParameter> getParameterMap() {
        return new HashMap<>(parameterMap);
    }

    public Map<String, IPrimitive> getPrimitiveMap() {
        return new HashMap<>(primitiveMap);
    }
}
