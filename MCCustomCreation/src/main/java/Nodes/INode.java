package Nodes;

import java.io.Serializable;

/**
 * An interface representing a node*
 *
 * *node - a node is any type of function.
 *         like in code a function can be a function which returns a value or not, receive values... etc.
 */
public interface INode extends Cloneable, Serializable {

    /**
     * @return a node item referencing this node
     */
    NodeItemStack getItemReference();

    String getKey();
}