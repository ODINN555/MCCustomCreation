package Nodes.GenericNodes.List;

import Nodes.FunctionTree;
import Nodes.IGenericNode;
import Nodes.IParameter;
import Utility.Logging.Logging;
import Utility.Logging.LoggingOptions;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Array;

public class GP_GetItemFromListByIndex implements IGenericNode, IParameter {

    private Class returnType;

    public GP_GetItemFromListByIndex(Class returnType) {
        this.returnType = returnType;
    }
    public GP_GetItemFromListByIndex() {
        this.returnType = Object.class;
    }

    @Override
    public boolean onGenericChosen(Class chosenClass) {
        this.returnType = chosenClass;
        return true;
    }

    @Override
    public IGenericNode cloneGeneric() {
        return new GP_GetItemFromListByIndex();
    }

    @Override
    public Class getGenericType() {
        return this.returnType;
    }

    @Override
    public void setGenericType(Class type) {
        this.returnType = type;
    }


    @Override
    public String getDisplayTitle() {
        return "Get "+getGenericType().getSimpleName()+" From List By Index";
    }

    @Override
    public String getKey() {
        return "GET_ITEM_FROM_LIST_BY_INDEX";
    }

    @Override
    public String getDescription() {
        return "Get an object from a list by index (index starts from 0)";
    }

    @Override
    public Object getParameter(Object... objects) {
        return ((Object[]) objects[0])[((int) objects[1])];
    }

    @Override
    public Class getReturnType() {
        return this.returnType;
    }

    @Override
    public Class[] getReceivedTypes() {
        return new Class[]{Array.newInstance(getGenericType(),1).getClass(),Integer.class};
    }

    @Override
    public String[] getReceivedTypesDescriptions() {
        return new String[]{"The list","The index"};
    }
}
