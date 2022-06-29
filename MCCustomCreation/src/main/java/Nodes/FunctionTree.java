package Nodes;

import Nodes.Events.IEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

/**
 * An object representing a node as a part of a tree which represents a Function
 */
public class FunctionTree {

    /**
     * The current object
     */
    private Object current;

    /**
     * next trees
     */
    private FunctionTree[] next;

    /**
     * previous tree
     */
    private FunctionTree prev;

    /**
     * executes an IAction with the given function tree
     * @param func a given function tree
     * @param executor the executor of the function
     * @param item the function's item executed on
     * @return will return null if haven't succeeded and the first FunctionTree if succeeded.
     * The recursion itself returns the value of the next parameter/primitive in the tree
     */
    public static Object executeFunction(FunctionTree func, LivingEntity executor, ItemStack item){
        if(func.getCurrent() instanceof IEvent)
            for (FunctionTree functionTree : func.getNext())
                executeFunction(functionTree,executor,item);


        if(func.getNext() == null || func.getNext().length == 0){
            if(func.getCurrent() instanceof IPrimitive)
                return ((IPrimitive) func.getCurrent()).getValue(executor,item);

            return null;
        }

        if(func.getCurrent() instanceof  IAction) {
            IAction action = (IAction) func.getCurrent();
            Object[] values = new Object[action.getReceivedTypes().length];
            for(int i = 0 ; i < action.getReceivedTypes().length; i++)
                values[i] = executeFunction(func.getNext()[i],executor,item);
            return  action.action(values);

        }

        if(func.getCurrent() instanceof IParameter){
            IParameter param = (IParameter) func.getCurrent();
            Object[] values = new Object[param.getReceivedTypes().length];
            for(int i = 0 ; i < param.getReceivedTypes().length; i++)
                values[i] = executeFunction(func.getNext()[i],executor,item);
            return param.getParameter(values);
        }

        return null;

    }

    /**
     *
     * @param current a given current object
     * @param next given next trees
     * @param prev given previous tree
     */
    public FunctionTree(Object current, FunctionTree[] next,FunctionTree prev){
        this.current = current;
        this.next = next;
        this.prev = prev;
    }

    /**
     *
     * @param current a given current tree
     */
    public FunctionTree(Object current){
        this(current,null,null);
    }

    /**
     *
     * @param obj a given tree
     * @return if the given tree is found below this tree
     */
    public boolean isInTree(FunctionTree obj){
        return isInTree(obj,this);
    }

    /**
     *
     * @param obj a given tree
     * @param current a given current tree
     * @return if the given tree is found below the current tree
     */
    private boolean isInTree(FunctionTree obj,FunctionTree current){
        if(current == null)
            return false;

        if(current.equals(obj))
            return true;

        for (FunctionTree functionTree : current.getNext())
            if(isInTree(obj,functionTree))
                return true;
        return false;
    }

    public Object getCurrent() {
        return current;
    }

    public void setCurrent(Object current) {
        this.current = current;
    }

    public FunctionTree[] getNext() {
        return next;
    }

    public void setNext(FunctionTree[] next) {
        this.next = next;
    }


    public FunctionTree getPrev() {
        return prev;
    }

    public void setPrev(FunctionTree prev) {
        this.prev = prev;
    }


    /**
     * saves a given tree
     * @param tree a given tree
     * @return if the tree was saved successfully
     */
    public static boolean saveTree(FunctionTree tree){
        //TODO
        return false;
    }
}
