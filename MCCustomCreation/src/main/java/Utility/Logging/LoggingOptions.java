package Utility.Logging;

import org.bukkit.ChatColor;

public enum LoggingOptions {
    ERROR("[Error]", ChatColor.RED),
    TEST("[Test]",ChatColor.AQUA),
    INFO("[Info]",ChatColor.YELLOW)
    ;

    /**
     * the option's prefix
     */
    private String prefix;

    /**
     * the option's chat color
     */
    private ChatColor color;

    LoggingOptions(String prefix, ChatColor color) {
        this.prefix = prefix;
        this.color = color;
    }

    public String getPrefix() {
        return prefix;
    }

    public ChatColor getColor() {
        return color;
    }
}
