package GUI.DisplayGUI;

import GUI.ChooseGUIs.GUI_ChooseGUI;
import GUI.GUIAtrriutes.ChainGUI.IReturnable;
import GUI.GUIAtrriutes.ListGUI.ListableGUI;
import Nodes.Events.IEvent;
import Nodes.*;
import Utility.ItemStackUtil;
import Utility.Logging.Logging;
import Utility.Logging.LoggingOptions;
import com.sun.istack.internal.NotNull;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A GUI for displaying a certain event and it's actions
 */
public class GUI_DisplayEvent extends ListableGUI implements IReturnable {

    private IEvent event;
    /**
     * The returnItem ItemStack instance of this GUI
     */
    private ItemStack returnItemInstance;

    /**
     * The addAction ItemStack instance of this GUI
     */
    private ItemStack addActionItemInstance;

    /**
     * The removeAction ItemStack instance of this GUI
     */
    private ItemStack removeActionItemInstance;

    /**
     * If the GUI is on "remove action" mode
     */
    boolean removeMode;

    /**
     * The removeAction ItemStack instance's slot
     */
    private static final int removeActionSlot = 8;

    /**
     * The addAction ItemStack instance's slot
     */
    private static final int addActionSlot = 7;

    /**
     * The returnItem ItemStack instance's slot
     */
    private static final int returnItemSlot = 0;

    /**
     * A map containing all the IActions of the event which is displayed by this GUI and their FunctionTree reference
     */
    private Map<IAction,FunctionTree> actionsOfTrees;
    private List<FunctionTree> functionList;
    /**
     *
     * @param actions a given FunctionTree list referencing IActions
     * @param event a given event
     */
    public GUI_DisplayEvent(@NotNull List<FunctionTree> actions, IEvent event) {
        super(getActionsAsItemStacks(actions), "Actions for event "+event.getDefault().getDisplay(),6,0);
        this.actionsOfTrees = new HashMap<>();
        this.functionList = actions;
        for (FunctionTree action : actions)
            actionsOfTrees.put((IAction) action.getCurrent(),action);
        this.removeMode = false;
        this.event = event;
    }

    /**
     *
     * @param actions a given action list
     * @return the actions as ItemStack instances
     */
    private static List<ItemStack> getActionsAsItemStacks(List<FunctionTree> actions){
        return actions.stream()
                .filter(obj -> (obj.getCurrent() instanceof IAction))
                .map(action -> ((IAction)action.getCurrent()).getItemReference().getItemStack())
                .collect(Collectors.toList());
    }

    /**
     *
     * @param event a given event
     */
    public GUI_DisplayEvent(IEvent event){
        this(new ArrayList<>(),event);
    }
    @Override
    public void onClosing() {
        IReturnable.super.onClosing();
    }

    @Override
    public void onOpening(){

        if(actionsOfTrees.containsKey(null)){
            if(actionsOfTrees.get(null).getCurrent() == null)
                functionList.remove(actionsOfTrees.remove(null));
            else {
                actionsOfTrees.put((IAction) actionsOfTrees.get(null).getCurrent(), actionsOfTrees.get(null));
                actionsOfTrees.remove(null);
                this.itemsList = getActionsAsItemStacks(actionsOfTrees.values().stream().collect(Collectors.toList()));
            }
        }
        super.onOpening();
        if(removeMode)
            removeMode = false;
        initAddActionItemInInventory();
        initRemoveActionItemInInventory();
        initReturnItemInInventory();
        initDisplayActionItems();
        updateInventory();
    }

    public void initDisplayActionItems(){

        for (int i =0; i< getInventory().getSize(); i++) {
            ItemStack itemStack = getInventory().getItem(i);
            if(NodeItemStack.isNodeItemStack(itemStack))
            {

                ItemMeta meta = itemStack.getItemMeta();
                IAction action = (IAction) NodeItemStack.getNodeFromItem(itemStack).getClassRef();
                FunctionTree tree = actionsOfTrees.get(getKeyByName(action.getKey()));
                meta.setLore(DisplayTypesHandler.INSTANCE.getFunctionTreeDisplayOnNode(tree));
                itemStack.setItemMeta(meta);
                getInventory().setItem(i,itemStack);
            }
        }
    }

    @Override
    public void initReturnItemInInventory() {
        this.returnItemInstance = getDefaultReturnItemStack();
        getInventory().setItem(returnItemSlot,returnItemInstance);
    }

    /**
     *
     * @return The default ItemStack instance
     */
    public ItemStack getDefaultAddActionItemStack(){
        return ItemStackUtil.newItemStack(Material.CRAFTING_TABLE, ChatColor.GREEN+"Add Action +");
    }

    /**
     * initializes the ItemStack instance in the inventory
     */
    public void initAddActionItemInInventory(){
        this.addActionItemInstance = getDefaultAddActionItemStack();
        getInventory().setItem(addActionSlot,this.addActionItemInstance);
    }

    /**
     *
     * @return The default ItemStack instance
     */
    public ItemStack getDefaultRemoveActionItemStack(){
        ItemStack removeItem = ItemStackUtil.newItemStack(Material.CAULDRON,ChatColor.RED+"Remove Action -");
        ItemMeta meta = removeItem.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        removeItem.setItemMeta(meta);
        return removeItem;
    }

    /**
     * initializes the ItemStack instance in the inventory
     */
    public void initRemoveActionItemInInventory(){
        this.removeActionItemInstance = getDefaultRemoveActionItemStack();
        getInventory().setItem(removeActionSlot,this.removeActionItemInstance);
    }


    @Override
    protected void onClick(InventoryClickEvent event){
        if(event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR))
            return;

        event.setCancelled(true); // no item should be grabbed, event should always be cancelled
        ItemStack currentItem = event.getCurrentItem();
        if(currentItem.equals(returnItemInstance)) {
            onReturnClicked();

        }
        else if(currentItem.equals(this.addActionItemInstance)){// add item
                this.onAddActionClicked();

        } else if(currentItem.equals(this.removeActionItemInstance)){
                this.toggleRemoveMode();
        }else if(NodeItemStack.isNodeItemStack(currentItem)) {
            onActionNodeClicked(NodeItemStack.getNodeFromItem(currentItem));
        }
    }

    /**
     * handles a click on an action node
     * @param node the clicked action node
     */
    public void onActionNodeClicked(@NotNull NodeItemStack node){
        IAction action = (IAction) node.getClassRef();
        if(getKeyByName(action.getKey()) == null) {
            Logging.log("There is no action with the specified key. Key: "+action.getKey(), LoggingOptions.ERROR);
            return;
        }
        if(removeMode)
        {
            this.functionList.remove(this.actionsOfTrees.get(getKeyByName(action.getKey())));
            this.actionsOfTrees.remove(getKeyByName(action.getKey()));
            functionList.addAll(this.actionsOfTrees.values());
            GUI_DisplayEvent gui = new GUI_DisplayEvent(functionList, this.event);
            this.next(gui,true);
        }else {
            if(action.getReceivedTypes().length == 0)
                return;
            GUI_DisplayGUI gui = new GUI_DisplayGUI(action, this.actionsOfTrees.get(getKeyByName(action.getKey())));
            this.next(gui, false);
        }
    }

    /**
     * handles a click
     */
    public void onAddActionClicked(){
        FunctionTree tree = new FunctionTree(null);
        actionsOfTrees.put(null,tree);
        functionList.add(tree);
        GUI_ChooseGUI gui = new GUI_ChooseGUI(NodesHandler.INSTANCE.getActionMap().values().stream()
                .filter(action ->{ // filter the ones already exists, if duplicable then it gets displayed
            if(getKeyByName(action.getKey()) == null)
                return true;
            else return (action instanceof IDuplicableAction);
        }).collect(Collectors.toList()),tree,"Choose Action");

        this.next(gui,false);
    }

    /**
     *
     * @param key a given key name
     * @return an IAction from the map which it's type is equals to the key
     */
    public IAction getKeyByName(String key){
        for (IAction action : this.actionsOfTrees.keySet())
           if(action != null && action.getKey().equalsIgnoreCase(key))
               return action;
        return null;
    }

    /**
     * toggles the "remove action" mode
     */
    public void toggleRemoveMode(){
        this.removeMode = !removeMode;
        if(removeMode)
            setRemoveModeView();
        else setNormalView();
    }

    /**
     * sets the view of the GUI to "remove action" mode
     */
    private void setRemoveModeView(){
        this.removeActionItemInstance.addUnsafeEnchantment(Enchantment.MENDING,1);
        for (int i = 0; i < getInventory().getSize(); i++) {
            ItemStack itemStack = getInventory().getItem(i);
            if (NodeItemStack.isNodeItemStack(itemStack))
                itemStack.setType(Material.RED_STAINED_GLASS);
            getInventory().setItem(i,itemStack);
        }
        getInventory().setItem(removeActionSlot, removeActionItemInstance);
        updateInventory();
    }

    /**
     * sets the view mode of the GUI to "normal" mode
     */
    private void setNormalView(){
        this.removeActionItemInstance.removeEnchantment(Enchantment.MENDING);
        for (int i = 0; i < getInventory().getSize(); i++) {
            ItemStack itemStack = getInventory().getItem(i);
            if (NodeItemStack.isNodeItemStack(itemStack))
                itemStack.setType(NodeItemStack.getNodeFromItem(itemStack).getMaterial());
            getInventory().setItem(i,itemStack);
        }
        getInventory().setItem(removeActionSlot,removeActionItemInstance);
        updateInventory();
    }



}
