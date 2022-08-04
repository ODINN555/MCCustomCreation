package Nodes;

import java.io.Serializable;
import java.util.Locale;

/**
 * An interface representing a node*
 *
 * *node - a node is any type of function.
 *         like in code a function can be a function which returns a value or not, receive values... etc.
 */
public interface INode extends Cloneable , Serializable {

    /**
     * @return a node item referencing this node
     */
    NodeItemStack getItemReference();

    /**
     *
     * @return the node's key
     */
    String getKey();

    /**
     *
     * @return the node's description
     */
    String getDescription();

    /**
     *
     * @param other a given other node
     * @return the compare result of the given node with this node (0 is equal)
     */
    default int compareTo(INode other){
        if(other == null)
            return 1;
        return other.getKey().compareTo(getKey());
    }

    /**
     *
     * @return the node's key as a display name
     */
    default String getKeyAsDisplay(){
        String display = getKey().toLowerCase(Locale.ROOT);
        String[] arr = display.split("_");
        display = "";
        for (String s : arr)
            display += s.substring(0,1).toUpperCase() + s.substring(1) +" ";
        return display;
    }


}