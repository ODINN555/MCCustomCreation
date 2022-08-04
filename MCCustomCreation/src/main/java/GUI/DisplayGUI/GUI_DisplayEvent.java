package GUI.DisplayGUI;

import GUI.ChooseGUIs.GUI_ChooseGUI;
import GUI.GUIAtrriutes.ChainGUI.IReturnable;
import GUI.GUIAtrriutes.ListGUI.ListableGUI;
import Nodes.Events.EventInstance;
import Nodes.Events.IEvent;
import Nodes.*;
import Utility.ItemStackUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A GUI for displaying a certain event and it's actions
 */
public class GUI_DisplayEvent extends ListableGUI implements IReturnable {

    private EventInstance event;
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
     * If the GUI is on "swap actions" mode
     */
    private boolean swapActionsMode;

    /**
     * The swap actions item slot
     */
    private static final int SWAP_ACTIONS_ITEM_SLOT = 6;

    /**
     * The swap actions item material
     */
    private static final Material SWAP_ACTION_MATERIAL = Material.YELLOW_STAINED_GLASS;

    /**
     * The current selected for swap item's slot. -1 if non selected.
     */
    private int selectedSwapSlot;

    private static final int CANCEL_SLOT = 1;
    /**
     * The function tree list to display
     */
    private List<FunctionTree> functionList;

    /**
     * Map<Slot,FunctionTree>, the map represents the slots of the function trees
     */
    private Map<Integer,FunctionTree> indexes ;

    /**
     *
     * @param actions a given FunctionTree list referencing IActions
     * @param event a given event
     */
    public GUI_DisplayEvent( List<FunctionTree> actions, IEvent event) {
        super(getActionsAsItemStacks(actions), "Actions for event "+event.getDefaultNodeItem().getDisplay(),7,0,1,0);
        this.functionList = actions;
        this.removeMode = false;
        this.event = (EventInstance) event;
        selectedSwapSlot = -1;
    }

    /**
     *
     * @param actions a given action list
     * @return the actions as ItemStack instances
     */
    private static List<ItemStack> getActionsAsItemStacks(List<FunctionTree> actions){
        return actions.stream()
                .filter(obj -> (obj != null && obj.getCurrent() instanceof IAction))
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

        this.indexes = new HashMap<>();
    if(functionList.size() > 0)
    if(functionList.get(functionList.size() -1) == null || functionList.get(functionList.size() -1).getCurrent() == null){
        functionList.remove(functionList.size() -1);
    }

        this.itemsList = getActionsAsItemStacks(functionList);
        super.onOpening();
        if(removeMode)
            removeMode = false;
        this.swapActionsMode = false;
        this.selectedSwapSlot = -1;
        int index = 0;
        for(int i = 0; i < getInventory().getSize(); i++)
            if(NodeItemStack.isNodeItemStack(getInventory().getItem(i))) {
                indexes.put(i, functionList.get(index));
                index++;
            }
        initSetCancelledItem();
        initAddActionItemInInventory();
        initRemoveActionItemInInventory();
        initReturnItemInInventory();
        initDisplayActionItems();
        initSwapActionsItem();
        updateInventory();
    }

    /**
     * initializes the display items
     */
    public void initDisplayActionItems(){

        for (int i =0; i< getInventory().getSize(); i++) {
            ItemStack itemStack = getInventory().getItem(i);
            if(NodeItemStack.isNodeItemStack(itemStack))
            {

                ItemMeta meta = itemStack.getItemMeta();
                FunctionTree tree = indexes.get(i);
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
        }else if(event.getSlot() == CANCEL_SLOT){
            toggleCancelled(!this.event.isCancelled());
        } else if(currentItem.equals(this.addActionItemInstance)){// add item
                this.onAddActionClicked();

        } else if(currentItem.equals(this.removeActionItemInstance)){
                if(swapActionsMode)
                    this.toggleSwapActionsMode(false);
            this.toggleRemoveMode();
        }else if(NodeItemStack.isNodeItemStack(currentItem)) {
            onActionNodeClicked(NodeItemStack.getNodeFromItem(currentItem),event.getSlot());
        }else if(event.getSlot() == SWAP_ACTIONS_ITEM_SLOT) {
            toggleSwapActionsMode(!swapActionsMode);
            if(removeMode)
                toggleRemoveMode();
        }
    }

    /**
     * handles a click on an action node
     * @param node the clicked action node
     */
    public void onActionNodeClicked( NodeItemStack node,int slot){
        IAction action = (IAction) node.getClassRef();
        if(removeMode)
        {
            this.functionList.remove(indexes.get(slot));
            GUI_DisplayEvent gui = new GUI_DisplayEvent(functionList, this.event);
            this.next(gui,true);
        }else if(swapActionsMode) {
            if(selectedSwapSlot == slot)
                toggleSwapActionsMode(false);
            else{
                if(selectedSwapSlot == -1) {
                    selectedSwapSlot = slot;
                    selectSwap(slot);
                }else {
                    Collections.swap(this.functionList,this.functionList.indexOf(this.indexes.get(slot)),this.functionList.indexOf(this.indexes.get(selectedSwapSlot)));
                    GUI_DisplayEvent gui = new GUI_DisplayEvent(functionList, this.event);
                    this.next(gui,true);
                }
            }
        }else {
            if(action.getReceivedTypes().length == 0)
                return;
            GUI_DisplayGUI gui = new GUI_DisplayGUI(action,this.indexes.get(slot));
            this.next(gui, false);
        }
    }

    /**
     * handles a click
     */
    public void onAddActionClicked(){
        FunctionTree tree = new FunctionTree(null);
        functionList.add(tree);
        GUI_ChooseGUI gui = new GUI_ChooseGUI(NodesHandler.INSTANCE.getActionMap().values().stream()
                .filter(action ->{ // filter the ones already exists, if duplicable then it gets displayed
             if(action instanceof IDuplicableAction)
                    return ((IDuplicableAction) action).isDuplicable();
            return false;
        }).collect(Collectors.toList()),tree,"Choose Action");

        this.next(gui,false);
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

    /**
     * toggles the 'swap actions' mode
     * @param flag the flag to toggle to
     */
    private void toggleSwapActionsMode(boolean flag){
        this.swapActionsMode = flag;
        if(!swapActionsMode) {
            setNormalView();
            this.selectedSwapSlot = -1;
        }
        initSwapActionsItem();
    }

    /**
     * selects a slot for a swap
     * @param slot a given slot
     */
    private void selectSwap(int slot){
        getInventory().getItem(slot).setType(SWAP_ACTION_MATERIAL);
        updateInventory();
    }

    /**
     *
     * @return the default swap actions item
     */
    private ItemStack getDefaultSwapActionsItem(){
        ItemStack removeItem = ItemStackUtil.newItemStack(Material.COMPARATOR, ChatColor.GOLD+"Swap Actions");
        ItemMeta meta = removeItem.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        if(swapActionsMode)
            meta.addEnchant(Enchantment.MENDING,1,true);
        removeItem.setItemMeta(meta);
        return removeItem;
    }

    /**
     * initializes the swap actions item in the inventory
     */
    private void initSwapActionsItem(){
        getInventory().setItem(SWAP_ACTIONS_ITEM_SLOT,getDefaultSwapActionsItem());
        updateInventory();
    }

    /**
     *
     * @return a new "set event cancelled" item instance
     */
    private ItemStack getSetCancelledItem(){
        boolean cancelled = false;
        if(this.event != null)
            cancelled = this.event.isCancelled();

        final Material CANCELLED_MATERIAL = Material.GREEN_STAINED_GLASS;
        final ChatColor CANCELLED_COLOR = ChatColor.GREEN;
        final Material NOT_CANCELLED_MATERIAL = Material.RED_STAINED_GLASS;
        final ChatColor NOT_CANCELLED_COLOR = ChatColor.RED;

        String description = cancelled ? "Cancelled" : "Not Cancelled";
        Material mat = cancelled ? CANCELLED_MATERIAL : NOT_CANCELLED_MATERIAL;
        ChatColor color = cancelled ? CANCELLED_COLOR : NOT_CANCELLED_COLOR;
        return ItemStackUtil.newItemStack(mat,color+"Toggle Cancelled",Arrays.asList(ChatColor.GRAY+description));
    }

    /**
     * initializes the "set event cancelled" item in the inventory
     */
    public void initSetCancelledItem(){
        getInventory().setItem(CANCEL_SLOT,getSetCancelledItem());
        updateInventory();
    }

    /**
     * sets by the given flag if the displayed event should be cancelled
     * @param flag a given flag
     */
    private void toggleCancelled(boolean flag){
        if(this.event == null)
            return;
        this.event.setCancelled(flag);
        initSetCancelledItem();
    }

}
