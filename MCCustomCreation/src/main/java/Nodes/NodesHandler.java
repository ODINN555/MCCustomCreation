package Nodes;

import Nodes.Events.EventInstance;
import Nodes.Events.IEvent;
import Utility.ConfigUtil.ConfigHandler;
import me.ODINN.MCCustomCreation.Main;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles nodes
 */
public class NodesHandler {

    /**
     * Singleton implementation
     */
    public static final NodesHandler INSTANCE = new NodesHandler();


    /**
     * Events map
     */
    private Map<String,IEvent> events;

    /**
     * Actions map
     */
    private Map<String,IAction> actionMap;

    /**
     * Parameter map
     */
    private Map<String,IParameter> parameterMap;

    /**
     * Primitives map
     */
    private Map<String,IPrimitive> primitiveMap;

    /**
     * Singleton implementation
     */
    private NodesHandler(){
        this.events = new HashMap<>();
        this.actionMap = new HashMap<>();
        this.parameterMap = new HashMap<>();
        this.primitiveMap = new HashMap<>();
    }

    /**
     * registers nodes
     * @param nodes given nodes
     */
    public void register(INode... nodes){
        for (INode node : nodes)
            register(node);
    }

    /**
     * registers the given node
     * @param obj a given node
     */
    public void register(INode obj){
        List<String> blacklist = (List<String>) ConfigHandler.INSTANCE.getSetting(ConfigHandler.Settings.NodeBlackList);
        if(blacklist != null && blacklist.contains(obj.getKey()))
            return;

        if(obj instanceof IAction)
            putInMap(actionMap,(IAction) obj);
        else if(obj instanceof IParameter)
            putInMap(parameterMap,(IParameter) obj);
        else if(obj instanceof IPrimitive)
            putInMap(primitiveMap,(IPrimitive) obj);
        else if(obj instanceof IEvent) {
            putInMap(events, (IEvent) obj);
            Bukkit.getPluginManager().registerEvents((IEvent) obj, Main.getInstance());
        }
    }

    /**
     * puts the given node in the given map
     * @param map a given map
     * @param obj a given node
     * @param <T> the node's type
     */
    private <T extends INode> void putInMap(Map<String,T> map,T obj){
        map.put(obj.getKey(),obj);
    }

    /**
     *
     * @return a copy of the registered events map
     */
    public Map<String, IEvent> getEvents() {
        return cloneMap(events);
    }

    /**
     *
     * @return a copy of the registered actions map
     */
    public Map<String, IAction> getActionMap() {
        return cloneMap(actionMap);
    }



    /**
     *
     * @return a copy of the registered parameters map
     */
    public Map<String, IParameter> getParameterMap() {
        return cloneMap(parameterMap);
    }

    /**
     * clones a map,
     * NOTE: only TruePrimitive is getting cloned here because cannot use clone() of Cloneable...
     * if needed, add any parameter you want to clone in this function
     * @param map a given map
     * @param <T> a node type
     * @return a new map containing the given map's objects with cloneable instances cloned
     */
    private <T extends INode> Map<String,T> cloneMap(Map<String,T> map){
        Map<String,T> newMap = new HashMap<>();
        for (String s : map.keySet())
            if(map.get(s) instanceof TruePrimitive) // add parameters here
                newMap.put(s, (T) ((TruePrimitive) map.get(s)).clone());
            else if(map.get(s) instanceof EventInstance)
                newMap.put(s, (T) ((EventInstance) map.get(s)).clone());
                else newMap.put(s,map.get(s));
        return newMap;
    }

    /**
     *
     * @return a copy of the registered primitives map
     */
    public Map<String, IPrimitive> getPrimitiveMap() {
        return new HashMap<>(primitiveMap);
    }

    /**
     *
     * @param name a given node name
     * @return an instance of the node with the given name
     */
    public INode getNodeByName(String name){

        //unique cases
        switch (name){
            case "EVENT_INSTANCE":
                return new EventInstance(null,false,null);
        }


        Map map = null;
        if(events.containsKey(name))
            map = events;
        else if(actionMap.containsKey(name))
            map = actionMap;
        else if(parameterMap.containsKey(name))
            map = parameterMap;
        else if(primitiveMap.containsKey(name))
            map = primitiveMap;

        if(map == null)
            return null;
        INode node = (INode) map.get(name);
        if(node instanceof TruePrimitive)
           return ((TruePrimitive) node).clone();
        return  node;
    }

    public Map<String,INode> getAllNodes(){
        Map<String,INode> nodes = new HashMap<>();
        nodes.putAll(getEvents());
        nodes.putAll(getParameterMap());
        nodes.putAll(getPrimitiveMap());
        nodes.putAll(getActionMap());
        return nodes;
    }

    public <T extends INode> Map<String,T> getNodesByType(Class<T> type){
        Map<String,T> filtered = new HashMap<>();
        getAllNodes().forEach((key,node) -> {
            if (type.isAssignableFrom(node.getClass()))
                filtered.put(key,(T) node);
        });
        return filtered;
    }
}
