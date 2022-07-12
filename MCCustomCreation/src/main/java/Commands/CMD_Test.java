package Commands;

import GUI.ChooseGUIs.GUI_CreateEvent;
import Nodes.*;
import Nodes.Actions.ATest;
import Nodes.Events.DefaultEvents;
import Nodes.Events.IEvent;
import Nodes.Primitives.TPri_Boolean;
import Nodes.Primitives.TPri_Integer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * A command for testing purposes only
 */
public class CMD_Test extends CustomCommand{
    private static final String permission = "MCCustomCreation.test";
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
        IEvent event = DefaultEvents.LEFT_CLICK;
        IAction action = new ATest();

        TruePrimitive iPrim = new TPri_Integer();
        TruePrimitive bPrim = new TPri_Boolean() ;

        NodesHandler.INSTANCE.register(action,iPrim,bPrim,event);
        FunctionTree eTree = new FunctionTree(event);
        FunctionTree aTree = new FunctionTree(action,null,eTree);
        FunctionTree iTree = new FunctionTree(iPrim,null,aTree);
        FunctionTree bTree = new FunctionTree(bPrim,null,aTree);
        aTree.setNext(new FunctionTree[]{iTree,bTree});
        eTree.setNext(new FunctionTree[]{aTree});

        GUI_CreateEvent gui = new GUI_CreateEvent(new HashMap<>());
        gui.open((Player) sender);

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
