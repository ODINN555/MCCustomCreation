package Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * A command for testing purposes only
 */
public class CMD_Test implements CommandExecutor {
    private final String permission = "MCCustomCreation.test";
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission(permission))
            return false;

        runTest(sender,args);
        return true;
    }

    /**
     * runs the test command, for testing of this plugin only
     * @param sender the tester
     * @param args the command arguments
     */
    private final void runTest(CommandSender sender,String[] args){
    
    }
}
