package Commands;

import Utility.Logging.Logging;
import Utility.Logging.LoggingOptions;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CMD_Help extends CustomCommand{
    private static final ChatColor NAME_COLOR = ChatColor.GOLD;
    private static final ChatColor DESCRIPTION_COLOR = ChatColor.AQUA;

    public CMD_Help() {
        super("Help", Arrays.asList("Displays the list of commands"), "Help", 0, 1, Arrays.asList("Help","Commands","Cmds","Info"), Arrays.asList("Help","Help [Command]"));
    }

    @Override
    boolean onCommand(Player sender, List<String> args) {
        if(args.size() == 0)
            sender.sendMessage(getCommandListString(getPermittedCommandsFromList(CommandsHandler.INSTANCE.getAllCommands(),sender)));
        else {
            String cmd = args.get(0);
            if(!CommandsHandler.INSTANCE.isCommandExists(cmd)){
                Logging.message(sender,"There is no command with that name. use /"+getName()+" for list of commands.", LoggingOptions.ERROR);
                return true;
            }

            CustomCommand command = CommandsHandler.INSTANCE.getCommand(cmd);
            if(!sender.hasPermission(command.getPermission())){ // TODO permissions
                Logging.message(sender,"You don't have permission to view this command.",LoggingOptions.ERROR);
                return true;
            }

            sender.sendMessage(getDisplayCommandString(command));
        }
        return true;
    }

    /**
     *
     * @param commands given commands
     * @param player a given player
     * @return a list containing all the commands from the given commands which the given player has permission to use
     */
    private List<CustomCommand> getPermittedCommandsFromList(List<CustomCommand> commands,Player player){
        return commands.stream().filter(command -> player.hasPermission(command.getPermission())).collect(Collectors.toList());
    }

    /**
     *
     * @param commands given commands
     * @return a string of a list of the given commands
     */
    private String getCommandListString(List<CustomCommand> commands){
        String str = NAME_COLOR+"Commands: \n";
        for (CustomCommand command : commands)
            str+=getCommandToString(command)+"\n";

        return str;
    }

    /**
     *
     * @param cmd a given command
     * @return a string which describes the given command
     */
    private String getCommandToString(CustomCommand cmd){
        return  ChatColor.RESET+"- "+NAME_COLOR+cmd.getName()+": "+listToString(cmd.getDescription());
    }

    /**
     *
     * @param cmd a given command
     * @return a string for displaying the command
     */
    private String getDisplayCommandString(CustomCommand cmd){
        return NAME_COLOR+cmd.getName()+": " +
                "\n Description: "+DESCRIPTION_COLOR+listToString(cmd.getDescription())+
                NAME_COLOR+ "\n Usages: "+DESCRIPTION_COLOR+listToString(cmd.getUsages())+
                NAME_COLOR+"\n Permission: "+DESCRIPTION_COLOR+cmd.getPermission()+
                NAME_COLOR+"\n Aliases: "+DESCRIPTION_COLOR+listToString(cmd.getAliases())+ChatColor.RESET;


    }

    @Override
    List<String> getCompletions(int argumentIndex,Player player) {
        return getPermittedCommandsFromList(CommandsHandler.INSTANCE.getAllCommands(), player).stream().map(cmd -> cmd.getName()).collect(Collectors.toList());
    }

    /**
     *
     * @param list a given list
     * @return the given list to string
     */
    private String listToString(List<String> list){
        String str ="";
        for (String s : list)
            str+=s+",";

       str = str.substring(0,str.length() -1);
       return str;
    }
}
