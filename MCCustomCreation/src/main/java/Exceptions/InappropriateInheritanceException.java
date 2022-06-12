package Exceptions;

public class InappropriateInheritanceException extends RuntimeException{
    public InappropriateInheritanceException(String msg){
        super("Inappropriate Inheritance: "+ msg);
    }
}
