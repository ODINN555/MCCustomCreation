package Exceptions;

import Nodes.IParameter;

import java.util.Arrays;

public class InappropriateArgumentsException extends RuntimeException {

    public InappropriateArgumentsException(Class[] expected, Class[] given) {
        super("Inappropriate arguments, given: " + formatClassesToString(given)+". Expected: "+formatClassesToString(given));

    }

    public InappropriateArgumentsException(Class[] exepcted, Object[] given){

        this(exepcted, (Class[]) (Arrays.stream(given).map(param -> param.getClass()).toArray()));

    }

    private static String formatClassesToString(Class... classes){
        String str ="";

        for (Class aClass : classes)
            str+= aClass.getSimpleName()+", ";

        return str;
    }
}
