package me.ODINN.MCCustomCreation;


import GUI.DisplayGUI.DisplayType;
import GUI.DisplayGUI.DisplayTypesHandler;
import Nodes.NodesHandler;
import Utility.ConfigUtil.Serialization.Serializations;
import Utility.ConfigUtil.Serialization.Serializer;

/**
 * This class is for developers utility, it holds all the functions the developers need to use
 */
public class CustomCreations {

    /**
     * The plugin's node handler
     */
    public static NodesHandler NodesHandler = Nodes.NodesHandler.INSTANCE;

    /**
     * registers the given display type
     * @param type a given display type
     */
    public static void registerDisplayType(DisplayType type){
        DisplayTypesHandler.INSTANCE.register(type);
    }

    /**
     * registers a serializer of the given type
     * @param type a given class type
     * @param serializer a given serializer
     * @param <T> the type
     */
    public static <T> void registerSerializer(Class<T> type, Serializer<T> serializer){
        Serializations.registerSerializer(type,serializer);
    }



}
