package Commands;

import GUI.ChooseGUIs.GUI_CreateEvent;
import Nodes.Events.IEvent;
import Nodes.FunctionTree;
import me.ODINN.MCCustomCreation.CreationsManager;
import me.ODINN.MCCustomCreation.CreationsUtil;
import me.ODINN.MCCustomCreation.Main;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A command for creating a custom item
 */
public class CMD_Create extends CustomCommand {
    private static final String NAME = "Create";
    private static final String PERMISSION = NAME;


    public CMD_Create() {
        super(NAME, Arrays.asList("Creates or modifies a creation, currently works on items only."), PERMISSION, 1, 1, Arrays.asList(NAME),Arrays.asList("Create [Name]"));
    }

    @Override
    boolean onCommand(Player sender, List<String> args) {
        String name = args.get(0);
        if(Main.getCreationsManager().getCreation(name) == null) { //create new
            Map<IEvent,List<FunctionTree>> createEvent = new HashMap<>();
            Main.getCreationsManager().setCreation(name,createEvent);
        }
        ItemStack mainHand = sender.getInventory().getItemInMainHand();
        if( mainHand!= null && !mainHand.getType().equals(Material.AIR))
            CreationsUtil.setCreationIntoItem(sender.getInventory().getItemInMainHand(),name);
        //anyway open the gui so the player can modify the created instance or modify the existent
        GUI_CreateEvent gui = new GUI_CreateEvent(Main.getCreationsManager().getCreation(name));
        gui.open(sender);

        return true;
    }

    @Override
    List<String> getCompletions(int argumentIndex,Player player) {
        if(argumentIndex == 0)
            return Main.getCreationsManager().getCreationList();
        return null;
    }
}
