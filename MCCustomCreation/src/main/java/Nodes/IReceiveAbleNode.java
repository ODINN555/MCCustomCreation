package Nodes;

import Utility.Logging.Logging;
import Utility.Logging.LoggingOptions;

/**
 * An interface representing a value receivable node.
 */
public interface IReceiveAbleNode extends INode{
    /**
     *
     * @return the node's received types
     */
    Class[] getReceivedTypes();

    /**
     *
     * @return the descriptions for the node's received types
     */
    String[] getReceivedTypesDescriptions();

    /**
     *
     * @param params given parameters
     * @return if the given parameters are valid and matches the required parameters
     */
    default boolean checkParameters( Object... params){
        if(params.length < getReceivedTypes().length) {
            Logging.log("Inappropriate arguments given to receivable node " + getKeyAsDisplay() + " , so ignored. The amount of parameters given was too little.", LoggingOptions.INFO);
            return false;
        }

        for(int i = 0; i < getReceivedTypes().length; i++) 
            if (params[i] == null || !getReceivedTypes()[i].isAssignableFrom(params[i].getClass()))
            {
                Logging.log("Inappropriate arguments given to receivable node "+getKeyAsDisplay()+" , so ignored. parameter is or null or not matching, given: "+params[i]+" expected: "+getReceivedTypes()[i].getSimpleName(), LoggingOptions.INFO);
                return false;
            }
        
        return true;
    }
}
