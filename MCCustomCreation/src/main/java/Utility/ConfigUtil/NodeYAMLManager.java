package Utility.ConfigUtil;

import Nodes.Events.IEvent;
import Nodes.FunctionTree;
import Nodes.TruePrimitive;
import me.ODINN.MCCustomCreation.Main;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeYAMLManager extends YmlManager implements INodeFileManager{
    /**
     * The nodes config file name
     */
    private static String NAME = "Creations";

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



}
