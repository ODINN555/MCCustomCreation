package Utility.ConfigUtil;

import me.ODINN.MCCustomCreation.Main;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigHandler extends YmlManager{
    private static final String FILE_NAME = "Config";
    public static String SETTINGS_KEY = "Settings";
    // Singleton implementation
    public static final ConfigHandler INSTANCE = new ConfigHandler();
    private ConfigHandler() {
        super(Main.getInstance(),FILE_NAME);
    }

    public static class Settings{
        public static final Setting FileManagerType = new Setting("FileManagerType","YAML");
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
