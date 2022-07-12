package GUI.GUIAtrriutes.ChainGUI;

import com.sun.istack.internal.NotNull;

import java.util.*;

/**
 * IChainable gui handler
 */
public class ChainHandler {

    /**
     * all the handlers instances for each of the players
     */
    private static Map<UUID,ChainHandler> handlers = new HashMap<>();

    /**
     * The chain of chainable guis
     */
    private List<IChainable> chain;

    /**
     * The father of the chain
     */
    private IChainable father;

    /**
     *
     * @param father a given father chain
     */
    public ChainHandler(IChainable father){
        this.father = father;
        this.chain = new ArrayList<>();
        chain.add(father);
    }

    /**
     * handles a "next" function in the given chain
     * @param curr a given current chainable gui
     * @param next a given next chainable gui
     * @return the next chainable gui
     */
    public static IChainable onNext(@NotNull IChainable curr, IChainable next){
        UUID ownerId = curr.getCurrentGUI().getOwner().getUniqueId();
        if(getHandler(ownerId) == null)
            setHandler(ownerId,new ChainHandler(curr));

        getHandler(ownerId).chain.add(next);

        return next;


    }

    /**
     *
     * @param current a given current chainable gui
     * @return the new current gui
     */
    public static IChainable onPrev(IChainable current){
        UUID ownerId = current.getCurrentGUI().getOwner().getUniqueId();
        if(getHandler(ownerId) == null)
            return null;

        getHandler(ownerId).chain.remove(getHandler(ownerId).chain.size() -1);
        if(getHandler(ownerId).chain.size() == 0) {
            remove(ownerId);
            return null;
        }
        return getHandler(ownerId).chain.get(getHandler(ownerId).chain.size() -1);
    }

    /**
     *
     * @param id a given player's id
     * @return the chain handler of the given player
     */
    public static ChainHandler getHandler(UUID id){
        return handlers.get(id);
    }

    /**
     * sets the given handler to the given player
     * @param id a given player's id
     * @param handler a given handler
     */
    public static void setHandler(UUID id, ChainHandler handler){
        handlers.put(id,handler);
    }

    /**
     * removes the handler of the given player
     * @param id a given player's id
     */
    public static void remove(UUID id){
        handlers.remove(id);
    }

    /**
     *
     * @return the removed chainable gui
     */
    public IChainable removeCurrent(){
        if(this.chain == null || this.chain.size() == 0)
            return null;
        return this.chain.remove(this.chain.size() -1);
    }

    /**
     *
     * @param index a given index
     * @return the chainable gui of the given index
     */
    public IChainable getChainable(int index){
        if(chain == null)
            return null;
        return this.chain.get(index);
    }

    /**
     *
     * @return the current chainable gui
     */
    public IChainable getCurrentChainable(){
        if(chain == null || chain.size() == 0)
            return null;
        return this.chain.get(this.chain.size() -1);
    }


    public IChainable getFather() {
        return father;
    }
}
