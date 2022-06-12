package GUI;

import me.ODINN.MCCustomCreation.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public abstract class GUI implements Listener {

    private Inventory inventory;
    private Player owner;
    public GUI(){
        this.inventory = initInventory();
    }

    protected abstract Inventory initInventory();


    public Inventory getInventory() {
        return inventory;
    }

    public final void open(Player p){
        this.owner = p;
        GUIHandler.INSTANCE.openGUI(this,this.owner);
        onOpening();
    }

    public final void close(){
        if(getOwner() == null)
            return;
        GUIHandler.INSTANCE.closeGUI(this);
        onClosing();
    }

    public Player getOwner(){
        return owner;
    }

    protected final void updateInventory(){
        if(getOwner() == null)
            return;

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> getOwner().updateInventory(),1);
    }

    protected void onOpening(){}
    protected void onClosing(){}

    abstract protected void onClick(InventoryClickEvent event);


}
