package Utility.ConfigUtil.NodeSavingManagers;

import Utility.ConfigUtil.NodeSavingManagers.Managers.NodeYAMLManager;

import java.util.HashMap;
import java.util.Map;

public class FileManagersSelection {

    private static Map<String, INodeFileManager> managers = new HashMap<>();
    private static final String DEFAULT = "YAML";
    //Singleton implementation
    public static final FileManagersSelection INSTANCE = new FileManagersSelection();
    private FileManagersSelection(){
        registerDefaults();
    }

    private void registerDefaults(){
        register("YAML", NodeYAMLManager.INSTANCE);
    }

    public void register(String type,INodeFileManager fileManager){
        managers.put(type,fileManager);
    }

    public INodeFileManager getFileManager(String type){
        return managers.containsKey(type) ? managers.get(type) : managers.get(DEFAULT);
    }



}
