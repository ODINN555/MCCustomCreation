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

/**
 * a YAML file manager
 */
public class YmlManager {

    /**
     * the plugin
     */
    private JavaPlugin plugin;

    /**
     * the config
     */
    private FileConfiguration dataConfig = null;

    /**
     * the file
     */
    private File configFile = null;

    /**
     * the file's name
     */
    private String name;

    /**
     *
     * @param plugin the plugin
     * @param name the file's name
     */
    public YmlManager(JavaPlugin plugin, String name) {
        this.plugin = plugin;
        if(name.contains(".yml"))
            name = name.replaceAll(".yml","");
        this.name = name;
        saveDefaultConfig();
    }

    /**
     * reloads the config
     */
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

    /**
     *
     * @return the config
     */
    public FileConfiguration getConfig() {
        if(this.dataConfig == null)
            reloadConfig();
        return 	this.dataConfig;

    }

    /**
     * saves the config
     */
    public void saveConfig() {
        if(this.dataConfig == null || this.configFile == null)
            return;

        try {
            this.getConfig().save(configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Error trying to save a config file: "+this.configFile, e);
        }
    }

    /**
     * saves the default config
     */
    public void saveDefaultConfig() {
        if(this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), getName()+".yml");

        if(!this.configFile.exists())
            this.plugin.saveResource(getName()+".yml", false);
    }


    public String getName() {
        return name;
    }

    /**
     * sets the given object in the given path in the config
     * @param path a given path
     * @param obj a given object
     * @return if the set was successful
     */
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

    /**
     * sets the given object in the given path in the config
     * @param path a given path
     * @param obj a given object
     * @return if the set was successful
     */
    public boolean set(String[] path, Object obj){
        return set(convertArrayToPath(path),obj);
    }

    /**
     *
     * @param path a given path
     * @return the retrieved object of the path
     */
    public Object get(String path){
        return getConfig().get(path);
    }

    /**
     *
     * @param path a given path
     * @return the retrieved object of the path
     */
    public Object get(String[] path){
        return getConfig().get(convertArrayToPath(path));
    }

    /**
     *
     * @param path a given path
     * @param clazz the class which presents the return type
     * @param <T> the return type
     * @return the retrieved object of the path
     */
    public <T> T get(String path,Class<T> clazz){
        return getConfig().getObject(path,clazz);
    }

    /**
     *
     * @param path a given array
     * @return the given path as a YAML string path
     */
    protected String convertArrayToPath(String[] path){

        String pathStr = "";
        for (String s : path)
            pathStr+=s+".";

        pathStr = pathStr.substring(0,pathStr.length()-1);
        return pathStr;
    }
}
