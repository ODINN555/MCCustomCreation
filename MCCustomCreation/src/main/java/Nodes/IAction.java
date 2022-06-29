package Nodes;

import Exceptions.InappropriateArgumentsException;

/**
 * An interface representing an Action*
 *
 * *Action - a function which is executed and effects something in the game, called from event and receives parameters from other functions.
 */
public interface IAction extends IReceiveAbleNode{

    boolean action(Object... params);

    

    default boolean checkParameters(IAction action, Object... params){
        if(params.length < action.getReceivedTypes().length)
            throw new InappropriateArgumentsException(IAction.class,action.getReceivedTypes(), params);

        for(int i = 0; i < action.getReceivedTypes().length; i++)
            if(!action.getReceivedTypes()[i].equals(params[i].getClass()))
                throw new InappropriateArgumentsException(IAction.class,action.getReceivedTypes(),params);

        return true;
    }

}
