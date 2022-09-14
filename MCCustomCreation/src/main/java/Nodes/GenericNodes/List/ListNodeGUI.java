package Nodes.GenericNodes.List;

import GUI.ChooseGUIs.GUI_ChooseGUI;
import GUI.DisplayGUI.GUI_DisplayGUI;
import Nodes.FunctionTree;
import Utility.ItemStackUtil;
import Utility.Logging.Logging;
import Utility.Logging.LoggingOptions;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ListNodeGUI extends GUI_DisplayGUI{

    private Class listType;
    private final int ADD_NODE_ITEM_SLOT = 7;

    public ListNodeGUI(GTPri_List listNode, FunctionTree currentTree) {
        super(Arrays.stream(currentTree.getNext()).map(x -> listNode.getReturnType()).collect(Collectors.toList()).toArray(new Class[0]), listNode, currentTree);
        this.listType = listNode.getGenericType();
    }



    @Override
    public void onClick(InventoryClickEvent event){
        Logging.log("on click in listNodeGui", LoggingOptions.TEST);
        if(event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR)
            return;

        event.setCancelled(true);
        if(super.deleteMode && super.slotsOfIndexes.containsKey(event.getSlot())) {
            int index = super.slotsOfIndexes.get(event.getSlot());
            getCurrentTree().getNext()[index] = null;
            FunctionTree[] arr = Arrays.stream(getCurrentTree().getNext()).filter(x -> x != null).collect(Collectors.toList()).toArray(new FunctionTree[0]);
            getCurrentTree().setNext(arr);
            ListNodeGUI gui = new ListNodeGUI((GTPri_List) super.node,getCurrentTree());
            next(gui,true);
        }else if(event.getSlot() == ADD_NODE_ITEM_SLOT){
            // Add one to the size of the list
            FunctionTree nextTree = new FunctionTree(null);
            FunctionTree[] nextArr = new FunctionTree[getCurrentTree().getNext().length +1];
            for (int i = 0; i < getCurrentTree().getNext().length; i++) {
                nextArr[i] = getCurrentTree().getNext()[i];
            }
            nextArr[nextArr.length -1] = nextTree;
            getCurrentTree().setNext(nextArr);
            nextTree.setPrev(getCurrentTree());

            Logging.log("listType: "+this.listType,LoggingOptions.TEST);
            GUI_ChooseGUI gui = new GUI_ChooseGUI(this.listType,nextTree);
            next(gui,false);
        }else super.onClick(event);
    }

    @Override
    public void onOpening() {
        Logging.log("onOpen current chain: "+ getHandler().getCurrentChainable(),LoggingOptions.TEST);
        this.primitives = new Class[getCurrentTree().getNext().length];
        for (int i = 0; i < getCurrentTree().getNext().length; i++)
            primitives[i] = listType;

        super.onOpening();
        initDefaultAddNodeItem();
        updateInventory();
    }

    private void initDefaultAddNodeItem(){
        getInventory().setItem(ADD_NODE_ITEM_SLOT,ItemStackUtil.newItemStack(Material.CRAFTING_TABLE,ChatColor.GREEN+"Add Value +"));
    }
}
