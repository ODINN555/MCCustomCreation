package Utility.ConfigUtil.NodeSavingManagers;

import Nodes.Events.IEvent;
import Nodes.FunctionTree;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface INodeFileManager {

    /**
     * saves a creation in the file
     * @param creationName a given creation name
     * @param nodes a map containing the creation's events and their responding nodes tree
     * @return if the save was successful
     */
    boolean saveCreation( String creationName,  List<FunctionTree> nodes);

    /**
     *
     * @param name a given name
     * @return retrieve the creation with the given name
     */
    List<FunctionTree> retrieveCreation( String name);

    /**
     * retrieves all the creations
     * @return all creations
     */
    Map<String,Map<IEvent,List<FunctionTree>>> retrieveAllCreations();

    /**
     * saves the given creations
     * @param creations given creations
     */
   default void saveAllCreations(Map<String,Map<IEvent,List<FunctionTree>>> creations){
       clearCreations();
       creations.forEach((name,nodes) -> {
           List<FunctionTree> events = nodes.keySet().stream()
                   .map(e -> new FunctionTree(e,nodes.get(e).toArray(new FunctionTree[0]),null))
                   .collect(Collectors.toList());
           saveCreation(name,events);
       });
   }

    /**
     * deletes (clears) all creations from the file
     */
   void clearCreations();


}
