package Nodes;

/**
 * An interface representing a value receivable node.
 */
public interface IReceiveAbleNode extends INode{
    /**
     *
     * @return the node's received types
     */
    Class[] getReceivedTypes();


}
