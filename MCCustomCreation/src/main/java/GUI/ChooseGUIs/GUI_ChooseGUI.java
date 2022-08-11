package GUI.ChooseGUIs;

import GUI.DisplayGUI.GUI_DisplayGUI;
import GUI.GUIAtrriutes.ChainGUI.IReturnable;
import GUI.GUIAtrriutes.ListGUI.ListableGUI;
import Nodes.*;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A node choosing GUI
 */
public class GUI_ChooseGUI extends ListableGUI implements IReturnable {

    /**
     * The current tree instance, this current tree is presenting the value of the current choice of value.
     * In other words, the value chosen from this gui will be replacing the current tree value
     */
    private FunctionTree currentTree;

    /**
     * The return's item instance
     */
    private ItemStack returnItem;

    /**
     *
     * @param returnType a given return type
     * @param currentTree a given current tree
     */
    public GUI_ChooseGUI(Class returnType , FunctionTree currentTree) {
        this(Stream.concat( // make 1 big list out of all primitives and parameters which their return type matches the given one
                NodesHandler.INSTANCE.getPrimitiveMap().values()
                        .stream()
                        .filter(p -> returnType.isAssignableFrom(p.getReturnType()) || p.getReturnType().equals(Object.class))

                ,NodesHandler.INSTANCE.getParameterMap().values()
                        .stream()
                        .filter(p -> returnType.isAssignableFrom(p.getReturnType()) || p.getReturnType().equals(Object.class)))
                            .collect(Collectors.toList())
                ,currentTree,"Choose A "+returnType.getSimpleName());


    }


    /**
     *
     * @param nodes a given list of nodes to choose from
     * @param currentTree a given current function tree
     * @param name a given name
     */
    public GUI_ChooseGUI(List<INode> nodes, FunctionTree currentTree, String name){
        super(nodes.stream().map(n -> n.getItemReference().getItemStack()).collect(Collectors.toList()),name);
        this.currentTree = currentTree;
    }

    @Override
    public void initReturnItemInInventory() {
            returnItem = getDefaultReturnItemStack();
            getInventory().setItem(0,returnItem);
    }

    @Override
    public void onOpening(){
        super.onOpening();

        initReturnItemInInventory();
    }

    @Override
    protected void onClick(InventoryClickEvent event){
        super.onClick(event);
        if(event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR))
            return;

        event.setCancelled(true); // event should always be cancelled at this GUI

        ItemStack curr = event.getCurrentItem();
        if(curr.equals(returnItem))
            onReturnClicked();
        else if(NodeItemStack.isNodeItemStack(curr))
            onNodeClicked(NodeItemStack.getNodeFromItem(curr));


    }

    @Override
    public void onClosing() {
        IReturnable.super.onClosing();
    }

    /**
     * handles a click on one of the items presenting a class of a returnType of the current node
     * @param item a given clicked item
     */
    public void onNodeClicked(NodeItemStack item){

        if(item.getClassRef() instanceof IPrimitive) { // IPrimitive is unique since it closes the chain
            this.currentTree.setCurrent(item.getClassRef());
            if (item.getClassRef() instanceof TruePrimitive) {
                ((TruePrimitive) item.getClassRef()).onChosen(this);
            }else {
                this.prev();
            }
            return;
        }
        if(item.getClassRef() instanceof IReceiveAbleNode) { // node must be receivable

            IReceiveAbleNode ref = (IReceiveAbleNode) item.getClassRef();
            FunctionTree[] next = new FunctionTree[ref.getReceivedTypes().length];

            // current tree should not be null, if it is, its a structural error
                this.currentTree.setCurrent(ref);
                if(next.length != 0)
                    this.currentTree.setNext(next);

            if (next.length != 0) {
                GUI_DisplayGUI gui = new GUI_DisplayGUI(ref.getReceivedTypes(), ref, this.currentTree);
                this.next(gui, true);
            } else prev();
        }
    }

    public FunctionTree getCurrentTree() {
        return currentTree;
    }

    public ItemStack getReturnItem() {
        return returnItem;
    }

}
