package GUI;

import Nodes.FunctionTree;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FunctionCopyHandler {

    /**
     * the saved copies
     */
    private static Map<UUID, FunctionTree> functionCopies;

    /**
     * singleton implementation
     */
    public static final FunctionCopyHandler INSTANCE = new FunctionCopyHandler();
    private FunctionCopyHandler(){functionCopies = new HashMap<>(); }

    /**
     * sets the given function tree copy in the map for the given player
     * @param player a given
     * @param tree a given function tree
     * @return the replaced copy
     */
    public FunctionTree setCopy(Player player,FunctionTree tree){
        if(player == null)
            return null;
       return functionCopies.put(player.getUniqueId(),tree);
    }

    /**
     *
     * @param player a given player
     * @return the copy which the player own
     */
    public FunctionTree getCopy(Player player){
        if(player == null)
            return null;
        return functionCopies.get(player.getUniqueId());
    }

    /**
     *
     * @param player a given player
     * @return if a player has copied a function tree
     */
    public boolean hasCopy(Player player){
        return functionCopies.containsKey(player);
    }
}
