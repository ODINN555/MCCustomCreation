package me.ODINN.MCCustomCreation;

import Commands.CMD_Create;
import Commands.CMD_Test;
import Commands.CommandsHandler;
import Nodes.Actions.ATest;
import Nodes.Events.DefaultEvents;
import Nodes.Events.IEvent;
import Nodes.IAction;
import Nodes.NodesHandler;
import Nodes.Primitives.TPri_Boolean;
import Nodes.Primitives.TPri_Integer;
import Nodes.TruePrimitive;
import Utility.ConfigUtil.ConfigHandler;
import Utility.ConfigUtil.NodeSavingManagers.FileManagersSelection;
import Utility.ConfigUtil.NodeSavingManagers.INodeFileManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

public class Main extends JavaPlugin {

    private static Main INSTANCE;
    private INodeFileManager FileManager;
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

    private void initConfigManagers(){
        this.FileManager = FileManagersSelection.INSTANCE.getFileManager(
                (String) ConfigHandler.INSTANCE.
                        getSetting(ConfigHandler.Settings.FileManagerType)
        );

        this.CreationsManager = new CreationsManager(FileManager);
    }

    private void registerDefaults(){
        NodesHandler.INSTANCE.register(
                //TODO default register
                DefaultEvents.RIGHT_CLICK,
                new ATest(),
                new TPri_Integer(),
                new TPri_Boolean()
        );
    }

    private void registerCommands(){
        CommandsHandler.INSTANCE.register(
                new CMD_Test(),
                new CMD_Create()
        );
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return CommandsHandler.INSTANCE.onCommand(sender,command,label,args);
    }

    public static Main getInstance(){
        return INSTANCE;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return CommandsHandler.INSTANCE.onTabComplete(sender, command, alias, args);
    }

    public static CreationsManager getCreationsManager() {
        return CreationsManager;
    }
}
