package GUI;

import Utility.ItemStackUtil;
import me.ODINN.MCCustomCreation.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * An abstract GUI class, representing a custom GUI of this plugin.
 * Handled by GUIHandler.
 */
public abstract class AGUI {

    /**
     * The default blank item material
     */
    protected static final Material BLANK_ITEM_MATERIAL = Material.BLACK_STAINED_GLASS_PANE;

    /**
     * The default error item material
     */
    protected static final Material ERROR_ITEM_MATERIAL = Material.BARRIER;

    /**
     * The GUI's inventory reference
     */
    private Inventory inventory;
    private String title;
    /**
     * The GUI's owner
     */
    private Player owner;


    public AGUI(String title){
        if(title == null)
            title = "";
        this.title = title;


        this.inventory = initInventory();
    }

    public AGUI(){
        this("");
    }

    /**
     * initializes the inventory with the items
     * @return the initialized inventory
     */
    protected abstract Inventory initInventory();

    /**
     *
     * @return The GUI's inventory reference
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * opens this GUI for a player
     * @param player a given player
     */
    public final void open(Player player){
        this.owner = player;
        GUIHandler.INSTANCE.openGUI(this,this.owner);
    }

    /**
     * closes this GUI
     */
    public final void close(){
        if(getOwner() == null)
            return;
        GUIHandler.INSTANCE.closeGUI(this);
        onClosing();
    }

    /**
     *
     * @return The GUI's owner
     */
    public Player getOwner(){
        return owner;
    }

    /**
     * updates the inventory with any changes occurred to it
     */
    protected final void updateInventory(){
        if(getOwner() != null)
            Bukkit.getScheduler().runTaskLater(Main.getInstance(),
                    () -> getOwner().updateInventory(),
                    1);
    }

    /**
     * on opening event
     */
    protected void onOpening(){}

    /**
     * on closing event
     */
    protected void onClosing(){}

    /**
     * on click event
     * @param event a given InventoryClickEvent event
     */
    abstract protected void onClick(InventoryClickEvent event);

    /**
     *
     * @return The default blank item instance
     */
    public static ItemStack getDefaultBlankItem(){
        return ItemStackUtil.newItemStack(BLANK_ITEM_MATERIAL," ");
    }

    /**
     *
     * @return The default error item instance
     */
    public static ItemStack getDefaultErrorItem(){
        return ItemStackUtil.newItemStack(ERROR_ITEM_MATERIAL, ChatColor.RED+"Error!");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
