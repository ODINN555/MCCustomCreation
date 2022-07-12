package Commands;

import GUI.ChooseGUIs.GUI_CreateEvent;
import Nodes.*;
import Nodes.Actions.ATest;
import Nodes.Events.DefaultEvents;
import Nodes.Events.IEvent;
import Nodes.Primitives.TPri_Boolean;
import Nodes.Primitives.TPri_Integer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * A command for testing purposes only
 */
public class CMD_Test extends CustomCommand{
    private static final String permission = "Test";
    private static final int minArgAmount = 0; // changed depending on current test
    private static final int maxArgAmount = 0; // changed depending on current test

    public CMD_Test() {
        super("Test", Arrays.asList("a test command for admins and developers of this plugin"), permission, minArgAmount,maxArgAmount, Arrays.asList("test"));
    }


    /**
     * runs the test command, for testing of this plugin only
     * @param sender the tester
     * @param args the command arguments
     */
    private final void runTest(CommandSender sender,String[] args){


    }

    @Override
    boolean onCommand(Player sender, List<String> args) {
        runTest(sender,args.toArray(new String[0]));
        return true;
    }

    @Override
    List<String> getCompletions(int argumentIndex) {
        return null;
    }
}
