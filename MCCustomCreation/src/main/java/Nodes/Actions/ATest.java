package Nodes.Actions;

import Nodes.IAction;
import Nodes.NodeItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;

public class ATest implements IAction {
    @Override
    public boolean action(Object... params) {
        Bukkit.broadcastMessage("Test action works!");
        return true;
    }

    @Override
    public Class[] getReceivedTypes() {
        return new Class[]{};
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
