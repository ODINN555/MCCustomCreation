package Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * A command for creating a custom item
 */
public class CMD_Create implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender.hasPermission("MCCustomCreation.Create")){ //TODO retrieve permissions from file

        }


        return false;
    }
}
