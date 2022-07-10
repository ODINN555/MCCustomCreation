package Nodes;

import java.io.Serializable;

/**
 * An interface representing a node*
 *
 * *node - a node is any type of function.
 *         like in code a function can be a function which returns a value or not, receive values... etc.
 */
public interface INode extends Cloneable, Serializable, Comparable<INode> {

    /**
     * @return a node item referencing this node
     */
    NodeItemStack getItemReference();

    String getKey();


    default int compareTo(INode other){
        if(other == null)
            return 1;
        return other.getKey().compareTo(getKey());
    }

}