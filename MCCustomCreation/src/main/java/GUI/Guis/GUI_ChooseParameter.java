package GUI.Guis;

import GUI.GUI;
import GUI.GUIAtrriutes.ChainGUI.IReturnable;
import GUI.GUIAtrriutes.ListGUI.ListableGUI;
import GUI.GUIAtrriutes.NodeGUI;
import Nodes.IParameter;
import Nodes.NodeItemStack;
import Nodes.NodesHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GUI_ChooseParameter extends ListableGUI implements IReturnable , NodeGUI {
    private List<IParameter> parameters;
    private ItemStack returnItem;

    public GUI_ChooseParameter(List<Class> returnTypes) {
        super(new ArrayList<>(NodesHandler.INSTANCE.getParameterMap().values()
                .stream()
                .filter( p -> returnTypes.contains(p.getReturnType()))
                .map(param -> param.getItemReference().getItemStack())
                .collect(Collectors.toList())),
                "Choose a matching Parameter",7,0);
    }






    @Override
    public GUI getCurrentGUI() {
        return this;
    }

    @Override
    public void initReturnItemInInventory() {
        this.returnItem = getDefaultReturnItemStack();
        getInventory().setItem(0,returnItem);
    }

    @Override
    public ItemStack getDefaultReturnItemStack(){
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RED+"<- Back");
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void handleNodeClick(NodeItemStack node) {
        //TODO
    }

    protected void onClick(InventoryClickEvent event){
        if(event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR))
            return;

        ItemStack currItem = event.getCurrentItem();
        if(currItem.equals(this.returnItem))
            onReturnClicked();
        else if(NodeItemStack.isNodeItemStack(currItem))
                handleNodeClick(NodeItemStack.getNodeFromItem(currItem));


    }


}
