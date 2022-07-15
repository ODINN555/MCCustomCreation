package me.ODINN.MCCustomCreation;

import Commands.CMD_Create;
import Commands.CMD_Help;
import Commands.CMD_Test;
import Commands.CommandsHandler;
import Nodes.Actions.ATest;
import Nodes.Events.DefaultEvents;
import Nodes.NodesHandler;
import Nodes.Primitives.Boolean.TPri_Boolean;
import Nodes.Primitives.TPri_Integer;
import Utility.ConfigUtil.ConfigHandler;
import Utility.ConfigUtil.NodeSavingManagers.FileManagersSelection;
import Utility.ConfigUtil.NodeSavingManagers.INodeFileManager;
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

    @Override
    public void onEnable(){
        INSTANCE = this;
        registerDefaults();
        initConfigManagers();
        registerCommands();
    }

    @Override
    public void onDisable() {
        FileManager.saveCreations(CreationsManager.getCreationsMap());

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
        NodesHandler.INSTANCE.register(
                DefaultEvents.RIGHT_CLICK,
                new ATest(),
                new TPri_Integer(),
                new TPri_Boolean()
        );
    }

    /**
     * registers the plugin's commands
     */
    private void registerCommands(){
        CommandsHandler.INSTANCE.register(
                new CMD_Test(),
                new CMD_Create(),
                new CMD_Help()
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
}
