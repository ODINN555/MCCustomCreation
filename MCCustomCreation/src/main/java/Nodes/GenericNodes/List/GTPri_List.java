package Nodes.GenericNodes.List;

import GUI.DisplayGUI.GUI_DisplayGUI;
import Nodes.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GTPri_List implements IGenericNode, ICustomDisplayedNode,IParameter {

    private Class returnType;
    private FunctionTree tree;

    public GTPri_List(){
        this(null,null);
    }

    public GTPri_List(Class returnType,FunctionTree tree){
        this.returnType = returnType;
        this.tree = tree == null ? new FunctionTree(this,new FunctionTree[0],null) : tree;
    }

    @Override
    public boolean checkParameters(Object... params) {
        return !Arrays.stream(params).anyMatch(x -> !params[0].getClass().equals(x.getClass()));
    }

    @Override
    public Object onGenericExecution(FunctionTree tree, LivingEntity executor, ItemStack item) {
        List arr = new ArrayList();
        for(FunctionTree t : tree.getNext())
            arr.add(FunctionTree.executeFunction(t,executor,item));

        return arr.toArray((Object[]) Array.newInstance(getGenericType(),arr.size()));
    }

    @Override
    public boolean onDisplay(FunctionTree displayTree,GUI_DisplayGUI displayGUI) {
        this.tree = displayTree;
        if(this.tree.getNext() == null)
            this.tree.setNext(new FunctionTree[0]);
        displayGUI.next(new ListNodeGUI(this,this.tree),false);
        return true;
    }

    @Override
    public boolean onGenericChosen(Class chosenClass) {
        if(List.class.isAssignableFrom(chosenClass) || chosenClass.isArray()) {
            setReturnType(chosenClass.getComponentType());
            return true;
        }

        return false;
        }

    @Override
    public IGenericNode cloneGeneric() {
        return new GTPri_List(this.returnType,tree);
    }

    @Override
    public String getKey() {
        return "LIST";
    }

    @Override
    public String getDescription() {
        return "Create a new list of "+ getGenericType().getSimpleName();
    }

    @Override
    public Object getParameter(Object... objects) {
        return Arrays.stream(objects).collect(Collectors.toList());
    }

    @Override
    public Class getReturnType() {
        if(returnType == null)
            return Object.class;

        return Array.newInstance(returnType,0).getClass();
    }


    public void setReturnType(Class returnType) {
        this.returnType = returnType;
    }

    @Override
    public Class[] getReceivedTypes() {
        return Arrays.stream(tree.getNext()).map(x-> getReturnType()).collect(Collectors.toList()).toArray(new Class[0]);
    }

    @Override
    public String[] getReceivedTypesDescriptions() {
        String[] arr = new String[tree.getNext().length];
        for (int i = 0; i < arr.length; i++)
            arr[i] = "";

        return arr;
    }

    public Class getGenericType(){
        return this.returnType;
    }

    @Override
    public String getDisplayTitle() {
        return "List Of "+ getGenericType().getSimpleName();
    }
}
