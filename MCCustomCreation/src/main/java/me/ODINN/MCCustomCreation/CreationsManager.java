package me.ODINN.MCCustomCreation;

import Nodes.Events.IEvent;
import Nodes.FunctionTree;
import Utility.ConfigUtil.NodeSavingManagers.INodeFileManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CreationsManager {
    private Map<String, Map<IEvent, List<FunctionTree>>> creations;
    private INodeFileManager fileManager;

    public CreationsManager(INodeFileManager fileManager){
        creations = new HashMap<>();
        this.fileManager = fileManager;
        init();
    }

    private void init(){
        if(fileManager == null) //TODO error
            return;
        Map<String, Map<IEvent, List<FunctionTree>>> retrieved = fileManager.retrieveAllCreations();
        if(retrieved != null)
            creations = new HashMap<>(retrieved);
        else creations = new HashMap<>();
    }

    public Map<IEvent,List<FunctionTree>> getCreation(String name){
        return creations.get(name);
    }

    public Map<IEvent,List<FunctionTree>> removeCreation(String name){
        return creations.remove(name);
    }

    public void setCreation(String name,Map<IEvent,List<FunctionTree>> creation){
        creations.put(name,creation);
    }

    public void setEventInCreation(String name,IEvent event,List<FunctionTree> actions){


    }

    public List<FunctionTree> getEventFromCreation(String name,IEvent event){
        List<FunctionTree> result = null;
        for (IEvent iEvent : creations.get(name).keySet())
            if(iEvent != null && iEvent.compareTo(event) == 0)
                result = creations.get(name).get(iEvent);

            return result;
    }

    private IEvent getMapEventByEvent(String name,IEvent other){
        for (IEvent iEvent : creations.get(name).keySet())
            if(iEvent != null && iEvent.compareTo(other) == 0)
                return iEvent;
            return null;
    }

    public List<String> getCreationList(){
        return creations.keySet().stream().collect(Collectors.toList());
    }

    public Map<String, Map<IEvent, List<FunctionTree>>> getCreationsMap(){
        return new HashMap<>(creations);
    }

}
