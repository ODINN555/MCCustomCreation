package Nodes;

import Utility.ConfigUtil.Serialization.Serializations;
import Utility.ItemStackUtil;
import Utility.PDCUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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

    private static final ChatColor VALUES_COLOR = ChatColor.GRAY;
    private static final ChatColor HEADERS_COLOR = ChatColor.GOLD;
    /**
     *
     * @param material a given material
     * @param display a given display name
     * @param lore a given lore
     * @param stackAmount a given stack amount
     * @param classRef a given class reference
     */
    public NodeItemStack(Material material, String display, List<String> lore, int stackAmount, INode classRef) {
        this(ItemStackUtil.newItemStack(material,display,lore,stackAmount),classRef);


    }
    /**
     *
     * @param item a given ItemStack instance
     * @param classRef a given class reference
     */
    public NodeItemStack( ItemStack item, INode classRef){
        ItemMeta meta = item.getItemMeta();
        this.material = item.getType();
        this.display = meta.getDisplayName();
        this.lore = meta.getLore();
        this.stackAmount = item.getAmount();
        this.classRef = classRef;

        this.itemStack = new ItemStack(item);
         meta = itemStack.getItemMeta();
        List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
        if(!classRef.getDescription().isEmpty())
            lore.add(HEADERS_COLOR+"Description: "+VALUES_COLOR+classRef.getDescription());

        if(classRef instanceof IReceiveAbleNode) {
            lore.add(HEADERS_COLOR+"Primitives: ");
            for (Class receivedType : ((IReceiveAbleNode) classRef).getReceivedTypes()) {
                lore.add(HEADERS_COLOR+"- "+VALUES_COLOR + receivedType.getSimpleName());
            }
        }
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        this.setNodePDC();
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
        return PDCUtil.has(item,PDC_KEY);
    }

    /**
     * sets this NodeItemStack instance inside the PDC of this ItemStack instance
     */
    private final void setNodePDC(){
        PDCUtil.set(this.itemStack,PDC_KEY,this);
    }

    /**
     *
     * @param item a given item
     * @return the NodeItemStack reference from the PDC of the item
     */
    public static NodeItemStack getNodeFromItem(ItemStack item){
        return isNodeItemStack(item) ? PDCUtil.get(item,PDC_KEY) : null;
    }

    /**
     * override for serializable
     */
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.writeObject(this.itemStack);
        out.writeUTF(this.display);
        out.writeObject(this.lore);
        out.writeObject(this.material);
        out.writeInt(this.stackAmount);
        out.writeObject(Serializations.serialize(this.classRef));
    }

    /**
     * override for serializable
     */
    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException{
        this.itemStack = (ItemStack) in.readObject();
        this.display = in.readUTF();
        this.lore = (List<String>) in.readObject();
        this.material = (Material) in.readObject();
        this.stackAmount = in.readInt();
        this.classRef = (INode) Serializations.deserialize((byte[]) in.readObject());
    }
    }
