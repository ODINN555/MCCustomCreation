package GUI.DisplayGUI;

import GUI.ChooseGUIs.GUI_ChooseGUI;
import GUI.GUIAtrriutes.ChainGUI.IReturnable;
import GUI.GUIAtrriutes.ListGUI.ListableGUI;
import Nodes.Events.IEvent;
import Nodes.*;
import Utility.ItemStackUtil;
import com.sun.istack.internal.NotNull;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A GUI for displaying a certain event and it's actions
 */
public class GUI_DisplayEvent extends ListableGUI implements IReturnable {

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

    /**
     *
     * @param actions a given FunctionTree list referencing IActions
     * @param event a given event
     */
    public GUI_DisplayEvent(@NotNull List<FunctionTree> actions, IEvent event) {
        super(actions.stream()
                .filter(obj -> (obj.getCurrent() instanceof IAction))
                .map(action -> ((IAction)action.getCurrent()).getItemReference().getItemStack())
                .collect(Collectors.toList())
                , "Actions for event "+event.getDefault().getDisplay(),6,0);
        this.actionsOfTrees = new HashMap<>();
        for (FunctionTree action : actions)
            actionsOfTrees.put((IAction) action.getCurrent(),action);
        this.removeMode = false;

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
        if(removeMode)
            removeMode = false;
        initAddActionItemInInventory();
        initRemoveActionItemInInventory();
        initReturnItemInInventory();
        updateInventory();
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
        if(currentItem.equals(returnItemInstance))
            onReturnClicked();
        else if(currentItem.equals(this.addActionItemInstance)){ // add item
                this.onAddActionClicked();
        } else if(currentItem.equals(this.removeActionItemInstance)){
                this.toggleRemoveMode();
        }else if(NodeItemStack.isNodeItemStack(currentItem))
            onActionNodeClicked(NodeItemStack.getNodeFromItem(currentItem));
    }

    /**
     * handles a click on an action node
     * @param node the clicked action node
     */
    public void onActionNodeClicked(@NotNull NodeItemStack node){
        IAction action = (IAction) node.getClassRef();
        if(!this.actionsOfTrees.containsKey(action)) //TODO put error here
            return;
        if(removeMode)
        {
            this.actionsOfTrees.remove(action);
            this.toggleRemoveMode();
            return;
        }

        GUI_DisplayGUI gui = new GUI_DisplayGUI(action,this.actionsOfTrees.get(action));
        this.next(gui,false);
    }

    /**
     * handles a click
     */
    public void onAddActionClicked(){
        GUI_ChooseGUI gui = new GUI_ChooseGUI(NodesHandler.INSTANCE.getActionMap().values().stream()
                .filter(action ->{ // filter the ones already exists, if duplicable then it gets displayed
            if(getKeyByName(action.getClass().getSimpleName()) == null)
                return true;
            else return (action instanceof IDuplicableAction);
        }).collect(Collectors.toList()),null,"Choose Action");

        this.next(gui,false);
    }

    /**
     *
     * @param key a given key name
     * @return an IAction from the map which it's type is equals to the key
     */
    public IAction getKeyByName(String key){
        for (IAction action : this.actionsOfTrees.keySet())
           if(action.getClass().getSimpleName().equalsIgnoreCase(key))
               return action;
        return null;
    }

    /**
     * toggles the "remove action" mode
     */
    public void toggleRemoveMode(){
        this.removeMode = !removeMode;
        System.out.println("remove mode: "+removeMode);
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