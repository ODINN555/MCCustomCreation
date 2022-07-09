package Nodes.Actions;

import Nodes.IAction;
import Nodes.NodeItemStack;
import org.bukkit.Material;

public class ATest implements IAction {
    @Override
    public boolean action(Object... params) {
        return true;
    }

    @Override
    public Class[] getReceivedTypes() {
        return new Class[]{Integer.class};
    }

    @Override
    public NodeItemStack getItemReference() {
        return new NodeItemStack(Material.QUARTZ,getKey(),null,1,this);
    }

    @Override
    public String getKey() {
        return "TestAction";
    }
}
