package me.ODINN.MCCustomCreation;

import Commands.CMD_Test;
import Nodes.NodesHandler;
import Utility.ConfigUtil.ConfigHandler;
import Utility.ConfigUtil.NodeSavingManagers.FileManagersSelection;
import Utility.ConfigUtil.NodeSavingManagers.INodeFileManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {

    private static Main INSTANCE;
    private INodeFileManager FileManager;
    private CreationsManager CreationsManager;
    @Override
    public void onEnable(){
        INSTANCE = this;
        registerDefaults();
        initConfigManagers();



        //TODO cmd handler
        Bukkit.getPluginCommand("test").setExecutor(new CMD_Test());

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
        );
    }












    public static Main getInstance(){
        return INSTANCE;
    }



}
