package Nodes;

import Utility.ItemStackUtil;
import Utility.PDCUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.List;

/**
 * A class representing a node as ItemStack
 */
public class NodeItemStack implements Serializable {

    /**
     * The Persistent Data Container key for NodeItemStack items
     */
    private static final String PDC_KEY = "NodeItemStack";

    /**
     * The item's material
     */
    private Material material;

    /**
     * The item's display name
     */
    private String display;

    /**
     * The item's lore
     */
    private List<String> lore;

    /**
     * The item's node class reference
     */
    private INode classRef;

    /**
     * The item's stack amount
     */
    private int stackAmount;

    /**
     * The item's ItemStack instance
     */
    private ItemStack itemStack;


    /**
     *
     * @param material a given material
     * @param display a given display name
     * @param lore a given lore
     * @param stackAmount a given stack amount
     * @param classRef a given class reference
     */
    public NodeItemStack(Material material, String display, List<String> lore, int stackAmount, INode classRef) {
        this.material = material;
        this.display = display;
        this.lore = lore;
        this.stackAmount = stackAmount;
        this.classRef = classRef;

        this.itemStack = ItemStackUtil.newItemStack(material,display,lore,stackAmount);
        this.setNodePDC();
    }

    /**
     *
     * @param item a given ItemStack instance
     * @param classRef a given class reference
     */
    public NodeItemStack(ItemStack item,INode classRef){
        this(item.getType(),item.getItemMeta().getDisplayName(),item.getItemMeta().getLore(),item.getAmount(),classRef);
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setDescription(List<String> lore) {
        this.lore = lore;
    }

    public INode getClassRef() {
        return this.classRef;
    }

    public void setClassRef(INode classRef) {
        this.classRef = classRef;
    }

    public ItemStack getItemStack(){
        return this.itemStack;
    }


    /**
     *
     * @param item a given item
     * @return if the given item is a NodeItemStack
     */
    public static boolean isNodeItemStack(ItemStack item){
        return PDCUtil.has(item,PDC_KEY,NodeItemStack.class);
    }

    /**
     * sets this NodeItemStack instance inside the PDC of this ItemStack instance
     */
    private final void setNodePDC(){
        PDCUtil.set(this.itemStack,PDC_KEY,NodeItemStack.class,this);
    }

    /**
     *
     * @param item a given item
     * @return the NodeItemStack reference from the PDC of the item
     */
    public static NodeItemStack getNodeFromItem(ItemStack item){
        if(!isNodeItemStack(item))
            return null;
        return PDCUtil.get(item,PDC_KEY,NodeItemStack.class);
    }
}
