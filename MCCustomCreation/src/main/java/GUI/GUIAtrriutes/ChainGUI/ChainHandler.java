package GUI.GUIAtrriutes.ChainGUI;

import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ChainHandler implements Listener {
    private static Map<UUID,ChainHandler> handlers;
    private List<IChainable> chain;
    private IChainable father;

    public ChainHandler(IChainable father){
        this.father = father;
        this.chain = new ArrayList<>();
        chain.add(father);
    }

    public static IChainable onNext(IChainable curr,IChainable next){
        UUID ownerId = curr.getCurrentGUI().getOwner().getUniqueId();
        if(getHandler(ownerId) == null)
            setHandler(ownerId,new ChainHandler(curr));
        ChainHandler handler = getHandler(ownerId);
        handler.chain.add(next);
        return next;
    }

    public static IChainable onPrev(IChainable current){
        UUID ownerId = current.getCurrentGUI().getOwner().getUniqueId();
        if(getHandler(ownerId) == null)
            return null;

        ChainHandler handler = getHandler(ownerId);
        handler.chain.remove(handler.chain.size() -1);
        if(handler.chain.size() == 0) {
            remove(ownerId);
            return null;
        }

        return handler.chain.get(handler.chain.size() -1);
    }

    public IChainable getFather() {
        return father;
    }

    public static ChainHandler getHandler(UUID id){
        return handlers.get(id);
    }

    public static void setHandler(UUID id, ChainHandler handler){
        handlers.put(id,handler);
    }

    public static void remove(UUID id){
        handlers.remove(id);
    }
}
