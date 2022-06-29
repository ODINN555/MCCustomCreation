package Nodes;

/**
 * An interface representing a parameter*
 * @param <T> the parameter*'s type
 *
 * *parameter - a function which effects nothing, receives values and with those values returns a value.
 */
public interface IParameter<T> extends IReceiveAbleNode,IReturningNode{

    /**
     *
     * @param objects given received objects
     * @return a value depending on the received objects
     */
    T getParameter(Object... objects);
    Class<T> getReturnType();

}
