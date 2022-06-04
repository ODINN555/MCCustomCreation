package Nodes;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class FunctionTree {

    private Object current;
    private FunctionTree[] next;

    /**
     * executes an IAction with the given function tree
     * @param func a given function tree
     * @param executor the executor of the function
     * @param item the function's item executed on
     * @return will return null if haven't succeeded and the first FunctionTree if succeeded.
     * The recursion itself returns the value of the next parameter/primitive in the tree
     */
    public static Object executeFunction(FunctionTree func, LivingEntity executor, ItemStack item){
        if(func.getNext() == null || func.getNext().length == 0){
            if(func.getCurrent() instanceof IPrimitive)
                return ((IPrimitive) func.getCurrent()).getValue(executor,item);

            return null;
        }

        if(func.getCurrent() instanceof  IAction) {
            IAction action = (IAction) func.getCurrent();
            Object[] values = new Object[action.getParameterTypes().length];
            for(int i = 0 ; i < action.getParameterTypes().length; i++)
                values[i] = executeFunction(func.getNext()[i],executor,item);
            return  action.action(values);

        }

        if(func.getCurrent() instanceof IParameter){
            IParameter param = (IParameter) func.getCurrent();
            Object[] values = new Object[param.getPrimitiveTypes().length];
            for(int i = 0 ; i < param.getPrimitiveTypes().length; i++)
                values[i] = executeFunction(func.getNext()[i],executor,item);
            return param.getParameter(values);
        }

        return null;

    }

    public FunctionTree(Object current, FunctionTree[] next){
        this.current = current;
        this.next = next;
    }

    public FunctionTree(Object current){
        this(current,null);
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


}
