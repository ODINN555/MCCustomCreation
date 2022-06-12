package GUI.GUIAtrriutes;

import GUI.GUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public abstract class PageableGUI extends GUI implements Listener {


    protected int page;
    protected int maxPageCount;
    private ItemStack nextPageItemStack;
    private ItemStack prevPageItemStack;
    protected int nextPageSlot;
    protected int previousPageSlot;

    public PageableGUI(int maxPageCount,int nextPageSlot,int previousPageSlot,ItemStack nextPageItemStack, ItemStack prevPageItemStack) {
        super();
        this.maxPageCount = maxPageCount;
        this.nextPageSlot = nextPageSlot;
        this.previousPageSlot = previousPageSlot;
        this.initNextPageItemStack(nextPageItemStack);
        this.initPrevPageItemStack(prevPageItemStack);
    }

    public PageableGUI(int maxPageCount,int nextPageSlot,int previousPageSlot){
        super();
        this.maxPageCount = maxPageCount;
        this.nextPageSlot = nextPageSlot;
        this.previousPageSlot = previousPageSlot;
        this.initNextPageItemStack(Material.ARROW, ChatColor.YELLOW+"Next Page", Arrays.asList(ChatColor.GRAY+"Shift + Click to go to last page."),1);
        this.initPrevPageItemStack(Material.ARROW, ChatColor.YELLOW+"Previous Page", Arrays.asList(ChatColor.GRAY+"Shift + Click to go to first page."),1);
    }

    public PageableGUI(){
        super();
        maxPageCount = 1;
        this.initNextPageItemStack(Material.ARROW, ChatColor.YELLOW+"Next Page", Arrays.asList(ChatColor.GRAY+"Shift + Click to go to last page."),1);
        this.initPrevPageItemStack(Material.ARROW, ChatColor.YELLOW+"Previous Page", Arrays.asList(ChatColor.GRAY+"Shift + Click to go to first page."),1);
    }

    protected abstract void onPageChange(int from,int to);

    public void initPageItemStacks(){
        if(page > 1)// add previous
            getInventory().setItem(previousPageSlot,prevPageItemStack);
        else getInventory().setItem(previousPageSlot,null);

        if(page < maxPageCount )
            getInventory().setItem(nextPageSlot,nextPageItemStack);
        else getInventory().setItem(nextPageSlot,null);

        updateInventory();
    }



    private ItemStack initNextPageItemStack(Material mat,String name,List<String> description,int count) {
        ItemStack item = new ItemStack(mat,count);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(description);

        this.nextPageItemStack = item;
        return item;
    }

    private ItemStack initNextPageItemStack(ItemStack item){
        this.nextPageItemStack = item;
        return item;
    }


    private ItemStack initPrevPageItemStack(Material mat,String name,List<String> description,int count){
        ItemStack item = new ItemStack(mat,count);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(description);

        this.prevPageItemStack = item;
        return item;
    }
    private ItemStack initPrevPageItemStack(ItemStack item){
        this.prevPageItemStack = item;
        return item;
    }

    public int setPage(int page){
        this.onPageChange(this.page,page);
        this.page = page;
        return page;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(event.getInventory() == null || event.getClickedInventory() == null)
            return;
        if(event.getCurrentItem().equals(nextPageItemStack))
            setPage((event.isLeftClick()? maxPageCount : page +1));

        if(event.getCurrentItem().equals(prevPageItemStack))
            setPage((event.isShiftClick()? 0 : page -1));
    }

    @Override
    protected void onOpening(){
        initPageItemStacks();
        updateInventory();
    }

}
