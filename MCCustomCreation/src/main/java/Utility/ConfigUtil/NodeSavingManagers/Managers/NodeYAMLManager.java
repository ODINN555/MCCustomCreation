package Utility.ConfigUtil.NodeSavingManagers.Managers;

import Nodes.Events.IEvent;
import Nodes.FunctionTree;
import Utility.ConfigUtil.NodeSavingManagers.INodeFileManager;
import Utility.ConfigUtil.YmlManager;
import me.ODINN.MCCustomCreation.Main;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;
import java.util.stream.Collectors;

public class NodeYAMLManager extends YmlManager implements INodeFileManager {
    /**
     * The nodes config file name
     */
    private static String NAME = "Creations";

    /**
     * the nodes configuration section key (father as if highest)
     */
    private static String FATHER_KEY = "CREATIONS";

    /**
     * Singleton implementation
     */
    public static final NodeYAMLManager INSTANCE = new NodeYAMLManager();
    private NodeYAMLManager() {
        super(Main.getInstance(), NodeYAMLManager.NAME);
    }

    @Override
    public boolean saveCreation(String creationName, List<FunctionTree> nodes){
        List<Map<String,Object>> list = new ArrayList<>();
        for (FunctionTree node : nodes)
            list.add(node.serialize());

        set(new String[]{FATHER_KEY,creationName},list);
        return true;
    }

    @Override
    public List<FunctionTree> retrieveCreation(String name) {

         List<Map<String,Object>> list = (List) getConfig().getList(FATHER_KEY+"."+name,null);
         List<FunctionTree> trees = new ArrayList<>();
         list.forEach(map -> {
             try {
                 trees.add(FunctionTree.deserialize(null,map));
             } catch (CloneNotSupportedException e) {
                 e.printStackTrace();
             }
         });
        return trees;
    }

    @Override
    public Map<String, Map<IEvent, List<FunctionTree>>> retrieveAllCreations() {
        Map<String, Map<IEvent, List<FunctionTree>>> map = new HashMap<>();
        ConfigurationSection section = getConfig().getConfigurationSection(FATHER_KEY);

        if(section == null)
            return null;

        Set<String> creations = section.getKeys(false);

        for (String creation : creations) {

            List<FunctionTree> tree = retrieveCreation(creation);
            Map<IEvent,List<FunctionTree>> event = new HashMap<>();

            for (FunctionTree functionTree : tree) {
                if(functionTree.getCurrent() != null && functionTree.getNext() != null)
                event.put((IEvent) functionTree.getCurrent(),
                        Arrays.stream(functionTree.getNext())
                                .collect(Collectors.toList()));
            }

            map.put(creation,event);
        }
        return map;
    }
}
