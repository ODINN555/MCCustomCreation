package GUI.DisplayGUI;

import GUI.AGUI;
import GUI.ChooseGUIs.GUI_ChooseGUI;
import GUI.GUIAtrriutes.ChainGUI.IReturnable;
import GUI.Layout.LayoutOption;
import GUI.Layout.LayoutValue;
import Nodes.FunctionTree;
import Nodes.INode;
import Nodes.IReceiveAbleNode;
import Nodes.IReturningNode;
import Utility.Logging.Logging;
import Utility.Logging.LoggingOptions;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * A GUI for displaying a node
 */
public class GUI_DisplayGUI extends AGUI implements IReturnable {

    /**
     * Map<Slot,Index> , the Slot is a slot in the GUI and the Index is an index of the primitives array
     */
    private Map<Integer,Integer> slotsOfIndexes;

    /**
     * the primitives that the node has to display
     */
    private Class[] primitives;

    /**
     * The returnButton ItemStack instance
     */
    private ItemStack returnButton;

    /**
     * The current function tree
     */
    private FunctionTree currentTree;

    /**
     * The node being displayed
     */
    private INode node;

    /**
     *
     * @param primitives given primitives to display
     * @param node a given node to display
     * @param currentTree a given current function tree
     */
    public GUI_DisplayGUI(Class[] primitives, INode node, FunctionTree currentTree){
        super();
        this.currentTree = currentTree;
        this.node = node;
        this.primitives = primitives;
        this.slotsOfIndexes = new HashMap<>();
    }

    /**
     *
     * @param node a given node to display
     * @param currentTree a given current function tree
     */
    public GUI_DisplayGUI(IReceiveAbleNode node, FunctionTree currentTree){
        this(node.getReceivedTypes(),node,currentTree);
    }


    @Override
    protected Inventory initInventory() {

        LayoutValue val = LayoutOption.CENTERED.getSlotsByLayout(this.primitives.length);

        if(val.size == 54)
        {
            Logging.log("Maximum amount of function primitives is 45! used 45 out of chosen primitives. also, good luck crushing your game.", LoggingOptions.ERROR);
            val.size -= 9;
        }
        Inventory inv = Bukkit.createInventory(null,val.size + 9,this.node.getItemReference().getDisplay());

        for (int i = 0; i < inv.getSize(); i++)
            inv.setItem(i,getDefaultBlankItem());
        for (int i = 0; i < val.slots.length; i++)
        {
            ItemStack item = DisplayTypesHandler.INSTANCE.getByType(this.primitives[i]).getDisplayItem(this.currentTree.getNext()[i]);
            inv.setItem(val.slots[i] + 9,item);
            this.slotsOfIndexes.put(9+val.slots[i],i);
        }

        return inv;
    }

    @Override
    protected void onClick(InventoryClickEvent event) {
        if(event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR))
            return;
        event.setCancelled(true); // no item should be grabbed, then event should always be cancelled
        ItemStack currentItem = event.getCurrentItem();
        if(currentItem.equals(returnButton))
            onReturnClicked();
        else if(slotsOfIndexes.containsKey(event.getSlot())) // it is node
            onPrimitiveClicked(event.getSlot());

    }

        private void onPrimitiveClicked(int slot){
            if(!slotsOfIndexes.containsKey(slot))
                return;

            int index = this.slotsOfIndexes.get(slot);

            Class clickedClass = this.primitives[index];
            // next cannot be null, if it is null its a structural error. if it is null it is supposed to return to the previous GUI
            FunctionTree next = this.currentTree.getNext()[index];

            // has no next or it is a primitive then need to choose
            if (next == null || next.getCurrent() == null || (next.getCurrent() instanceof IReturningNode && !(next.getCurrent() instanceof IReceiveAbleNode))){
                next = next == null ? new FunctionTree(null, null, this.currentTree) : next;
                this.currentTree.getNext()[index] = next;
                GUI_ChooseGUI nextGui = new GUI_ChooseGUI(clickedClass,next);
                this.next(nextGui,false);
            }else if(next.getCurrent() instanceof IReceiveAbleNode){ // has something to display so need to display the next

                Class[] nextPrimitives = ((IReceiveAbleNode) next.getCurrent()).getReceivedTypes();
                GUI_DisplayGUI gui = new GUI_DisplayGUI(nextPrimitives,(INode) next.getCurrent(),next);
                this.next(gui,false);
            }
        }

    @Override
    public void initReturnItemInInventory() {
            this.returnButton = getDefaultReturnItemStack();
            getInventory().setItem(0,returnButton);
    }

    @Override
    public void onClosing() {
        IReturnable.super.onClosing();
    }

    public ItemStack getReturnButton() {
        return returnButton;
    }

    public void setReturnButton(ItemStack returnButton) {
        this.returnButton = returnButton;
    }

    public FunctionTree getCurrentTree() {
        return currentTree;
    }

    public void setCurrentTree(FunctionTree currentTree) {
        this.currentTree = currentTree;
    }

    public INode getNode() {
        return node;
    }

    public void setNode(INode node) {
        this.node = node;
    }

    @Override
    public void onOpening(){
        super.onOpening();
        initReturnItemInInventory();
        initDisplayItems();
    }

    public void initDisplayItems(){
        for (int slot : slotsOfIndexes.keySet()) {
            int index = slotsOfIndexes.get(slot);
            FunctionTree next = getCurrentTree().getNext()[index];

            getInventory().setItem(slot,DisplayTypesHandler.INSTANCE.getByType(primitives[index]).getDisplayItem(next));
        }
    }
}
