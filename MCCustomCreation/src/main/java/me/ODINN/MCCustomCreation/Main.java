package me.ODINN.MCCustomCreation;

import Nodes.NodesHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main INSTANCE;

    @Override
    public void onEnable(){
        INSTANCE = this;
        NodesHandler.INSTANCE.register(
                //TODO default register
        );

    }

    public static Main getInstance(){
        return INSTANCE;
    }



}
