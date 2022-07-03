package me.ODINN.MCCustomCreation;

import Commands.CMD_Test;
import Nodes.NodesHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main INSTANCE;

    @Override
    public void onEnable(){
        INSTANCE = this;
        NodesHandler.INSTANCE.register(
                //TODO default register
        );
        Bukkit.getPluginCommand("test").setExecutor(new CMD_Test());

    }

    public static Main getInstance(){
        return INSTANCE;
    }



}
