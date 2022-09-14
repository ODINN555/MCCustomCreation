package me.ODINN.MCCustomCreation;

import Nodes.Events.IEvent;
import Nodes.FunctionTree;
import Utility.ConfigUtil.NodeSavingManagers.INodeFileManager;
import Utility.Logging.Logging;
import Utility.Logging.LoggingOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * manages creations
 */
public class CreationsManager {

    /**
     * creations registry map
     */
    private Map<String, Map<IEvent, List<FunctionTree>>> creations;

    /**
     * the manager's file manager
     */
    private INodeFileManager fileManager;

    /**
     *
     * @param fileManager the manager's file manager
     */
    public CreationsManager(INodeFileManager fileManager){
        creations = new HashMap<>();
        this.fileManager = fileManager;
        init();
    }

    /**
     * initializes the creations for the manager
     */
    private void init(){
        if(fileManager == null) {
            Logging.log("File manager is null, so could not initialize the CreationsManager.", LoggingOptions.ERROR);
            return;
        }
        Map<String, Map<IEvent, List<FunctionTree>>> retrieved = fileManager.retrieveAllCreations();
        if(retrieved != null)
            creations = new HashMap<>(retrieved);
        else creations = new HashMap<>();
    }

    /**
     *
     * @param name a given name
     * @return a creation with the given name
     */
    public Map<IEvent,List<FunctionTree>> getCreation(String name){
        return creations.get(name);
    }

    /**
     *
     * @param name a given name
     * @return the removed creation with the given name
     */
    public Map<IEvent,List<FunctionTree>> removeCreation(String name){
        return creations.remove(name);
    }

    /**
     * sets the creation with the given name and value
     * @param name a given name
     * @param creation the creation's value
     */
    public void setCreation(String name,Map<IEvent,List<FunctionTree>> creation){
        creations.put(name,creation);
    }

    /**
     *
     * @param name a given name
     * @param event a given event
     * @return the event's value of the given creation
     */
    public List<FunctionTree> getEventFromCreation(String name,IEvent event){
        List<FunctionTree> result = null;
        for (IEvent iEvent : creations.get(name).keySet())
            if(iEvent != null && iEvent.compareTo(event) == 0)
                result = creations.get(name).get(iEvent);

            return result;
    }

    /**
     *
     * @param name a given name
     * @param other a given event instance
     * @return the event of the creation with the given name which is similar to the given event instance
     */
    private IEvent getMapEventByEvent(String name,IEvent other){
        for (IEvent iEvent : creations.get(name).keySet())
            if(iEvent != null && iEvent.compareTo(other) == 0)
                return iEvent;
            return null;
    }

    /**
     *
     * @return the creation name list
     */
    public List<String> getCreationList(){
        return creations.keySet().stream().collect(Collectors.toList());
    }

    /**
     *
     * @return a new instance of the creations registry map
     */
    public Map<String, Map<IEvent, List<FunctionTree>>> getCreationsMap(){
        return creations;
    }

    /**
     *
     * @param creation a given creation
     * @return if the given creation is valid to execute
     */
    public boolean isValid(String creation){
        if(getCreation(creation ) == null)
            return false;

        Map<IEvent,List<FunctionTree>> map = getCreation(creation);
        for (IEvent event : map.keySet()) {
            List<FunctionTree> list = map.get(event);
            for (FunctionTree functionTree : list)
                if(functionTree == null || !functionTree.isValid()) {
                    Logging.log("There is an invalid Event on creation "+creation+". Event: "+event.getKey()+". FunctionTree" +functionTree,LoggingOptions.ERROR);
                    return false;
                }
        }
        return true;
    }


}
