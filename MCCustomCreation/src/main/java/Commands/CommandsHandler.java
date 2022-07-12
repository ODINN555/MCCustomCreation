package Commands;

import me.ODINN.MCCustomCreation.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class CommandsHandler {

    public static final CommandsHandler INSTANCE = new CommandsHandler();
    private static Map<String, CustomCommand> commands = new HashMap<>();
    public static final String PLUGIN_COMMAND = "CustomCreation";

    private CommandsHandler() {
    }


    public void register(CustomCommand cmd) {
        commands.put(cmd.getName(), cmd);
    }

    public void register(CustomCommand... cmds){
        for (CustomCommand cmd : cmds)
            register(cmd);
    }

    public CustomCommand getCommand(String name) {
        for (CustomCommand value : commands.values())
            if(value.getAliases().stream().anyMatch( s -> s.equalsIgnoreCase(name)))
                return value;
        return null;
    }

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
        if (!(sender instanceof Player))// TODO msg back
            return true;
        Player player = (Player) sender;
        if (command.getName().equalsIgnoreCase(PLUGIN_COMMAND)) {
            if (args.length == 0)//TODO msg back
                return true;

            String subCmd = args[0];
            if (subCmd == null || subCmd.equalsIgnoreCase(" "))
                return true;

            if (isCommandExists(subCmd)) {
                CustomCommand cmd = getCommand(subCmd);
                List<String> subArgs = new ArrayList<>(Arrays.asList(args));
                subArgs.remove(0); // remove the label of sub command
                if (subArgs.size() < cmd.getMinimumArgsAmount())  // TODO msg smth back
                    return true;
                if(subArgs.size() > cmd.getMaximumArgsAmount()) //TODO msg smth back
                    return true;

                return cmd.onCommand(player, subArgs);

                } else {
                    //TODO msg wrong usage
                }
            }


        return false;
        }


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

            return customCmd.getCompletions(argIndex)
                    .stream()
                    .filter(comp -> comp.startsWith(args[args.length -1]))
                    .collect(Collectors.toList());

        }
        return null;
    }
}

