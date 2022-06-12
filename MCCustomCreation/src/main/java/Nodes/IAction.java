package Nodes;

import Exceptions.InappropriateArgumentsException;

import java.util.Arrays;

public interface IAction{

    boolean action(Object... params);

    Class[] getParameterTypes();

    default boolean checkParameters(IAction action,Object... params){
        if(params.length < action.getParameterTypes().length)
            throw new InappropriateArgumentsException(action.getParameterTypes(), params);

        for(int i = 0; i < action.getParameterTypes().length; i++)
            if(!action.getParameterTypes()[i].equals(params[i].getClass()))
                throw new InappropriateArgumentsException(action.getParameterTypes(),params);

        return true;
    }

    NodeItemStack getItemReference();

}
