package Commands;

import Nodes.Events.EventInstance;
import Nodes.Events.IEvent;
import Nodes.FunctionTree;
import Utility.Logging.Logging;
import Utility.Logging.LoggingOptions;
import me.ODINN.MCCustomCreation.Main;
import org.bukkit.entity.Player;

import java.util.*;

public class CMD_Duplicate extends CustomCommand{

    public CMD_Duplicate() {
        super("Duplicate", Arrays.asList("Duplicates a creation with a new name."), "Duplicate", 2, 2, Arrays.asList("Duplicate","Clone","Copy","Replicate"), Arrays.asList("Duplicate [name to duplicate] [name of new creation]"));
    }

    @Override
    boolean onCommand(Player sender, List<String> args) {

        if(Main.getCreationsManager().getCreation(args.get(0)) == null){
            Logging.message(sender,"There is no creation with that name, therefore could not duplicate it.", LoggingOptions.ERROR);
            return true;
        }
        Map<IEvent,List<FunctionTree>> map = Main.getCreationsManager().getCreation(args.get(0));
        Main.getCreationsManager().setCreation(args.get(1),cloneMap(map));
        Logging.message(sender,"The creation was duplicated successfully.", LoggingOptions.INFO);
        sender.performCommand("customcreation create "+args.get(1));
        return true;
    }

    @Override
    List<String> getCompletions(int argumentIndex, Player player) {

        if(argumentIndex == 0)
            return Main.getCreationsManager().getCreationList();

        return new ArrayList<>();
    }

    /**
     *
     * @param map a given creations map
     * @return the given map cloned
     */
    private Map<IEvent,List<FunctionTree>> cloneMap(Map<IEvent,List<FunctionTree>> map){
        Map<IEvent,List<FunctionTree>> newMap = new HashMap<>();
        for (IEvent event : map.keySet()) {
            IEvent newEvent;
            if(event instanceof EventInstance)
                newEvent = ((EventInstance) event).clone();
            else newEvent = event;

            List<FunctionTree> trees = map.get(event);
            List<FunctionTree> clonedList = new ArrayList<>();
            for (FunctionTree tree : trees)
                    clonedList.add(tree.clone());



            newMap.put(newEvent,clonedList);
        }

        return newMap;
    }
}
