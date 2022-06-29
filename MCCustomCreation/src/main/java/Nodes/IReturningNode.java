package Nodes;

/**
 * An interface which represents a value returner node
 */
public interface IReturningNode extends INode{
    /**
     *
     * @return the node's return type
     */
    Class getReturnType();
}
