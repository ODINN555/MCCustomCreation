package Nodes;

import Nodes.Events.EventInstance;
import Nodes.Events.IEvent;
import Utility.ConfigUtil.Serialization.Serializations;
import Utility.Logging.Logging;
import Utility.Logging.LoggingOptions;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.*;

/**
 * An object representing a node as a part of a tree which represents a Function
 */
public class FunctionTree implements Serializable,Cloneable {

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
        if(func == null)
            return null;
        if(func.getCurrent() instanceof IEvent)
            for (FunctionTree functionTree : func.getNext())
                executeFunction(functionTree,executor,item);

        if(func.getCurrent() instanceof  IAction) {
            IAction action = (IAction) func.getCurrent();
            Object[] values = new Object[action.getReceivedTypes().length];
            for(int i = 0 ; i < action.getReceivedTypes().length; i++)
                values[i] = executeFunction(func.getNext()[i],executor,item);
            if(action.checkParameters(values))
                return  action.action(values);

        }

        if(func.getNext() == null || func.getNext().length == 0){
            if(func.getCurrent() instanceof IPrimitive)
                return ((IPrimitive) func.getCurrent()).getValue(executor,item);

            return null;
        }


        if(func.getCurrent() instanceof IGenericNode)
            return ((IGenericNode) func.getCurrent()).onGenericExecution(func,executor,item);

        if(func.getCurrent() instanceof IParameter){
            IParameter param = (IParameter) func.getCurrent();
            Object[] values = new Object[param.getReceivedTypes().length];
            for(int i = 0 ; i < param.getReceivedTypes().length; i++)
                values[i] = executeFunction(func.getNext()[i],executor,item);

            if(param.checkParameters(values))
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

    /**
     *
     * @return this tree serialized
     */
    public Map<String,Object> serialize(){
        return this.serialize(this);
    }

    /**
     *
     * @param tree a given tree
     * @return the tree serialized as a map
     */
    private Map<String,Object> serialize(FunctionTree tree){
        if(tree == null || tree.getCurrent() == null)
            return null;
        Map<String,Object> map = new HashMap<>();

        putDefaultTreeValues(tree,map);
        List<Map<String,Object>> values = new ArrayList<>();
        if(tree.getNext() != null)
        for (FunctionTree functionTree : tree.getNext())
                values.add(serialize(functionTree));

        if(tree.getCurrent() instanceof TruePrimitive) {
            TruePrimitive prim = ((TruePrimitive) tree.getCurrent());
            map.put("Value", Serializations.serialize(prim.getValue()));
            map.put("Class",prim.getReturnType().getName());
        }        else map.put("Values",values.isEmpty() ? null : values);

        if(tree.getCurrent() instanceof EventInstance) {
            map.put("Cancelled", ((EventInstance) tree.getCurrent()).isCancelled());
            map.put("Event", ((EventInstance) tree.getCurrent()).getKey());
        }

        if(tree.getCurrent() instanceof  IGenericNode){
            map.put("GenericType",((IGenericNode) tree.getCurrent()).getGenericType().getName());
        }
        return map;

    }

    /**
     *
     * @param prev the previous function tree
     * @param map the map to deserialize
     * @return the map deserialized
     * @throws CloneNotSupportedException
     */
    public static FunctionTree deserialize(FunctionTree prev ,Map<String,Object> map,String creation) throws CloneNotSupportedException, ClassNotFoundException {
        if(map == null)
            return null;

        FunctionTree tree = new FunctionTree(NodesHandler.INSTANCE.getNodeByName((String) map.get("Name")),null,prev);

        if(tree.getCurrent() instanceof IEvent) {
            tree.setCurrent(new EventInstance((IEvent) NodesHandler.INSTANCE.getNodeByName((String) map.get("Event")), (Boolean) map.get("Cancelled"), creation));
        }
        if (tree.getCurrent() instanceof IGenericNode)
                ((IGenericNode) tree.getCurrent()).setGenericType(Class.forName((String) map.get("GenericType")));

            if (tree.getCurrent() instanceof TruePrimitive) {
                    ((TruePrimitive) tree.getCurrent()).setValue(Serializations.deserialize((byte[]) map.get("Value"), Class.forName((String) map.get("Class"))));
            }else {
                    List<Map<String, Object>> list = (List) map.get("Values");
                    if (list == null || list.size() == 0)
                        return tree;
                    FunctionTree[] next = new FunctionTree[list.size()];
                    for (int i = 0; i < list.size(); i++)
                        next[i] = deserialize(tree, list.get(i), creation);
                    tree.setNext(next);
            }

        return tree;

    }

    /**
     * puts the default values of the given tree for serialization inside the given map
     * @param tree a given tree
     * @param map a given map
     */
    private void putDefaultTreeValues(FunctionTree tree, Map<String,Object> map){
        if(tree != null && map != null && tree.getCurrent() != null) {
            map.put("Name", ((INode) tree.getCurrent()).getKey());
            //Unique cases
            if(tree.getCurrent() instanceof EventInstance)
                map.put("Name","EVENT_INSTANCE");
        }


    }

    @Override
    public String toString(){
        return FunctionTree.toString(this);
    }

    /**
     *
     * @param tree a given tree
     * @return a string which describes the give tree
     */
    private static String toString(FunctionTree tree){
        String str = "";
        String key;
        if(tree == null || tree.getCurrent() == null)
            return "null";
        else key = ((INode)tree.getCurrent()).getKey();

        str+="- "+key+": \n";
        if(tree != null && tree.getNext() != null) {
            str+="Next: \n";
            for (FunctionTree functionTree : tree.getNext())
                str += toString(functionTree);
        }else if(tree.getCurrent() instanceof TruePrimitive)
                str+="Value: "+((TruePrimitive) tree.getCurrent()).getValue();

        return str;
    }

    /**
     *
     * @return if this tree is valid (if its complete)
     */
    public boolean isValid(){
        return isValid(this);
    }

    /**
     *
     * @param tree a given tree
     * @return if the given tree is valid
     */
    private static boolean isValid(FunctionTree tree){
        //validation need to be two sided since the function cant tell which is the first tree
        Object curr = tree.getCurrent();
        if(curr == null)
            return false;
        if(!(curr instanceof INode))
            return false;

        if(curr instanceof IEvent){
            if(tree.getPrev() != null) // previous should always be null for events
                return false;
            if(tree.getNext() != null && tree.getNext().length != 0)
                if(Arrays.stream(tree.getNext()).anyMatch(x -> x== null))
                    return false;
                else for (FunctionTree functionTree : tree.getNext())
                        if(!isValid(functionTree))
                            return false;

            return true;
        }

        if(curr instanceof IReceiveAbleNode) {
            if(tree.getNext() == null || tree.getNext().length == 0 || Arrays.stream(tree.getNext()).anyMatch(x -> x == null))
                return false;
        }

        if(curr instanceof  IReturningNode){
            if(tree.getPrev() == null || !(tree.getPrev().getCurrent() instanceof IReceiveAbleNode))
                return false;
        }

        if(tree.getNext() != null)
        for (FunctionTree functionTree : tree.getNext())
            if(!isValid(functionTree))
                return false;

        return true;
    }

    @Override
    public FunctionTree clone() {
        FunctionTree tree = clone(this);
        return tree;
    }

    /**
     *
     * @param tree a given tree
     * @return the given tree, cloned (includes next nodes)
     */
    private FunctionTree clone(FunctionTree tree){
        if(tree == null)
            return null;
        Object current = tree.getCurrent();
        if(current != null)
            if(tree.getCurrent() instanceof EventInstance)
                current = ((EventInstance) tree.getCurrent()).clone();
            else if(tree.getCurrent() instanceof  TruePrimitive) {
                TruePrimitive prim = (TruePrimitive)tree.getCurrent();
                        current = prim.clone();
                ((TruePrimitive) current).setValue(prim.getValue());
            }

        FunctionTree newTree = new FunctionTree(current,null,prev);

        if(tree.getNext() == null)
            return newTree;

        FunctionTree[] next = new FunctionTree[tree.getNext().length];
        for (int i = 0; i < tree.getNext().length; i++)
            next[i] = clone(tree.getNext()[i]);
        newTree.setNext(next);
        return newTree;
    }
}
