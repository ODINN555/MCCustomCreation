package Commands;

import Utility.Logging.Logging;
import Utility.Logging.LoggingOptions;
import me.ODINN.MCCustomCreation.Main;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CMD_Remove extends CustomCommand{

    public CMD_Remove() {
        super("Remove", Arrays.asList("Removes a creation."), "Remove", 1, 1, Arrays.asList("Delete","Remove","Erase"), Arrays.asList("Delete [name]"));
    }

    @Override
    boolean onCommand(Player sender, List<String> args) {
        if(Main.getCreationsManager().getCreation(args.get(0)) == null){
            Logging.message(sender, "There is no creation with that name.", LoggingOptions.ERROR);
            return true;
        }
        Main.getCreationsManager().removeCreation(args.get(0));
        Logging.message(sender,"The creation was removed successfully.",LoggingOptions.INFO);
        return true;
    }

    @Override
    List<String> getCompletions(int argumentIndex, Player player) {
        if(argumentIndex == 0)
            return Main.getCreationsManager().getCreationList();
        return new ArrayList<>();
    }
}
