package Utility.Logging;

import Utility.ConfigUtil.ConfigHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * a static class for logging
 */
public class Logging {
    private static final String PluginPrefix = "{McCustomCreation}";
    public static final boolean MessageLogging = (boolean) ConfigHandler.INSTANCE.getSetting(ConfigHandler.Settings.MessageLogging);

    /**
     * logs the given message
     * @param msg a given msg
     * @param option a given options
     */
    public static void log(String msg,LoggingOptions option){
        System.out.println(buildMsg(msg,option.getPrefix(),option.getColor()));
    }

    /**
     *
     * @param msg a given msg
     * @param prefix a given prefix
     * @param color a given color
     * @return the msg built with the color and prefix
     */
    private static String buildMsg(String msg, String prefix, ChatColor color){
        return color + PluginPrefix + " "+prefix+": "+msg;
    }

    /**
     * messages the player the given message with the given log option
     * @param player a given player
     * @param message a given message
     * @param option a given option
     */
    public static void message(Player player,String message, LoggingOptions option){
        player.sendMessage(option.getColor() + message);
        if(MessageLogging)
            log(message,option);
    }

}
