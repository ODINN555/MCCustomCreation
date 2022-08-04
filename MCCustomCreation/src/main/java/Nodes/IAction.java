package Nodes;

/**
 * An interface representing an Action*
 *
 * *Action - a function which is executed and effects something in the game, called from event and receives parameters from other functions.
 */
public interface IAction extends IReceiveAbleNode{

    /**
     * performs this action
     * @param params given parameters
     * @return if the action was successful
     */
    boolean action(Object... params);


}
