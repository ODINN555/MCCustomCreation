package Nodes;

import Nodes.Events.IEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public interface IGenericNode extends IReturningNode{

    Material GENERIC_NODE_MATERIAL = Material.WHITE_STAINED_GLASS_PANE;

    boolean onGenericChosen(Class chosenClass);

    IGenericNode cloneGeneric();

    @Override
    default NodeItemStack getItemReference(){
      return  new NodeItemStack(GENERIC_NODE_MATERIAL, ChatColor.WHITE+getDisplayTitle(),null,1,this);
    }

    Class getGenericType();

    default Object onGenericExecution(FunctionTree func, LivingEntity executor, ItemStack item){

        // same as executeFunction just without the generic, if needed the generic node will override the method
        if(func == null)
            return null;
        if(func.getCurrent() instanceof IEvent)
            for (FunctionTree functionTree : func.getNext())
                FunctionTree.executeFunction(functionTree,executor,item);

        if(func.getCurrent() instanceof  IAction) {
            IAction action = (IAction) func.getCurrent();
            Object[] values = new Object[action.getReceivedTypes().length];
            for(int i = 0 ; i < action.getReceivedTypes().length; i++)
                values[i] = FunctionTree.executeFunction(func.getNext()[i],executor,item);
            if(action.checkParameters(values))
                return  action.action(values);

        }

        if(func.getNext() == null || func.getNext().length == 0){
            if(func.getCurrent() instanceof IPrimitive)
                return ((IPrimitive) func.getCurrent()).getValue(executor,item);

            return null;
        }

        if(func.getCurrent() instanceof IParameter){
            IParameter param = (IParameter) func.getCurrent();
            Object[] values = new Object[param.getReceivedTypes().length];
            for(int i = 0 ; i < param.getReceivedTypes().length; i++)
                values[i] = FunctionTree.executeFunction(func.getNext()[i],executor,item);

            if(param.checkParameters(values))
                return param.getParameter(values);
        }

        return null;
    }

    String getDisplayTitle();
}
