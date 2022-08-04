package Nodes;

/**
 * This is just to declare that a certain action can be used twice.
 * This interface has no use other than that.
 */
public interface IDuplicableAction {

    /**
     *
     * @return if the action is duplicable, by default set to true
     */
    default boolean isDuplicable(){return true;}
}
