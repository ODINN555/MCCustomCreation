package GUI.Guis;

import GUI.GUI;
import GUI.GUIAtrriutes.ChainGUI.IChainable;
import GUI.GUIAtrriutes.ListGUI.ListableGUI;
import GUI.GUIAtrriutes.NodeGUI;
import Nodes.NodeItemStack;
import Nodes.NodesHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class GUI_ChooseAction extends ListableGUI implements IChainable , NodeGUI {

    public GUI_ChooseAction() {
        super( new ArrayList<>(NodesHandler.INSTANCE.getActionMap().values().stream().map(action -> action.getItemReference().getItemStack()).collect(Collectors.toList())),"Add Action",7,0);
    }

    @Override
    protected void onClick(InventoryClickEvent event) {
        //TODO
    }

    @Override
    public GUI getCurrentGUI() {
        return this;
    }

    @Override
    public void handleNodeClick(NodeItemStack node) {
        //TODo
    }
}
