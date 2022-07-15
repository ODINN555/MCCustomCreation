package Utility.ConfigUtil.NodeSavingManagers;

import Utility.ConfigUtil.NodeSavingManagers.Managers.NodeYAMLManager;

import java.util.HashMap;
import java.util.Map;

public class FileManagersSelection {
    /**
     * file managers registry map
     */
    private static Map<String, INodeFileManager> managers = new HashMap<>();

    /**
     * the default file manager
     */
    private static final String DEFAULT = "YAML";

    /**
     * Singleton implementation
     */
    public static final FileManagersSelection INSTANCE = new FileManagersSelection();
    private FileManagersSelection(){
        registerDefaults();
    }

    /**
     * register the default file managers
     */
    private void registerDefaults(){
        register("YAML", NodeYAMLManager.INSTANCE);
    }

    /**
     * registers the given file manager
     * @param type a given file type
     * @param fileManager a given file manager
     */
    public void register(String type,INodeFileManager fileManager){
        managers.put(type,fileManager);
    }

    /**
     *
     * @param type a given file type
     * @return the file manager of the given file type
     */
    public INodeFileManager getFileManager(String type){
        return managers.containsKey(type) ? managers.get(type) : managers.get(DEFAULT);
    }



}
