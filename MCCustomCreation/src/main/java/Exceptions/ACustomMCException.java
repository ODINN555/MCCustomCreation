package Exceptions;

/**
 * a custom mc exception - for this plugin usage.
 */
public abstract class ACustomMCException extends RuntimeException{

    /**
     * The exception's name
     */
    private final String name;

    /**
     * The class which called the exception
     */
    private final Class caller;


    public ACustomMCException(String name,Class caller,String msg){
        super("An "+name+" occurred in class "+caller.getSimpleName()+": "+msg);
        this.name = name;
        this.caller = caller;
    }

    public String getName() {
        return name;
    }

    public Class getCaller() {
        return caller;
    }
}
