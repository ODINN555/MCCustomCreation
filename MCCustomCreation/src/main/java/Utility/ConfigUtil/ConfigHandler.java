package Utility.ConfigUtil;

import me.ODINN.MCCustomCreation.Main;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class ConfigHandler extends YmlManager{
    private static final String FILE_NAME = "Config";
    public static String SETTINGS_KEY = "Settings";
    // Singleton implementation
    public static final ConfigHandler INSTANCE = new ConfigHandler();
    private ConfigHandler() {
        super(Main.getInstance(),FILE_NAME);
        for (Setting setting : Settings.getAllSettings())
            getConfig().addDefault(SETTINGS_KEY+"."+setting.Name,setting.DefaultValue);
    }

    public static class Settings{
        public static final Setting FileManagerType = new Setting("FileManagerType","YAML");


        public static List<Setting> getAllSettings(){
            return Arrays.asList(
              FileManagerType
            );
        }
    }

    private static class Setting{
        public final String Name;
        public final Object DefaultValue;

        public Setting(String name, Object defaultValue) {
            this.Name = name;
            this.DefaultValue = defaultValue;
        }
    }

    public Object getSetting(Setting setting){
        return this.get(new String[]{SETTINGS_KEY,setting.Name});
    }
}
