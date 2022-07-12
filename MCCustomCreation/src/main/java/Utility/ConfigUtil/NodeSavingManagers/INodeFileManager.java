package Utility.ConfigUtil.NodeSavingManagers;

import Nodes.Events.IEvent;
import Nodes.FunctionTree;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

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
    boolean saveCreation(@NotNull String creationName, @Nullable List<FunctionTree> nodes);

    List<FunctionTree> retrieveCreation(@NotNull String name);

    Map<String,Map<IEvent,List<FunctionTree>>> retrieveAllCreations();

   default void saveCreations(Map<String,Map<IEvent,List<FunctionTree>>> creations){
       creations.forEach((name,nodes) -> {
           List<FunctionTree> events = nodes.keySet().stream()
                   .map(e -> new FunctionTree(e,nodes.get(e).toArray(new FunctionTree[0]),null))
                   .collect(Collectors.toList());
           saveCreation(name,events);
       });
   }


}
