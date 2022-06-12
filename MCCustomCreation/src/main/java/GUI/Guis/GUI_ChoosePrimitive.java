package GUI.Guis;

import GUI.GUI;
import GUI.GUIAtrriutes.ChainGUI.ChainHandler;
import GUI.GUIAtrriutes.ChainGUI.IChainable;
import GUI.GUIAtrriutes.ListGUI.ListableGUI;
import GUI.GUIAtrriutes.NodeGUI;
import Nodes.IPrimitive;
import Nodes.NodeItemStack;
import Nodes.NodesHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GUI_ChoosePrimitive extends ListableGUI implements IChainable, NodeGUI {
    private List<IPrimitive> primitiveTypes;
    public GUI_ChoosePrimitive(List<Class> primitiveTypes) {
        super(new ArrayList<>(NodesHandler.INSTANCE.getPrimitiveMap().values()
                .stream()
                .filter( p -> primitiveTypes.contains(p))
                .map(prim -> prim.getItemReference().getItemStack())
                .collect(Collectors.toList())),"Choose a matching value");
    }

    @Override
    protected void onClick(InventoryClickEvent event) {
        //TODo
    }

    @Override
    public GUI getCurrentGUI() {
        return this;
    }

    @Override
    public void handleNodeClick(NodeItemStack node) {
            //TODO
    }
}
