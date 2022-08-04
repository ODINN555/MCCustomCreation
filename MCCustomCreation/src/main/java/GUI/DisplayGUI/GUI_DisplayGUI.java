package GUI.DisplayGUI;

import GUI.AGUI;
import GUI.ChooseGUIs.GUI_ChooseGUI;
import GUI.FunctionCopyHandler;
import GUI.GUIAtrriutes.ChainGUI.IReturnable;
import GUI.Layout.LayoutOption;
import GUI.Layout.LayoutValue;
import Nodes.FunctionTree;
import Nodes.INode;
import Nodes.IReceiveAbleNode;
import Nodes.IReturningNode;
import Utility.ItemStackUtil;
import Utility.Logging.Logging;
import Utility.Logging.LoggingOptions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
     * The description color of the display items
     */
    private static final ChatColor DESCRIPTION_COLOR = ChatColor.GRAY;

    /**
     * If the GUI is on 'delete' mode
     */
    private boolean deleteMode;

    private boolean copyMode;
    private final int COPY_ITEM_SLOT = 1;


    private boolean pasteMode;
    private final int PASTE_ITEM_SLOT = 2;
    /**
     * The delete mode item's slot
     */
    private final int DEFAULT_DELETE_SLOT = 8;

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
        this.deleteMode = false;
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
            setDisplayItemDescription(item,i);
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
        if(event.getSlot() == DEFAULT_DELETE_SLOT) {
            boolean delete = !deleteMode;
            toggleAllModesOff();
            toggleDeleteMode(delete);
        }else if(event.getSlot() == COPY_ITEM_SLOT){
            boolean copy = !copyMode;
            toggleAllModesOff();
            toggleCopyMode(copy);
        }else if(event.getSlot() == PASTE_ITEM_SLOT){
            boolean paste = !pasteMode;
            toggleAllModesOff();
            togglePasteMode(paste);
        } else if(currentItem.equals(returnButton))
            onReturnClicked();
        else if(slotsOfIndexes.containsKey(event.getSlot())) // it is node
        {
            try {
                onPrimitiveClicked(event.getSlot());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * handles a primitive click
     * @param slot a given slot
     */
    private void onPrimitiveClicked(int slot) throws CloneNotSupportedException {
            if(!slotsOfIndexes.containsKey(slot))
                return;

            int index = this.slotsOfIndexes.get(slot);

            Class clickedClass = this.primitives[index];
            // next cannot be null, if it is null its a structural error. if it is null it is supposed to return to the previous GUI
            FunctionTree next = this.currentTree.getNext()[index];

            if(deleteMode){
                this.currentTree.getNext()[index] = null;
                toggleDeleteMode(false);
                // has no next or it is a primitive then need to choose
            }else if(copyMode){
                FunctionCopyHandler.INSTANCE.setCopy(getOwner(),next);
                toggleCopyMode(false);
            }else if(pasteMode){
                FunctionTree tree =  FunctionCopyHandler.INSTANCE.getCopy(getOwner());
                if(tree != null && tree.getCurrent() != null && primitives[index].isAssignableFrom(((IReturningNode) tree.getCurrent()).getReturnType())) {
                    this.currentTree.getNext()[index] = (FunctionTree) tree.clone();

                    initDisplayItems();
                    updateInventory();
                }
                togglePasteMode(false);
            }else if (next == null || next.getCurrent() == null || (next.getCurrent() instanceof IReturningNode && !(next.getCurrent() instanceof IReceiveAbleNode))){
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
        initDeleteItem();
        initPasteItem();
        initCopyItem();
        updateInventory();
    }

    /**
     * initializes the display items
     */
    public void initDisplayItems(){
        for (int slot : slotsOfIndexes.keySet()) {
            int index = slotsOfIndexes.get(slot);
            FunctionTree next = getCurrentTree().getNext()[index];
            ItemStack item = DisplayTypesHandler.INSTANCE.getByType(primitives[index]).getDisplayItem(next);
            setDisplayItemDescription(item,index);
            getInventory().setItem(slot,item);
        }
    }

    /**
     * toggles delete mode
     * @param flag the flag to toggle the mode to
     */
    public void toggleDeleteMode(boolean flag){
        this.deleteMode = flag;
        if(deleteMode)
            setDeleteDisplay();
        else setNormalDisplay();
        initDeleteItem();
        updateInventory();
    }

    /**
     * sets the display items to delete mode display
     */
    private void setDeleteDisplay(){
        for (Integer integer : slotsOfIndexes.keySet())
            getInventory().getItem(integer).setType(Material.RED_STAINED_GLASS);


        updateInventory();
    }

    /**
     * sets the display items to normal
     */
    private void setNormalDisplay(){
        initDisplayItems();
        updateInventory();
    }

    /**
     *
     * @return the default delete mode item
     */
    private ItemStack getDefaultDeleteItem(){
        ItemStack removeItem = ItemStackUtil.newItemStack(Material.CAULDRON, ChatColor.RED+"Delete Node");
        ItemMeta meta = removeItem.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        if(deleteMode)
            meta.addEnchant(Enchantment.MENDING,1,true);
        removeItem.setItemMeta(meta);
        return removeItem;
    }

    /**
     * initializes the toggle delete mode item
     */
    private void initDeleteItem(){
        getInventory().setItem(DEFAULT_DELETE_SLOT,getDefaultDeleteItem());
    }

    /**
     * toggles the copy mode to the given flag
     * @param flag a given flag
     */
    private void toggleCopyMode(boolean flag){
        this.copyMode = flag;
        initCopyItem();
    }
    /**
     *
     * @return the default copy mode item
     */
    private ItemStack getDefaultCopyItem(){
        ItemStack removeItem = ItemStackUtil.newItemStack(Material.NETHERITE_SCRAP, ChatColor.GOLD+"Copy Node");
        ItemMeta meta = removeItem.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        if(copyMode)
            meta.addEnchant(Enchantment.MENDING,1,true);
        removeItem.setItemMeta(meta);
        return removeItem;
    }

    /**
     * initializes the toggle copy mode item
     */
    private void initCopyItem(){
        getInventory().setItem(COPY_ITEM_SLOT,getDefaultCopyItem());
    }

    /**
     * toggles the copy mode to the given flag
     * @param flag a given flag
     */
    private void togglePasteMode(boolean flag){
        this.pasteMode = flag;
        initPasteItem();
    }
    /**
     *
     * @return the default paste mode item
     */
    private ItemStack getDefaultPasteItem(){
        ItemStack removeItem = ItemStackUtil.newItemStack(Material.PAINTING, ChatColor.GOLD+"Paste Node");
        ItemMeta meta = removeItem.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        if(pasteMode)
            meta.addEnchant(Enchantment.MENDING,1,true);
        removeItem.setItemMeta(meta);
        return removeItem;
    }

    /**
     * initializes the toggle paste mode item
     */
    private void initPasteItem(){
        getInventory().setItem(PASTE_ITEM_SLOT,getDefaultPasteItem());
    }

    /**
     * set the description of the index's node to the given item
     * @param item a given item
     * @param index a given index
     */
    private void setDisplayItemDescription(ItemStack item,int index){
        if(item == null || item.getType().equals(Material.AIR))
            return;
        if(index < 0 || index >= this.primitives.length)
            return;

        ItemMeta meta = item.getItemMeta();
        String description = ((IReceiveAbleNode) this.node).getReceivedTypesDescriptions()[index];
        if(!description.isEmpty())
        {
            List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
            lore.add(DESCRIPTION_COLOR+description);
            meta.setLore(lore);
        }

        item.setItemMeta(meta);
    }

    /**
     * toggles all modes off
     */
    private void toggleAllModesOff(){
        togglePasteMode(false);
        toggleDeleteMode(false);
        toggleCopyMode(false);
    }
}
