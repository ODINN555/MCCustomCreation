package GUI.GUIAtrriutes;

import GUI.AGUI;
import Utility.ItemStackUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

/**
 * A pageable GUI, this abstract class lets the inherent classes use pages
 */
public abstract class PageableGUI extends AGUI {

    /**
     * The gui's current page
     */
    protected int page;

    /**
     * The gui's max page count
     */
    protected int maxPageCount;

    /**
     * The nextPage ItemStack instance
     */
    private ItemStack nextPageItemStack;

    /**
     * The prevPage ItemStack instance
     */
    private ItemStack prevPageItemStack;

    /**
     * The nextPage ItemStack instance's slot
     */
    protected int nextPageSlot;

    /**
     * The prevPage ItemStack instance's slot
     */
    protected int previousPageSlot;


    /**
     *
     * @param maxPageCount a given max page count
     * @param nextPageSlot a given nextPage item slot
     * @param previousPageSlot a given prevPage item slot
     * @param nextPageItemStack a given nextPage ItemStack instance
     * @param prevPageItemStack a given prevPage ItemStack instance
     */
    public PageableGUI(String title,int maxPageCount,int nextPageSlot,int previousPageSlot,ItemStack nextPageItemStack, ItemStack prevPageItemStack) {
        super(title);
        this.maxPageCount = maxPageCount;
        this.nextPageSlot = nextPageSlot;
        this.previousPageSlot = previousPageSlot;
        this.page = 1;

        this.initNextPageItemStack(nextPageItemStack);
        this.initPrevPageItemStack(prevPageItemStack);
    }

    /**
     *
     * @param maxPageCount a given max page count
     * @param nextPageSlot a given nextPage item slot
     * @param previousPageSlot a given prevPage item slot
     */
    public PageableGUI(String title, int maxPageCount,int nextPageSlot,int previousPageSlot){
        this(title,maxPageCount,nextPageSlot,previousPageSlot,getDefaultNextPageItemStack(),getDefaultPrevPageItemStack());
    }


    /**
     * handles a page change
     * @param from the page which the gui is moving from
     * @param to the page which the gui is moving too
     */
    protected abstract void onPageChange(int from,int to);

    /**
     * initializes the page ItemStack instances in the inventory
     */
    public void initPageItemStacks(){
        if(page > 1)// add previous
            getInventory().setItem(previousPageSlot,prevPageItemStack);
        else getInventory().setItem(previousPageSlot,null);

        if(page < maxPageCount )
            getInventory().setItem(nextPageSlot,nextPageItemStack);
        else getInventory().setItem(nextPageSlot,null);

        updateInventory();
    }


    /**
     *
     * @param mat a given material
     * @param name a given name
     * @param description a given description
     * @param count a given stack amount
     * @return a nextPage ItemStack instance with the given parameters
     */
    private ItemStack initNextPageItemStack(Material mat,String name,List<String> description,int count) {
        ItemStack item = new ItemStack(mat,count);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(description);
        item.setItemMeta(meta);
        this.nextPageItemStack = item;
        return item;
    }

    /**
     *
     * @param item a given item
     * @return a nextPage ItemStack instance with the given parameters
     */
    private ItemStack initNextPageItemStack(ItemStack item){
        this.nextPageItemStack = item;
        return item;
    }

    /**
     *
     * @return the default next page ItemStack instance
     */
    public static ItemStack getDefaultNextPageItemStack(){
        return ItemStackUtil.newItemStack(Material.ARROW, ChatColor.YELLOW+"Next Page", Arrays.asList(ChatColor.GRAY+"Shift + Click to go to last page."),1);
    }

    /**
     *
     * @param mat a given material
     * @param name a given name
     * @param description a given description
     * @param count a given stack amount
     * @return a prevPage ItemStack instance with the given parameters
     */
    private ItemStack initPrevPageItemStack(Material mat,String name,List<String> description,int count){
        ItemStack item = new ItemStack(mat,count);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(description);
        item.setItemMeta(meta);
        this.prevPageItemStack = item;
        return item;
    }

    /**
     *
     * @param item a given item
     * @return a nextPage ItemStack instance with the given parameters
     */
    private ItemStack initPrevPageItemStack(ItemStack item){
        this.prevPageItemStack = item;
        return item;
    }

    /**
     *
     * @return the default previous page ItemStack instance
     */
    public static ItemStack getDefaultPrevPageItemStack(){
        return ItemStackUtil.newItemStack(Material.ARROW, ChatColor.YELLOW+"Previous Page", Arrays.asList(ChatColor.GRAY+"Shift + Click to go to first page."),1);
    }

    /**
     *
     * @param page a given page number
     * @return the page which the gui is moving to
     */
    public int setPage(int page){
        if(page > maxPageCount)
            page = maxPageCount;

        this.onPageChange(this.page,page);
        this.page = page;
        initPageItemStacks();
        return page;
    }


    @Override
    protected void onOpening(){
        super.onOpening();
        initPageItemStacks();
        updateInventory();
    }

    @Override
    protected void onClick(InventoryClickEvent event){
        if(event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR))
            return;

        if(event.getCurrentItem().equals(nextPageItemStack))
            setPage((event.isShiftClick()? maxPageCount : page +1));
        else if(event.getCurrentItem().equals(prevPageItemStack))
                setPage((event.isShiftClick()? 1 : page -1));
    }
}
