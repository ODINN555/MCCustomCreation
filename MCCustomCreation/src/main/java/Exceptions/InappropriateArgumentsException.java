package Exceptions;

import java.util.Arrays;

/**
 * an inappropriate arguments exception occurs when a node expects a certain amount/types of classes
 * and it is not giving the correct amount/type of classes.
 */
public class InappropriateArgumentsException extends ACustomMCException {

    public InappropriateArgumentsException(Class caller,Class[] expected, Class[] given) {
        super(InappropriateArgumentsException.class.getSimpleName(),caller,"Inappropriate arguments, given: " + formatClassesToString(given)+". Expected: "+formatClassesToString(given));

    }

    public InappropriateArgumentsException(Class caller,Class[] expected, Object[] given){

        this(caller,expected, (Class[]) (Arrays.stream(given).map(param -> param.getClass()).toArray()));

    }

    /**
     * transforms the classes names to a string
     * for example, for [String,Integer,Byte]
     * will return: "String, Integer, Byte"
     *
     * @param classes given classes
     * @return the classes names list as a string
     */
    private static String formatClassesToString(Class... classes){
        String str ="";

        for (Class aClass : classes)
            str+= aClass.getSimpleName()+", ";

        return str;
    }
}
