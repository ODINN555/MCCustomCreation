package GUI.DisplayGUI;

import GUI.GUI;
import GUI.GUIAtrriutes.ChainGUI.IReturnable;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DisplayGUI extends GUI implements IReturnable {

    private List<DisplayType> displayTypes;
    private ItemStack returnButton;

    public DisplayGUI(Class... types){
        this(Arrays.asList(types).stream().map(t -> DisplayTypesHandler.INSTANCE.getByType(t)).collect(Collectors.toList()));
    }

    public DisplayGUI(List<DisplayType> types){
        this.displayTypes = types;
    }

    @Override
    protected Inventory initInventory() {
        return null;
    }

    @Override
    protected void onClick(InventoryClickEvent event) {
        if(event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR))
            return;

        ItemStack currentItem = event.getCurrentItem();
        if(currentItem.equals(returnButton))
            onReturnClicked();
        else{
            //TODO
        }
    }

    @Override
    public void initReturnItemInInventory() {
            this.returnButton = getDefaultReturnItemStack();
            getInventory().setItem(0,returnButton);
    }
}
