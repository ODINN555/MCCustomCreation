package Nodes;

import Nodes.Events.IEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An object representing a node as a part of a tree which represents a Function
 */
public class FunctionTree implements Serializable {

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

    public FunctionTree getFather(){
        FunctionTree prev = this;
        while(prev.getPrev() != null)
            prev = prev.getPrev();
        return prev;
    }

    public Map<String,Object> serialize(){
        return this.serialize(this);
    }

    private Map<String,Object> serialize(FunctionTree tree){
        Map<String,Object> map = new HashMap<>();

        putDefaultTreeValues(tree,map);
        List<Map<String,Object>> values = new ArrayList<>();
        if(tree.getNext() != null)
        for (FunctionTree functionTree : tree.getNext())
            values.add(serialize(functionTree));

        if(!values.isEmpty())
            map.put("Values",values);
        else if(tree.getCurrent() instanceof TruePrimitive)
            map.put("Value",((TruePrimitive) tree.getCurrent()).getValue());

        return map;

    }

    public static FunctionTree deserialize(FunctionTree prev ,Map<String,Object> map) throws CloneNotSupportedException {
        FunctionTree tree = new FunctionTree(NodesHandler.INSTANCE.getNodeByName((String) map.get("Name")),null,prev);
        List<Map<String,Object>> list = (List) map.get("Values");
        if(tree.getCurrent() instanceof TruePrimitive)
            ((TruePrimitive) tree.getCurrent()).setValue(map.get("Value"));
        else {
            FunctionTree[] next = new FunctionTree[list.size()];
            for (int i = 0; i < list.size(); i++)
                next[i] = deserialize(tree,list.get(i));
            tree.setNext(next);
        }

        return tree;

    }

    private void putDefaultTreeValues(FunctionTree tree, Map<String,Object> map){
        map.put("Name",((INode) tree.getCurrent()).getKey());

    }

    public String toString(){
        return FunctionTree.toString(this);
    }
    private static String toString(FunctionTree tree){
        String str = "";
        String key;
        if(tree == null || tree.getCurrent() == null)
            key = "null";
        else key = ((INode)tree.getCurrent()).getKey();

        str+="- "+key+": \n";
        if(tree != null && tree.getNext() != null) {
            str+="Next: \n";
            for (FunctionTree functionTree : tree.getNext())
                str += toString(functionTree);
        }

        return str;
    }
}
