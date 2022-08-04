package Utility.ConfigUtil;

import me.ODINN.MCCustomCreation.Main;

import java.util.Arrays;
import java.util.List;

public class ConfigHandler extends YmlManager{

    /**
     * the config's file name
     */
    private static final String FILE_NAME = "Config";

    /**
     * the config's settings configuration section key
     */
    public static String SETTINGS_KEY = "Settings";

    // Singleton implementation
    public static final ConfigHandler INSTANCE = new ConfigHandler();
    private ConfigHandler() {
        super(Main.getInstance(),FILE_NAME);
        for (Setting setting : Settings.getAllSettings())
            if(get(new String[]{SETTINGS_KEY,setting.Name}) == null)
                set(new String[]{SETTINGS_KEY,setting.Name},setting.DefaultValue);


    }

    /**
     * the settings class, holds all the settings
     */
    public static class Settings{
        /**
         * FileManagerType - String, the file type of a manager which the plugin will use
         */
        public static final Setting FileManagerType = new Setting("FileManagerType","YAML");

        /**
         * MessageLogging - Boolean, if messaging should be logged as well
         */
        public static final Setting MessageLogging = new Setting("MessageLogging",true);

        /**
         *
         * @return all the settings
         */
        public static List<Setting> getAllSettings(){
            return Arrays.asList(
                    FileManagerType,
                    MessageLogging
            );
        }
    }

    /**
     * an object which presents a setting
     */
    private static class Setting{
        /**
         * the setting's name
         */
        public final String Name;

        /**
         * the setting's default value
         */
        public final Object DefaultValue;

        /**
         *
         * @param name the setting's name
         * @param defaultValue the setting's default value
         */
        public Setting(String name, Object defaultValue) {
            this.Name = name;
            this.DefaultValue = defaultValue;
        }
    }

    /**
     *
     * @param setting a given setting
     * @return retrieves the given setting from the config
     */
    public Object getSetting(Setting setting){
        return this.get(new String[]{SETTINGS_KEY,setting.Name});
    }
}
