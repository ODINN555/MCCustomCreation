package me.ODINN.MCCustomCreation;

import Commands.*;
import Nodes.Actions.DefaultActions;
import Nodes.Events.DefaultEvents;
import Nodes.NodeEnum;
import Nodes.NodesHandler;
import Nodes.Parameters.DefaultParameters;
import Nodes.Primitives.DefaultPrimitives;
import Nodes.Primitives.TruePrimitives.Boolean.TPri_Boolean;
import Nodes.Primitives.TruePrimitives.EnumPrimitives.EnumPrimitives;
import Nodes.Primitives.TruePrimitives.*;
import Utility.ConfigUtil.ConfigHandler;
import Utility.ConfigUtil.NodeSavingManagers.FileManagersSelection;
import Utility.ConfigUtil.NodeSavingManagers.INodeFileManager;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Main extends JavaPlugin {

    /**
     * the plugin's instance
     */
    private static Main INSTANCE;

    /**
     * the plugin's file manager
     */
    private INodeFileManager FileManager;

    /**
     * the plugin's creations manager
     */
    private static CreationsManager CreationsManager;

    /**
     * the plugin's protocols manager
     */
    private static ProtocolManager ProtocolsManager;

    @Override
    public void onEnable(){
        INSTANCE = this;
        Thread.currentThread().setContextClassLoader(this.getClassLoader());
        registerDefaults();
        initConfigManagers();
        registerCommands();
        initProtocols();
    }

    @Override
    public void onDisable() {
        FileManager.saveAllCreations(CreationsManager.getCreationsMap());

    }

    /**
     * initializes protocols
     */
    private void initProtocols(){
        this.ProtocolsManager = ProtocolLibrary.getProtocolManager();
    }

    /**
     * initializes the config managers
     */
    private void initConfigManagers(){
        this.FileManager = FileManagersSelection.INSTANCE.getFileManager(
                (String) ConfigHandler.INSTANCE.
                        getSetting(ConfigHandler.Settings.FileManagerType)
        );

        this.CreationsManager = new CreationsManager(FileManager);
    }

    /**
     * registers default nodes
     */
    private void registerDefaults(){
        NodeEnum.registerDefaults(DefaultEvents.class);
        NodeEnum.registerDefaults(DefaultActions.class);
        NodeEnum.registerDefaults(DefaultParameters.class);
        NodeEnum.registerDefaults(DefaultPrimitives.class);
        EnumPrimitives.registerDefaults();
        NodesHandler.INSTANCE.register(
                new TPri_Integer(),
                new TPri_Boolean(),
                new TPri_String(),
                new TPri_Float(),
                new TPri_Double(),
                new TPri_Byte()

        );
    }

    /**
     * registers the plugin's commands
     */
    private void registerCommands(){
        CommandsHandler.INSTANCE.register(
                new CMD_Test(),
                new CMD_Create(),
                new CMD_Help(),
                new CMD_Remove(),
                new CMD_Duplicate()
        );
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return CommandsHandler.INSTANCE.onCommand(sender,command,label,args);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return CommandsHandler.INSTANCE.onTabComplete(sender, command, alias, args);
    }

    /**
     *
     * @return the plugin's creation manager
     */
    public static CreationsManager getCreationsManager() {
        return CreationsManager;
    }

    /**
     *
     * @return the plugin's instance
     */
    public static Main getInstance(){
        return INSTANCE;
    }

    /**
     *
     * @return the plugin's protocls manager
     */
    public static ProtocolManager getProtocolsManager(){
        return ProtocolsManager;
    }
}
