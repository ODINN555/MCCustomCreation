package Utility.ConfigUtil;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.logging.Level;

public class YmlManager {
    private JavaPlugin plugin;
    private FileConfiguration dataConfig = null;
    private File configFile = null;
    private String name;
    
    public YmlManager(JavaPlugin plugin, String name) {
        this.plugin = plugin;
        if(name.contains(".yml"))
            name = name.replaceAll(".yml","");
        this.name = name;
        saveDefaultConfig();
    }

    public void reloadConfig() {

        if(this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), getName()+".yml");

        this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);

        InputStream defaultStream = this.plugin.getResource(getName()+".yml");
        if(defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.dataConfig.setDefaults(defaultConfig);

        }
    }

    public FileConfiguration getConfig() {
        if(this.dataConfig == null)
            reloadConfig();
        return 	this.dataConfig;

    }

    public void saveConfig() {
        if(this.dataConfig == null || this.configFile == null)
            return;

        try {
            this.getConfig().save(configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Error trying to save a config file: "+this.configFile, e);
        }
    }

    public void saveDefaultConfig() {
        if(this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), getName()+".yml");

        if(!this.configFile.exists())
            this.plugin.saveResource(getName()+".yml", false);
    }


    public String getName() {
        return name;
    }

    public boolean set(String path, Object obj){
        if(getConfig() == null)
            return false;

        try {
            getConfig().set(path, obj);
            saveConfig();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean set(String[] path, Object obj){
        String pathStr = "";
        for (String s : path)
            pathStr+=s+".";

        pathStr = pathStr.substring(0,pathStr.length()-1);

        return set(pathStr,obj);
    }

    public Object get(String path){
        return getConfig().get(path);
    }

    public <T> T get(String path,Class<T> clazz){
        return getConfig().getObject(path,clazz);
    }

    protected String convertArrayToPath(String[] path){

        String pathStr = "";
        for (String s : path)
            pathStr+=s+".";

        pathStr = pathStr.substring(0,pathStr.length()-1);
        return pathStr;
    }
}
