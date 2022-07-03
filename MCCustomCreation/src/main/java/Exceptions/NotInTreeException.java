package Exceptions;

/**
 * a not in tree exception occurs when a certain object is supposed to be found within
 * a function tree but it doesn't.
 */
public class NotInTreeException extends ACustomMCException{

    public NotInTreeException(Class caller,String msg){
        super(NotInTreeException.class.getSimpleName(),caller,msg);
    }


}
