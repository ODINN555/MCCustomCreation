package Exceptions;

/**
 * an inappropriate inheritance exception occurs
 * when a certain object must inherit a certain class but it doesn't.
 */
public class InappropriateInheritanceException extends ACustomMCException{
    public InappropriateInheritanceException(Class caller,String msg){
        super(InappropriateInheritanceException.class.getSimpleName(),caller,msg);
    }
}
