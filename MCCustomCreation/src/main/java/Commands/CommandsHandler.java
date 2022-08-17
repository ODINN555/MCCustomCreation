package Commands;

import Utility.Logging.Logging;
import Utility.Logging.LoggingOptions;
import Utility.Logging.Messages;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

/**
 * handles commands
 */
public class CommandsHandler {

    /**
     * Singleton implementation
     */
    public static final CommandsHandler INSTANCE = new CommandsHandler();

    /**
     * commands registry map
     */
    private static Map<String, CustomCommand> commands = new HashMap<>();

    /**
     * the plugin's command name
     */
    public static final String PLUGIN_COMMAND = "CustomCreation";

    /**
     * Singleton implementation
     */
    private CommandsHandler() {}

    /**
     * registers the given command
     * @param cmd a given command
     */
    public void register(CustomCommand cmd) {
        commands.put(cmd.getName(), cmd);
    }

    /**
     * registers the given commands
     * @param cmds given commands
     */
    public void register(CustomCommand... cmds){
        for (CustomCommand cmd : cmds)
            register(cmd);
    }

    /**
     *
     * @param name a given command name/alias
     * @return the command with the given name/alias
     */
    public CustomCommand getCommand(String name) {
        for (CustomCommand value : commands.values())
            if(value.getAliases().stream().anyMatch( s -> s.equalsIgnoreCase(name)))
                return value;
        return null;
    }

    /**
     *
     * @param name a given command name or alias
     * @return if a command with the given name/alias exists
     */
    public boolean isCommandExists(String name) {
        for (CustomCommand value : commands.values())
            if(value.getAliases().stream().anyMatch( s -> s.equalsIgnoreCase(name)))
                return true;

        return false;
    }


    /**
     * @param sender  the command's sender
     * @param command the command
     * @param label   the command's label
     * @param args    the command's arguments
     * @return if the command was successful (note that it should return true even on exceptions!
     */
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Logging.log(Messages.NOT_PLAYER, LoggingOptions.ERROR);
            return true;
        }

        Player player = (Player) sender;
        if (command.getName().equalsIgnoreCase(PLUGIN_COMMAND)) {

            if (args.length == 0) {
                Logging.message(player,"Wrong usage, usage: /"+PLUGIN_COMMAND+" [Command]",LoggingOptions.ERROR);
                return true;
            }

            String subCmd = args[0];
            if (subCmd == null || subCmd.equalsIgnoreCase(" "))
                return true;

            subCmd = ChatColor.stripColor(subCmd);

            if (isCommandExists(subCmd)) {
                CustomCommand cmd = getCommand(subCmd);
                if(!player.hasPermission(cmd.getPermission())) {
                    Logging.message(player, "You don't have the permission to use this command.", LoggingOptions.ERROR);
                    return true;
                }
                List<String> subArgs = new ArrayList<>(Arrays.asList(args));
                subArgs.remove(0); // remove the label of sub command
                if (subArgs.size() < cmd.getMinimumArgsAmount()) {
                    Logging.message(player,"Not enough arguments, usages: "+cmd.getUsages(),LoggingOptions.ERROR);
                    return true;
                }
                if(subArgs.size() > cmd.getMaximumArgsAmount())
                {
                    Logging.message(player,"Too many arguments, usages: "+cmd.getUsages(),LoggingOptions.ERROR);
                    return true;
                }
                return cmd.onCommand(player, subArgs);

            } else {
                    Logging.message(player,"This command does not exist.",LoggingOptions.ERROR);
            }
        }


        return false;
        }


    /**
     *
     * @param sender the tab completer user
     * @param command a given command
     * @param alias a given alias
     * @param args given arguments
     * @return a list of words to display as tab completion
     */
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if(command.getName().equalsIgnoreCase(PLUGIN_COMMAND)){
           int argIndex = args.length -1;
            if(argIndex == 0)
                return commands.keySet().stream().filter(c -> c.toLowerCase().startsWith(args[0].toLowerCase())).collect(Collectors.toList());

            String cmd = args[0];
            if(!isCommandExists(cmd))
                return null;
            CustomCommand customCmd = getCommand(cmd);
            argIndex--;

            if(customCmd.getMaximumArgsAmount() <= argIndex)
                return null;

            return customCmd.getCompletions(argIndex, (Player) sender)
                    .stream()
                    .filter(comp -> comp.toLowerCase().startsWith(args[args.length -1].toLowerCase()))
                    .collect(Collectors.toList());

        }
        return null;
    }

    /**
     *
     * @return all the registered commands
     */
    public List<CustomCommand> getAllCommands(){
        return commands.values().stream().collect(Collectors.toList());
    }
}

